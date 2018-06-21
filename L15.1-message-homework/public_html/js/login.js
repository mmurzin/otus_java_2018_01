var ws;

init = function () {
    ws = new WebSocket("ws://localhost:8080/websocket");
    ws.onopen = function (event) {

    }
    ws.onmessage = function (event) {
        var result = JSON.parse(event.data)
        if(result.loginSuccessful){
            alert("Авторизация прошла успешно")
        }else{
            alert("Неверный логин пароль")
        }
    }
    ws.onclose = function (event) {

    }
};

function doLogin() {
    var login = document.getElementById("login");
    var password = document.getElementById("password");
    var user = {};
    user.login = login.value;
    user.password = password.value;
    ws.send(JSON.stringify(user));
}