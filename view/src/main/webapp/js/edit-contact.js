//скрипт для редактирования контакта

"use strict";

//форма и поля
var th;
var tr;
var checkbox;
var phoneCheckboxes = [];
var attachmentCheckboxes = [];
var form = document.querySelector('#contactSave');
var phonesTable = document.querySelector("#phonesTable");
var attachTable = document.querySelector("#attachmentsTable");

var idContact = document.querySelector('#idField');
var idPhoto = document.querySelector('#idPhoto');
var searchImage = document.querySelector('#searchImage');
var surnameField = document.querySelector('#surnameField');
var nameField = document.querySelector('#nameField');
var patronymicField = document.querySelector('#patronymicField');
var birthdayField = document.querySelector('#birthdayField');
var statusField = document.querySelector('#statusField');
var citizenshipField = document.querySelector('#citizenshipField');
var siteField = document.querySelector('#siteField');
var emailField = document.querySelector('#emailField');
var workField = document.querySelector('#workField');
var countryField = document.querySelector('#countryField');
var cityField = document.querySelector('#cityField');
var addressField = document.querySelector('#addressField');
var zipField = document.querySelector('#zipField');

//обязательные для заполнения поля
var fields = [];
fields.push(nameField);
fields.push(surnameField);

//создание ряда таблицы
function createTd(text, tr) {
    var td = document.createElement('td');
    td.innerHTML = text;
    tr.appendChild(td);
}

function createTableHead () {
    phonesTable.innerHTML = '';
    th = document.createElement('tr');
    phonesTable.appendChild(th);
    createTd('<b>#</b>', th);
    createTd('<b>Номер</b>', th);
    createTd('<b>Тип</b>', th);
    createTd('<b>Комментарий</b>', th);
    attachTable.innerHTML = '';
    th = document.createElement('tr');
    attachTable.appendChild(th);
    createTd('<b>#</b>', th);
    createTd('<b>Имя</b>', th);
    createTd('<b>Загружено</b>', th);
    createTd('<b>Комментарий</b>', th);
}

//автозаполнение контакта по выбранному чекбоксу
function autofill() {
    createTableHead();
    var x = window.location.href.split("?id=");
    if (x.length === 2) {
        var id = x[1];
        if (id != null && id.trim()) {
            var request = new XMLHttpRequest();
            request.onreadystatechange = function () {
                if (this.readyState === 4 && this.status === 200) {
                    if (this.responseText != null && this.responseText.trim()) {
                        var person = JSON.parse(this.responseText);
                        idContact.value = id;
                        if (person.photo_path != null && person.photo_path.trim()) {
                            searchImage.src = person.photo_path;
                        } else {
                            searchImage.src = "../image/user_no_photo.png";
                        }
                        idPhoto.value = person.idPhoto;
                        surnameField.value = person.surname;
                        nameField.value = person.name;
                        patronymicField.value = person.patronymic;
                        birthdayField.value = person.birthday;
                        var gender = document.querySelector('#' + person.gender);
                        if (!gender.checked) {
                            gender.checked = true;
                        }
                        statusField.value = person.status;
                        citizenshipField.value = person.citizenship;
                        siteField.value = person.website;
                        emailField.value = person.email;
                        workField.value = person.work;
                        countryField.value = person.country;
                        cityField.value = person.city;
                        addressField.value = person.address;
                        zipField.value = person.zipcode;

                        if (person.phones) {
                            for (var phone of person.phones) {
                                tr = document.createElement('tr');
                                phonesTable.appendChild(tr);
                                checkbox = '<label><input type="checkbox" class="checkbox" value="' + phone.id_phone + '"/></label>';
                                var phoneNumber = phone.p_country + '/' + phone.p_operator + '/' + phone.p_number;
                                createTd(checkbox, tr);
                                createTd(phoneNumber, tr);
                                createTd(phone.p_type, tr);
                                createTd(phone.p_comments, tr);
                            }
                        }
                        phoneCheckboxes = phonesTable.querySelectorAll('.checkbox');

                        if (person.attachments) {
                            for (var attach of person.attachments) {
                                tr = document.createElement('tr');
                                attachTable.appendChild(tr);
                                checkbox = '<label><input type="checkbox" class="checkbox" value="' + attach.id_attachment + '"/></label>';
                                createTd(checkbox, tr);
                                createTd(attach.a_path, tr);
                                createTd(attach.a_date, tr);
                                createTd(attach.a_comments, tr);
                            }
                        }
                        attachmentCheckboxes = attachTable.querySelectorAll('.checkbox');
                    }
                }

            }
            request.open('GET', '../contacts/' + id);
            request.send();
        }
    }
}

