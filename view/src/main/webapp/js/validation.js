//скрипт для валидации данных при отправке запроса

'use strict';

//показ спиннера во время обработки запроса
function showSpinner(spinnerDiv) {
    var pageContainer = document.querySelector('.pageContainer');
    var spinner = document.createElement('img');
    spinner.src = '../image/spinner.svg';
    spinnerDiv.appendChild(spinner);
    var spinnerMsg = document.createElement('label');
    spinnerMsg.textContent = 'Ожидайте...';
    spinnerDiv.appendChild(spinnerMsg);
    pageContainer.insertAdjacentElement('afterbegin', spinnerDiv);
}

//очистка ошибок
var removeValidation = function () {
    errors = document.querySelectorAll('.error');
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
var checkFieldsPresence = function (form, fields) {
    for (var i = 0; i < fields.length; i++) {
        if (!fields[i].value) {
            var error = generateError('Заполните это поле');
            form[i].parentElement.insertBefore(error, fields[i]);
        }
    }
}

//проверка email
function validateEmail(mail) {
    var regex = /^[-\w.]+@([A-z0-9][-A-z0-9]+\.)+[A-z]{2,4}$/;
    alert(mail.value.length);
    if (!regex.test(mail.value) || mail.value.length < 10 || mail.value.length > 255) {
        var error = generateError('Неподходящий e-mail');
        mail.parentElement.insertBefore(error, mail);
    }
}
