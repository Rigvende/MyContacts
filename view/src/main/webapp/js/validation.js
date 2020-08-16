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

//проверка заполненности обязательного поля
function checkFieldPresence (field) {
    if (!field.value) {
        var error = generateError('Заполните это поле');
        field.parentElement.insertBefore(error, field);
        return false;
    }
    return true;
}

//проверка имени, фамилии
function validateName(name) {
    validateMiniLength(name);
    if (checkFieldPresence(name)) {
        var regex = /^[A-Za-zА-Яа-яЁё]+([\s-]+[A-Za-zА-Яа-яЁё]+)?$/;
        if (!regex.test(name.value)) {
            var error = generateError('Неподходящие данные (символы)');
            name.parentElement.insertBefore(error, name);
        }
    }
}

//проверка отчества
function validatePatronymic(patronymic) {
    validateMiniLength(patronymic);
    if (patronymic.value !== null && patronymic.value.trim()) {
        var regex = /^[A-Za-zА-Яа-яЁё]+$/;
        if (!regex.test(patronymic.value)) {
            var error = generateError('Неподходящие данные (символы)');
            patronymic.parentElement.insertBefore(error, patronymic);
        }
    }
}

//проверка даты
function validateBirthday(birthday) {
    var birth = Date.parse(birthday.value);
    var dateStart = new Date(1920, 1, 1);
    var dateEnd = new Date();
    if (birth < dateStart || birth > dateEnd) {
        var error = generateError('Неподходящая дата рождения');
        birthday.parentElement.insertBefore(error, birthday);
    }
}

//проверка сайта
function validateSite(site) {
    validateLength(site);
    if (site.value !== null && site.value.trim()) {
        var regex = /([-\w]+.)?([-\w]+).\w+(?:.\w+)?\/?.*/;
        if (!regex.test(site.value) || site.value.length < 5) {
            var error = generateError('Неподходящие данные (символы)');
            site.parentElement.insertBefore(error, site);
        }
    }
}

//проверка email
function validateEmail(mail) {
    validateLength(mail);
    if (mail.value !== null && mail.value.trim()) {
        var regex = /^[-\w.]+@([A-z0-9][-A-z0-9]+\.)+[A-z]{2,4}$/;
        if (!regex.test(mail.value) || mail.value.length < 10) {
            var error = generateError('Неподходящие данные для e-mail');
            mail.parentElement.insertBefore(error, mail);
        }
    }
}

//проверка полей
function validateData(data) {
    validateMiniLength(data);
    if (data.value !== null && data.value.trim()) {
        var regex = /^[-)\\(.,A-Za-zА-Яа-яЁё\d\s\/]+$/;
        if (!regex.test(data.value)) {
            var error = generateError('Неподходящие данные (символы)');
            data.parentElement.insertBefore(error, data);
        }
    }
}

//проверка индекса
function validateZip(zip) {
    if (zip.value !== null && zip.value.trim()) {
        var regex = /^[\d]+[-]?[\d]+$/;
        if (!regex.test(zip.value) || zip.value.length < 5 || zip.value.length > 9) {
            var error = generateError('Неподходящие данные (символы) или превышена длина');
            zip.parentElement.insertBefore(error, zip);
        }
    }
}

//проверка телефонных кодов
function validateCode(code) {
    if (code.value !== null && code.value.trim()) {
        var regex = /^([-+\d]){2,5}$/;
        if (!regex.test(code.value)) {
            var error = generateError('Неподходящие данные (символы)');
            code.parentElement.insertBefore(error, code);
        }
    }
}

//проверка телефонного номера
function validateNumber(number) {
    if (number.value !== null && number.value.trim()) {
        var regex = /^[\d]([-\d]){4,9}$/;
        if (!regex.test(number.value)) {
            var error = generateError('Неподходящие данные');
            number.parentElement.insertBefore(error, number);
        }
    }
}

//проверка длины коммента
function validateLength(text) {
    if (text.value.length > 255) {
        var error = generateError('Длина строки превышает 255 символов');
        text.parentElement.insertBefore(error, text);
    }
}

//проверка длины коммента
function validateMiniLength(text) {
    if (text.value.length > 45) {
        var error = generateError('Длина строки превышает 45 символов');
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

//проверка размера файла
function validateSize(file) {
    if (file.files[0].size > 5000 * 1024) {
        var error = generateError('Размер файла превышает 5 мБ');
        file.parentElement.insertBefore(error, file);
    }
}

//проверка id
function validateId(id) {
    var regex = /^[\d]+$/;
    return regex.test(id);
}