autofill();

//отмена создания/редактирования
var cancelEdit = document.querySelector('#cancelEdit');
cancelEdit.addEventListener('click', function () {
    document.location.href = "../index.html";
})

//модальное окошко после сохранения данных
var modalSend = document.querySelector('#contactSaveMsg');
var modalBack = document.querySelector('.modalContentMsg');
var success = "Контакт успешно сохранен";
var fail = "Что-то пошло не так...";

var returnBtn = document.querySelector('#returnButton');
returnBtn.addEventListener('click', function () {
    document.location.href = "../index.html";
})

/////////////////////////////////////////////////
//закрытие модальных окошек
var closeBtn = document.querySelectorAll('.close');

closeBtn.forEach(btn => {
    btn.addEventListener('click', () => {
        modalSend.style.display = "none";
        popupAttachment.style.display = "none";
        popupPhone.style.display = "none";
        popupPhoto.style.display = "none";
        addPhoneBtn.removeEventListener('click', editPhones);
        addPhoneBtn.addEventListener('click', addPhone);
        addAttachBtn.removeEventListener('click', editAttachments);
        addAttachBtn.addEventListener('click', addAttachment);
    })
})

window.onclick = function (event) {
    if (event.target === popupPhone
        || event.target === popupAttachment
        || event.target === popupPhoto
        || event.target === modalSend) {
        popupPhone.style.display = "none";
        popupAttachment.style.display = "none";
        popupPhoto.style.display = "none";
        modalSend.style.display = "none";
        addPhoneBtn.removeEventListener('click', editPhones);
        addPhoneBtn.addEventListener('click', addPhone);
        addAttachBtn.removeEventListener('click', editAttachments);
        addAttachBtn.addEventListener('click', addAttachment);
    }
}
////////////////////////////////////////////////
//редактирование(создание) контакта
var errors;
form.addEventListener('submit', function (event) {
    event.preventDefault();
    removeValidation();
    checkFieldsPresence(form, fields);
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
            } else if (this.status === 404 || this.status === 500) {
                spinnerDiv.style.display = 'none';
                modalSend.querySelector('p').innerHTML = fail;
                modalBack.style.backgroundColor = 'Chocolate';
                modalSend.style.display = 'block';
            }
        }
        var postData = createPostData();
        request.open("POST", "../contacts/", true);
        request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        request.send(postData);
    }
})

//формирование данных запроса
function createPostData() {
    var id = idContact.value;
    var name = nameField.value;
    var surname = surnameField.value;
    var patronymic = patronymicField.value;
    var birthday = birthdayField.value;
    var gender = document.querySelector('input[name="gender"]:checked').value;
    var citizenship = citizenshipField.value;
    var status = statusField.value;
    var website = siteField.value;
    var email = emailField.value;
    var work = workField.value;
    var country = countryField.value;
    var city = cityField.value;
    var location = addressField.value;
    var zipcode = zipField.value;
    var photoId = idPhoto.value;
    var postData = 'id=' + id;
    postData += '&name=' + encodeURIComponent(name);
    postData += '&surname=' + encodeURIComponent(surname);
    postData += '&patronymic=' + encodeURIComponent(patronymic);
    postData += '&birthday=' + encodeURIComponent(birthday);
    postData += '&gender=' + encodeURIComponent(gender);
    postData += '&citizenship=' + encodeURIComponent(citizenship);
    postData += '&status=' + encodeURIComponent(status);
    postData += '&website=' + encodeURIComponent(website);
    postData += '&email=' + encodeURIComponent(email);
    postData += '&work=' + encodeURIComponent(work);
    postData += '&country=' + encodeURIComponent(country);
    postData += '&city=' + encodeURIComponent(city);
    postData += '&location=' + encodeURIComponent(location);
    postData += '&zipcode=' + encodeURIComponent(zipcode);
    postData += '&photoId=' + encodeURIComponent(photoId);
    return postData;
}

