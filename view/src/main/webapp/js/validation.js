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

//проверка на неподходящие знаки
var checkInappropriate = function (form, fields) {
    var inappropriate = [';', '\"', '\'', '\`'];
    for (var i = 0; i < fields.length; i++) {
        for (var k = 0; k < inappropriate.length; k++) {
            if (fields[i].value.indexOf(inappropriate[k]) !== -1) {
                var error = generateError('Поле содержит неподходящий знак препинания');
                form[i].parentElement.insertBefore(error, fields[i]);
            }
        }
    }
}

//проверка email
function validateEmail(mail) {
    var regex = /^[-\w.]+@([A-z0-9][-A-z0-9]+\.)+[A-z]{2,4}$/;
    if (!regex.test(mail.value) || mail.value.length < 10 || mail.value.length > 255) {
        var error = generateError('Неподходящий e-mail');
        mail.parentElement.insertBefore(error, mail);
    }
}

//проверка имени, фамилии
function validateName(name) {
    var regex = /^([-\sA-zА-я]){1,45}$/;
    if (!regex.test(name.value)) {
        var error = generateError('Неподходящее сочетание символов');
        name.parentElement.insertBefore(error, name);
    }
}

//проверка сайта
function validateSite(site) {
    var regex = /([-\w]+.)?([-\w]+).\w+(?:.\w+)?\/?.*/;
    if (!regex.test(site.value) || site.value.length > 255) {
        var error = generateError('Неподходящий сайт');
        site.parentElement.insertBefore(error, site);
    }
}

//проверка полей
function validateData(data) {
    var regex = /^([-)(.,\w\s/]){1,45}$/;
    if (!regex.test(data.value)) {
        var error = generateError('Неподходящие данные (символы)');
        data.parentElement.insertBefore(error, data);
    }
}

//проверка индекса
function validateZip(zip) {
    var regex = /^[\d]([-\d]){3,9}/;
    if (!regex.test(zip.value)) {
        var error = generateError('Неподходящие данные (символы)');
        zip.parentElement.insertBefore(error, zip);
    }
}

//проверка телефонных кодов
function validateCode(code) {
    var regex = /^([-+\d]){2,5}$/;
    if (!regex.test(code.value)) {
        var error = generateError('Неподходящие данные (символы)');
        code.parentElement.insertBefore(error, code);
    }
}

//проверка телефонного номера
function validateNumber(number) {
    var regex = /^[\d]([-\d]){4,9}$/;
    if (!regex.test(number.value)) {
        var error = generateError('Неподходящие данные (символы)');
        number.parentElement.insertBefore(error, number);
    }
}

//проверка длины коммента
function validateLength(text) {
    if (text.value.length > 255) {
        var error = generateError('Длина текста превышает 255 символов');
        text.parentElement.insertBefore(error, text);
    }
}

//проверка длины сообщения
function validateLengthMail(text) {
    if (text.value.length > 1000) {
        var error = generateError('Длина сообщения превышает 1000 символов');
        text.parentElement.insertBefore(error, text);
    }
}