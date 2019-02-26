var ws;

init = function () {
    ws = new WebSocket("ws://localhost:8090/wschat");
    ws.onopen = function (event) {

    }
    ws.onmessage = function (event) {
        var $textarea = document.getElementById("messages");
        $textarea.value = $textarea.value + event.data + "\n";
    }
    ws.onclose = function (event) {

    }
};

function SendMessageOnEnter(e) {
    e = e || window.event;
    if (e.keyCode === 13) {
        alert("Вы нажали Enter!");
		sendMessage();
    }
    return false;
};

function sendMessage() {
    var messageField = document.getElementById("message");
    var userNameField = document.getElementById("username");
    var message = userNameField.value + ":" + messageField.value;
    ws.send(message);
    messageField.value = '';
}