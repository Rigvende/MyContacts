//скрипт для почты

'use strict';

//форма и поля
var form = document.querySelector('#mailForm')
var fields = form.querySelectorAll('.field');
var mail = document.querySelector('#emailField');
var header = document.querySelector("#headerField");
var textarea = document.querySelector("#messageField");

//автозаполнение полей при выборе шаблона
var templates = {
    "template 1": "Поздравляю с днём рожденья! Желаю счастья, здоровья и успехов в делах!",
    "template 2": "Привет! Давно не виделись! Может быть, ты не против встретиться в ближайшее время?"
}

var choice = document.querySelector('#templateField');
choice.addEventListener('change', function () {
    var template = choice.options[choice.selectedIndex];
    if (templates.hasOwnProperty(template.value)) {
        header.value = template.text;
        textarea.value = templates[template.value];
    } else {
        header.value = ""
        textarea.value = "";
    }
})

//отмена отправки
var cancelMail = document.querySelector('#cancelMail');
cancelMail.addEventListener('click', function () {
    document.location.href = "../index.html";
})

//автозаполнение емэйла по чекбоксу
function autofill() {
    var id = window.location.href.split("?")[1].split("=")[1];
    if (id != null && id.trim()) {
        var request = new XMLHttpRequest();
        request.onreadystatechange = function () {
            if (this.readyState === 4 && this.status === 200) {
                var receiver = JSON.parse(this.responseText);
                mail.value = receiver.email;
            }
        }
        request.open("GET", "http://localhost:8080/view_war/mail/" + id, true);
        request.send();
    }
}

autofill();

//модальное окошко после отправки
var modalSend = document.querySelector('#messageSend');
var modalBack = document.querySelector('.modalContent');
var success = "Сообщение успешно отправлено";
var fail = "Что-то пошло не так";

var returnBtn = document.querySelector('#returnButton');
returnBtn.addEventListener('click', function () {
    document.location.href = "../index.html";
})

//закрытие модального окошка
var closeBtn = document.querySelectorAll('.close');

closeBtn.forEach(btn => {
    btn.addEventListener('click', () => {
        modalSend.style.display = "none";
    })
})

window.onclick = function (event) {
    if (event.target === modalSend) {
        modalSend.style.display = "none";
    }
}

//валидация полей и отправка
var errors;

form.addEventListener('submit', function (event) {
    event.preventDefault();
    removeValidation();
    checkFieldsPresence();
    validateEmail();
    errors = form.querySelectorAll('.error');
    if (!errors || errors.length === 0) {
        var spinnerDiv = document.createElement('div');
        showSpinner(spinnerDiv);
        var request = new XMLHttpRequest();
        request.onreadystatechange = function () {
            if (this.readyState === 4 && this.status === 200) {
                spinnerDiv.style.display = 'none';
                modalSend.querySelector('p').innerHTML = success;
                modalBack.style.backgroundColor = '#31c3ff';
                modalSend.style.display = 'block';
                form.reset();
            } else if (this.status === 404 || this.status === 500) {
                spinnerDiv.style.display = 'none';
                modalSend.querySelector('p').innerHTML = fail;
                modalBack.style.backgroundColor = 'Chocolate';
                modalSend.style.display = 'block';
            }
        }
        var to = mail.value;
        var subject = header.value;
        var body = textarea.value;
        var postData = 'to=' + to;
        postData += '&subject=' + encodeURIComponent(subject);
        postData += '&body=' + encodeURIComponent(body);
        request.open("POST", "http://localhost:8080/view_war/mail/", true);
        request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        request.send(postData);
    }
})

//показ спиннера во время отправки сообщения
function showSpinner(spinnerDiv) {
    var pageContainer = document.querySelector('.pageContainer');
    var spinner = document.createElement('img');
    spinner.src = '../image/spinner.svg';
    spinnerDiv.appendChild(spinner);
    var spinnerMsg = document.createElement('label');
    spinnerMsg.textContent = 'Сообщение отправляется...';
    spinnerDiv.appendChild(spinnerMsg);
    spinnerDiv.style.cssText = `display: flex; justify-content: center; background-color: chocolate;
    font-family: "Trebuchet MS", Arial, Helvetica, sans-serif; font-size: 18px;
    color: white; max-width: 300px`;
    pageContainer.insertAdjacentElement('afterbegin', spinnerDiv);
}

//очистка ошибок
var removeValidation = function () {
    errors = form.querySelectorAll('.error');
    for (var i = 0; i < errors.length; i++) {
        errors[i].remove();
    }
}

//генерация ошибок
var generateError = function (text) {
    var error = document.createElement('div');
    error.className = 'error';
    error.style.color = 'Chocolate';
    error.innerHTML = text;
    return error;
}

//проверка заполненности полей
var checkFieldsPresence = function () {
    for (var i = 0; i < fields.length; i++) {
        if (!fields[i].value) {
            var error = generateError('Заполните это поле');
            form[i].parentElement.insertBefore(error, fields[i]);
        }
    }
}

//проверка email
function validateEmail() {
    var regex = /^[-\w.]+@([A-z0-9][-A-z0-9]+\.)+[A-z]{2,4}$/;
    if (!regex.test(mail.value) || mail.length < 10 || mail.length > 255) {
        var error = generateError('Неподходящий e-mail');
        mail.parentElement.insertBefore(error, mail);
    }
}