//////////////////////////
//вызов всплывающего окошка для загрузки фото
var popupPhoto = document.querySelector('#messagePhoto');
var editPhoto = document.querySelector('.popupPhoto');

editPhoto.addEventListener('click', function () {
    popupPhoto.style.display = "block";
})

//загрузка фото по умолчанию, если его нет
var contactPhoto = document.querySelector('#searchImage');
var checkedSrc = contactPhoto.src;

//предпросмотр выбранного фото без сохранения на сервер
var photoSrc;

function readURL() {
    if (this.files && this.files[0]) {
        var reader = new FileReader();
        $(reader).on('load', function (e) {
            photoSrc = e.target.result;
            $('#searchImage').attr('src', photoSrc);
        });
        reader.readAsDataURL(this.files[0]);
    }
}

$("#photo").change(readURL);

//сброс выбора к первоначальному фото
var cancel = document.querySelector("#photoButton");
cancel.addEventListener('click', function () {
    contactPhoto.src = checkedSrc;
    document.querySelector('#photo').value = '';
    popupPhoto.style.display = "none";
})

//////////////////////////////
//обработка приложений
var popupAttachment = document.querySelector('#messageAttachment');
var createAttachment = document.querySelector('#createAttachment');
var editAttachment = document.querySelector('#editAttachment');
var deleteAttachment = document.querySelector('#deleteAttachment');
var ATable = document.querySelector('#attachmentsTable');
var attachmentComment = document.querySelector("#attachmentComment");
var attachment = document.querySelector('#attachment');

createAttachment.addEventListener('click', () => {
    popupAttachment.style.display = "block";
})

deleteAttachment.addEventListener('click', () => {
    deleteRows(ATable, attachmentCheckboxes);
    attachmentCheckboxes = [];
    attachmentCheckboxes = attachTable.querySelectorAll('.checkbox');
})

//добавляем новое приложение
var addAttachBtn = document.querySelector('#attachmentButton');

function getToday() {
    var today = new Date();
    var dd = String(today.getDate()).padStart(2, '0');
    var mm = String(today.getMonth() + 1).padStart(2, '0');
    var yyyy = today.getFullYear();
    today = yyyy + "-" + mm + "-" + dd;
    return today;
}

function addAttachment() {
//создаем hidden html с полями
    tr = document.createElement('tr');
    attachTable.appendChild(tr);
    checkbox = '<label><input type="checkbox" class="checkbox" value=""/></label>';
    var today = getToday();
    createTd(checkbox, tr);
    createTd(attachment.value, tr);
    createTd(today, tr);
    createTd(attachmentComment.value, tr);
    attachmentCheckboxes = [];
    attachmentCheckboxes = attachTable.querySelectorAll('.checkbox');
    popupAttachment.style.display = 'none';
}

addAttachBtn.addEventListener('click', addAttachment)

var chboxAttachment;

//редактирование по чекбоксу
editAttachment.addEventListener('click', () => {
    var checkedBoxes = [];
    attachmentCheckboxes.forEach(box => {
        if (box.checked) {
            checkedBoxes.push(box);
        }
    });
    if (checkedBoxes.length === 1) {
        chboxAttachment = checkedBoxes[0];
        var tr = chboxAttachment.closest('tr');
        var tds = tr.querySelectorAll('td');
        attachment.innerHTML = tds[1].innerHTML;
        attachmentComment.value = tds[3].innerHTML;
        popupAttachment.style.display = 'block';
        addAttachBtn.removeEventListener('click', addAttachment);
        addAttachBtn.addEventListener('click', editAttachments);
    }
})

