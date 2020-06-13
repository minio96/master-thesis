'use strict';

let usernamePage = document.querySelector('#username-page');
let title = document.getElementById('username-page-title');
let joinButton = document.getElementById('join-game-button');
let startGame = document.getElementById('start-game');
let startButton = document.getElementById('start-game-button');
let weaponList = document.getElementById('weapon-list');
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
let restartButton = document.getElementById('restart-button');
restartButton.addEventListener('click', startNewGame);
const url = 'http://localhost:8080'


let stompClient = null;
let gameClient = null;
let stompGameClient = null;
let isSubscribed = false;
let username = null;
let globalMsg = null;
let isCurrent = false;
var player = {id: 0, name: 'name', level: 1, bonus: 0};
var fetchMsg = null;
var addMsg = null;

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

function connectRest(username) {
    var link = url + '/newPlayer/' + username;
    console.log(link);
    fetch(link)
        .then(response => response.json())
        .then(response => addMsg = response)
        .catch(response => console.log(response));
}

function onConnected() {
    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/public', onMessageReceived);
    stompClient.subscribe('/user/game', onGameCommunicateReceived);
    stompClient.subscribe('/game', onGameCommunicateReceived);
    isSubscribed = true;
    connectingElement.classList.add('hidden');
    stompClient.send("/app/game.addUser",
        {},
        JSON.stringify({sender: username, type: 'JOIN'})
    );
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


function monsterFullfil(item, index) {
    weaponList.innerHTML += "<li id=\"" + item.power + "\">" +
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