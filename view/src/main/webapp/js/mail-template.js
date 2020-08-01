//скрипт для почты

'use strict';

//скрипт для автозаполнения полей при выборе шаблона
var templates = {
    "template 1": "Поздравляю с днём рожденья! Желаю счастья, здоровья и успехов в делах!",
    "template 2": "Привет! Давно не виделись! Может быть, ты не против встретиться в ближайшее время?"
}
var choice = document.querySelector('#templateField');
choice.addEventListener('change', function () {
    var template = choice.options[choice.selectedIndex];
    var textarea = document.querySelector("#messageField");
    var header = document.querySelector("#headerField");
    if (templates.hasOwnProperty(template.value)) {
        header.value = template.text;
        textarea.value = templates[template.value];
    } else {
        header.value = ""
        textarea.value = "";
    }
})

//автозаполнение емэйла по чекбоксу
function autofill() {
    var id = window.location.href.split("?")[1].split("=")[1];
    if (!(id.trim().length === 0)) {
        var request = new XMLHttpRequest();
        request.onreadystatechange = function () {
            if (this.readyState === 4 && this.status === 200) {
                var to = document.querySelector('#emailField');
                var receiver = JSON.parse(this.responseText);
                to.value = receiver.email;
            }
        }
        request.open("GET", "http://localhost:8080/view_war/mail/" + id, true);
        request.send();
    }
}

autofill();

//модальное окошко после отправки
var modalSend = document.querySelector('#messageSend');
var sendBtn = document.querySelector("#sendButton");
var success = "Сообщение успешно отправлено";
var fail = "Что-то пошло не так";

sendBtn.addEventListener('click', function (event) {
    event.preventDefault();
    var request = new XMLHttpRequest();
    request.onreadystatechange = function () {
        console.log(this.status + " " + this.readyState);
        if (this.readyState === 4 && this.status === 200) {
            modalSend.querySelector('p').innerHTML = success;
            modalSend.style.display = 'block';
        }
        else if (this.status === 404 || this.status === 500) {
            modalSend.querySelector('p').innerHTML = fail;
            modalSend.style.display = 'block';
        }
    }
    var to = document.getElementById('emailField').value;
    var subject = document.getElementById('headerField').value;
    var body = document.getElementById('messageField').value;
    var postData = 'to=' + to;
    postData += '&subject=' + encodeURIComponent(subject);
    postData += '&body=' + encodeURIComponent(body);
    request.open("POST", "http://localhost:8080/view_war/mail/", true);
    request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    request.send(postData);
})

var returnBtn = document.querySelector('#returnButton');
returnBtn.addEventListener('click', function () {
    document.location.href = "../index.html";
})

//закрытие модального окошка
var closeBtn = document.querySelectorAll('.close');

closeBtn.forEach(btn => {
    btn.addEventListener('click', () => {
        modalSend.style.display = "none";
        modalSend.querySelector('p').innerHTML = "Сообщение успешно отправлено";
    })
})

window.onclick = function (event) {
    if (event.target === modalSend) {
        modalSend.style.display = "none";
        modalSend.querySelector('p').innerHTML = "Сообщение успешно отправлено";
    }
}