function editAttachments() {
    var tr = chboxAttachment.closest('tr');
    var tds = tr.querySelectorAll('td');
    tds[1].innerHTML = attachment.value;
    tds[2].innerHTML = getToday();
    tds[3].innerHTML = attachmentComment.value;
    popupAttachment.style.display = 'none';
    chboxAttachment.checked = false;
    addPhoneBtn.removeEventListener('click', editAttachments);
    addPhoneBtn.addEventListener('click', addAttachment);
}

////////////////////////////////
//обработка телефонов
var popupPhone = document.querySelector('#messagePhone');
var createPhone = document.querySelector('#createPhone');
var editPhone = document.querySelector('#editPhone');
var deletePhone = document.querySelector('#deletePhone');
var PTable = document.querySelector('#phonesTable');

var phoneCountry = document.querySelector("#phoneCountry");
var phoneOperator = document.querySelector("#phoneOperator");
var phoneNumber = document.querySelector("#phoneNumber");
var phoneType = document.querySelector("#phoneType");
var phoneComment = document.querySelector("#phoneComment");

createPhone.addEventListener('click', () => {
    popupPhone.style.display = "block";
})

deletePhone.addEventListener('click', () => {
    deleteRows(PTable, phoneCheckboxes);
    phoneCheckboxes = [];
    phoneCheckboxes = phonesTable.querySelectorAll('.checkbox');
})

//добавляем новый телефон
var addPhoneBtn = document.querySelector('#phoneButton');

function addPhone() {
//создаем hidden html с полями
    tr = document.createElement('tr');
    phonesTable.appendChild(tr);
    checkbox = '<label><input type="checkbox" class="checkbox" value=""/></label>';
    var number = phoneCountry.value + '/' + phoneOperator.value + '/' + phoneNumber.value;
    createTd(checkbox, tr);
    createTd(number, tr);
    createTd(phoneType.value, tr);
    createTd(phoneComment.value, tr);
    phoneCheckboxes = [];
    phoneCheckboxes = phonesTable.querySelectorAll('.checkbox');
    popupPhone.style.display = 'none';
}

addPhoneBtn.addEventListener('click', addPhone);

var chboxPhoto;

//редактирование по чекбоксу
editPhone.addEventListener('click', () => {
    var checkedBoxes = [];
    phoneCheckboxes.forEach(box => {
        if (box.checked) {
            checkedBoxes.push(box);
        }
    });
    if (checkedBoxes.length === 1) {
        chboxPhoto = checkedBoxes[0];
        var tr = chboxPhoto.closest('tr');
        var tds = tr.querySelectorAll('td');
        var splitNumber = tds[1].innerHTML.split("/");
        phoneCountry.value = splitNumber[0];
        phoneOperator.value = splitNumber[1];
        phoneNumber.value = splitNumber[2];
        phoneType.value = tds[2].innerHTML;
        phoneComment.value = tds[3].innerHTML;
        popupPhone.style.display = 'block';
        addPhoneBtn.removeEventListener('click', addPhone);
        addPhoneBtn.addEventListener('click', editPhones);
    }
})

function editPhones() {
    var tr = chboxPhoto.closest('tr');
    var tds = tr.querySelectorAll('td');
    tds[1].innerHTML = phoneCountry.value + '/' + phoneOperator.value + '/' + phoneNumber.value;
    tds[2].innerHTML = phoneType.value;
    tds[3].innerHTML = phoneComment.value;
    popupPhone.style.display = 'none';
    chboxPhoto.checked = false;
    addPhoneBtn.removeEventListener('click', editPhones);
    addPhoneBtn.addEventListener('click', addPhone);
}

//удаление строк таблиц по чекбоксам
function deleteRows(table, checkboxes) {
    var i = checkboxes.length;
    alert(checkboxes.length);
    while (i-- && i >= 0) {
        var chbox = checkboxes[i];
        if (chbox.checked === true) {
            var tr = chbox.closest('tr');
            table.deleteRow(tr.rowIndex);
        }
    }
}


//валидация полей //todo
// var errors;
//
// function validate() {
//     removeValidation();
//     var form = document.querySelector('#searchForm');
//     var fields = form.querySelectorAll('.field');
//     checkInappropriate(form, fields);
//     errors = form.querySelectorAll('.error');
// }
//

