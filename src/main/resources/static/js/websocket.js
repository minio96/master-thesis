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
            weapons = message.weaponList;
            if (isCurrent) {
                weapons.forEach(monsterFullfil);
                weaponList.addEventListener("click", function (e) {
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
            connectRest(username);
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
            info.innerText = 'Name: ' + player.name + ' Level: ' + player.level;
            break;
        case 'start' :
            console.log("I got started");
            weaponList.innerHTML = "";
            usernamePage.classList.add('hidden');
            restartButton.classList.remove('hidden');
            first.classList.remove('hidden');
            chatPage.classList.remove('hidden');
            var weapons = message.startSetList[player.id].weaponList;
            let monster = message.startSetList[player.id].monster;
            setMonster(monster);

            console.log(weapons);
            weapons.forEach(monsterFullfil);

            weaponList.addEventListener("click", function (e) {
                if (e.target) {
                    globalMsg = e.target;
                    attack(e.target.id);
                    document.getElementById(e.target.id).remove();
                }
            });
            break;
    }

}
