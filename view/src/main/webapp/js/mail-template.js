//скрипт для почты

'use strict';

//форма и поля
var form = document.querySelector('#mailForm')
// var fields = form.querySelectorAll('.field');
var mail = document.querySelector('#emailField');
var header = document.querySelector("#headerField");
var textarea = document.querySelector("#messageField");

var templates;

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
    var x = window.location.href.split("?id=");
    if (x.length === 2) {
        var id = x[1];
        if (id != null && id.trim()) {
            if (validateId(id)) {
                var request = new XMLHttpRequest();
                request.onreadystatechange = function () {
                    if (this.readyState === 4 && this.status === 200) {
                        var receiver = JSON.parse(this.responseText);
                        if (receiver.email != null && receiver.email.trim()) {
                            mail.value = receiver.email;
                        } else {
                            mail.value = "Адрес отстутствует. Обновите данные контакта.";
                        }
                        templates = receiver.templates;
                    }
                    if (this.status === 404) {
                        document.location.href = "../html/error.html";
                    }
                }
                request.open("GET", "../mail/" + id, true);
                request.send();
            } else {
                document.location.href = "../html/error.html";
            }
        }
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

//валидация полей и отправка сообщения
var errors;
function validate() {
    removeValidation();
    checkFieldPresence(mail);
    checkFieldPresence(header);
    checkFieldPresence(textarea);
    validateEmail(mail);
    validateLengthMail(textarea);
}

form.addEventListener('submit', function (event) {
    event.preventDefault();
    validate();
    errors = form.querySelectorAll('.error');
    if (!errors || errors.length === 0) {
        var spinnerDiv = document.createElement('div');
        spinnerDiv.classList.add('spinner');
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
        request.open("POST", "../mail/", true);
        request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        request.send(postData);
    }
})
