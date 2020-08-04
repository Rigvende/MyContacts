//скрипт для редактирования контакта

"use strict";

//автозаполнение контакта по выбранному чекбоксу
function autofill() {
    var id = window.location.href.split("?")[1].split("=")[1];
    if (id != null && id.trim()) {
        var request = new XMLHttpRequest();
        request.onreadystatechange = function () {
            if (this.readyState === 4 && this.status === 200) {
                if (this.responseText != null && this.responseText.trim()) {
                    var person = JSON.parse(this.responseText);
                    document.querySelector('#idField').value = id;
                    if (person.photo_path != null && person.photo_path.trim()) {
                        document.querySelector('#searchImage').src = person.photo_path;
                    }
                    document.querySelector('#surnameField').value = person.surname;
                    document.querySelector('#nameField').value = person.name;
                    document.querySelector('#patronymicField').value = person.patronymic;
                    document.querySelector('#birthdayField').value = person.birthday;
                    var gender = document.querySelector('#' + person.gender);
                    if (!gender.checked) {
                        gender.checked = true;
                    }
                    document.querySelector('#statusField').value = person.status;
                    document.querySelector('#citizenshipField').value = person.citizenship;
                    document.querySelector('#siteField').value = person.website;
                    document.querySelector('#emailField').value = person.email;
                    document.querySelector('#workField').value = person.work;
                    document.querySelector('#countryField').value = person.country;
                    document.querySelector('#cityField').value = person.city;
                    document.querySelector('#addressField').value = person.address;
                    document.querySelector('#zipField').value = person.zipcode;

                    var th;
                    var tr;
                    var checkbox;
                    function createTd(text, tr) {
                        var td = document.createElement('td');
                        td.innerHTML = text;
                        tr.appendChild(td);
                    }

                    var phonesTable = document.querySelector("#phonesTable");
                    phonesTable.innerHTML = '';
                    th = document.createElement('tr');
                    phonesTable.appendChild(th);
                    createTd('<b>#</b>', th);
                    createTd('<b>Номер</b>', th);
                    createTd('<b>Тип</b>', th);
                    createTd('<b>Комментарий</b>', th);
                    for (var phone of person.phones) {
                        tr = document.createElement('tr');
                        phonesTable.appendChild(tr);
                        checkbox = '<label><input type="checkbox" value="' + phone.id_phone + '"/></label>';
                        var phoneNumber = phone.p_country + '(' + phone.p_operator + ')' + phone.p_number;
                        createTd(checkbox, tr);
                        createTd(phoneNumber, tr);
                        createTd(phone.p_type, tr);
                        createTd(phone.p_comments, tr);
                    }

                    var attachTable = document.querySelector("#attachmentsTable");
                    attachTable.innerHTML = '';
                    th = document.createElement('tr');
                    attachTable.appendChild(th);
                    createTd('<b>#</b>', th);
                    createTd('<b>Имя</b>', th);
                    createTd('<b>Загружено</b>', th);
                    createTd('<b>Комментарий</b>', th);
                    for (var attach of person.attachments) {
                        tr = document.createElement('tr');
                        attachTable.appendChild(tr);
                        checkbox = '<label><input type="checkbox" value="' + attach.id_attachment + '"/></label>';
                        createTd(checkbox, tr);
                        createTd(attach.a_path, tr);
                        createTd(attach.a_date, tr);
                        createTd(attach.a_comments, tr);
                    }
                }
            }
        }
        request.open('GET', 'http://localhost:8080/view_war/contacts/' + id);
        request.send();
    }
}

autofill();

//отмена создания/редактирования
var cancelEdit = document.querySelector('#cancelEdit');
cancelEdit.addEventListener('click', function () {
    document.location.href = "../index.html";
})
