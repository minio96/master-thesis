'use strict';

let usernamePage = document.querySelector('#username-page');
let title = document.getElementById('username-page-title');
let joinButton = document.getElementById('join-game-button');
let startGame = document.getElementById('start-game');
let startButton = document.getElementById('start-game-button');
let weapon = document.getElementById('weapon-list');
let equipment = document.getElementById('equipment');
let info = document.getElementById('info');
let time = document.getElementById('time');
let name = document.getElementById('name');
let chatPage = document.querySelector('#chat-page');
let first = document.querySelector('#game-page');
let usernameForm = document.querySelector('#usernameForm');
let messageForm = document.querySelector('#messageForm');
let messageInput = document.querySelector('#message');
let messageArea = document.querySelector('#messageArea');
let connectingElement = document.querySelector('.connecting');
let monsterDiv = document.getElementById('monster');
let nextRoundButton = document.getElementById('next-round');


let stompClient = null;
let gameClient = null;
let stompGameClient = null;
let isSubscribed = false;
let username = null;
let globalMsg = null;
let isCurrent = false;
var player = {id: 0, name: 'name', level: 1, bonus: 0};

let colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

function connect(event) {
    username = document.querySelector('#name').value.trim();

    if (username) {
        let socket = new SockJS('/chat');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onConnected, onError);
    }
    event.preventDefault();
}


function onConnected() {
    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/public', onMessageReceived);
    stompClient.subscribe('/user/game', onGameCommunicateReceived);
    stompClient.subscribe('/game', onGameCommunicateReceived);
    isSubscribed = true;
    connectingElement.classList.add('hidden');

    // Tell your username to the server
    stompClient.send("/app/game.addUser",
        {},
        JSON.stringify({sender: username, type: 'JOIN'})
    );
    // setInterval(doPolling, 3 * 1000);
}


function onError(error) {
    connectingElement.textContent = error.getAuthTag();
    connectingElement.style.color = 'red';
}

function doPolling() {
    const userAction = async () => {
        const response = await fetch('http:/localhost:8080/newMessage');
        onMessageReceived(response);
        // do something with myJson
    }
}


function sendMessage(event) {
    let messageContent = messageInput.value.trim();
    if (messageContent && stompClient) {
        let chatMessage = {
            sender: username,
            content: messageInput.value,
            type: 'CHAT'
        };
        stompClient.send("/app/game.sendMessage", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}

function startNewGame() {
    let chatMessage = {
        sender: username,
        content: "",
        type: 'START'
    };
    isSubscribed = true;
    stompClient.send("/app/game.startGame", {}, JSON.stringify(chatMessage));
}

function setMonster(monster) {
    globalMsg = monster;
    document.getElementById('monster-name').innerText = 'Name ' + monster.name;
    document.getElementById('monster-power').innerText = 'Health ' + monster.health;
    document.getElementById('monster-treasures').innerText = 'Treasures ' + monster.treasures;
}


function onGameCommunicateReceived(payload) {

    globalMsg = payload;
    let message = JSON.parse(payload.body);
    console.log("onGameCommunicateReceived " + message.body);

    switch (message.type) {
        case 'nextRound':
            monsterDiv.innerText = 'Monster';
            nextRoundButton.classList.add('hidden');
            var newMonster = message.monster;
            setMonster(newMonster);
            var weapons = message.weaponList;
            if (isCurrent) {
                weapons.forEach(monsterFullfil);
                weapon.addEventListener("click", function (e) {
                    if (e.target) {
                        globalMsg = e.target;
                        attack(e.target.id);
                        document.getElementById(e.target.id).remove();
                    }
                });
            }
            break;
        case 'deadMonster':
            monsterDiv.innerText = 'Monster defeated. Wait for next fight';
            if (isCurrent) {
                nextRoundButton.classList.remove('hidden');
                console.log("dupa");
                nextRoundButton.addEventListener('click', function () {
                    monsterDead(message.treasures.toString())
                });
            }
            globalMsg = message;
            break;
        case 'monster':
            setMonster(message);
            break;
        case 'player':
            player.name = username;
            if (message.id === 0) {
                title.innerText = "Start game whenever you want";
                name.classList.add('hidden');
                usernameForm.classList.add('hidden');
                startGame.classList.remove('hidden');
                startButton.addEventListener("click", startNewGame);
                isCurrent = true;
            } else {
                console.log(message.type + ' joined');
                title.innerText = "Waiting for other players...";
                name.classList.add('hidden');
                joinButton.classList.add('hidden');
                player.id = message.id;
            }
            info.innerText = 'Name: ' + player.name + ' ID: ' + player.id + player.bonus + player.level;
            break;
        case 'start' :
            console.log("I got started");
            usernamePage.classList.add('hidden');
            first.classList.remove('hidden');
            chatPage.classList.remove('hidden');
            var weapons = message.startSetList[player.id].weaponList;
            let monster = message.startSetList[player.id].monster;
            setMonster(monster);

            console.log(weapons);
            weapons.forEach(monsterFullfil);

            weapon.addEventListener("click", function (e) {
                if (e.target) {
                    globalMsg = e.target;
                    attack(e.target.id);
                    document.getElementById(e.target.id).remove();
                }
            });
            break;
    }

}

function monsterFullfil(item, index) {
    weapon.innerHTML += "<li id=\"" + item.power + "\">" +
        "Name:" + item.name + "<br>" +
        "Bonus:" + item.power + "<br>" +
        "Type:" + item.type + "<br>" +
        "</li>";
}

function attack(weaponId) {
    let hit = {
        sender: player.id,
        content: weaponId,
        type: 'HIT'
    };
    stompClient.send("/app/game.hitMonster", {}, JSON.stringify(hit));
}

function monsterDead(treasures) {
    let dead = {
        sender: player.id,
        content: treasures,
        type: 'DEAD'
    };
    stompClient.send("/app/game.deadMonster", {}, JSON.stringify(dead));
}

function onMessageReceived(payload) {
    let message = JSON.parse(payload.body);
    let messageElement = document.createElement('li');
    console.log(message);
    switch (message.type) {
        case 'JOIN':
            console.log(message.type + 'joined');
            messageElement.classList.add('event-message');
            message.content = message.sender + ' joined!';
            if (isSubscribed) {
                title.innerText = "Waiting for other players...";
                name.classList.add('hidden');
                joinButton.classList.add('hidden');
            }
            break;
        case 'LEAVE':
            console.log(message.type + 'left');
            messageElement.classList.add('event-message');
            message.content = message.sender + ' left!';
            break;
        default:
            console.log(message.type + 'default');
            messageElement.classList.add('chat-message');
            let avatarElement = document.createElement('i');
            let avatarText = document.createTextNode(message.sender[0]);
            avatarElement.appendChild(avatarText);
            avatarElement.style['background-color'] = getAvatarColor(message.sender);
            messageElement.appendChild(avatarElement);
            let usernameElement = document.createElement('span');
            let usernameText = document.createTextNode(message.sender);
            usernameElement.appendChild(usernameText);
            messageElement.appendChild(usernameElement);
    }


    let textElement = document.createElement('p');
    let messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}


function getAvatarColor(messageSender) {
    let hash = 0;
    for (let i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }
    let index = Math.abs(hash % colors.length);
    return colors[index];
}

usernameForm.addEventListener('submit', connect, true);
messageForm.addEventListener('submit', sendMessage, true);