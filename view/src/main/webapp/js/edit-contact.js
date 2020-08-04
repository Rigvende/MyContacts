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

                    // var phonesTable = document.querySelector("#phonesTable");
                    // phonesTable.innerHTML = '';
                    // var tr;
                    // var checkbox;
                    // for (var phone of person.phones) {
                    //     tr = document.createElement('tr');
                    //     phonesTable.appendChild(tr);
                    //
                    //     checkbox = '<label><input type="checkbox" value="' + phone.id + '"/></label>';
                    //     var phoneNumber = phone.countryCode + '(' + phone.operatorCode + ')' + phone.number;
                    //     createTd(checkbox, tr);
                    //     createTd(phoneNumber, tr);
                    //     createTd(phone.type, tr);
                    //     createTd(phone.comment, tr);
                    // }
                    //
                    // var attachTable = document.querySelector("#attachmentsTable");
                    // attachTable.innerHTML = '';
                    // for (var attach of person.attachments) {
                    //     tr = document.createElement('tr');
                    //     attachTable.appendChild(tr);
                    //
                    //     checkbox = '<label><input type="checkbox" value="' + attach.id + '"/></label>';
                    //     createTd(checkbox, tr);
                    //     createTd(attach.name, tr);
                    //     createTd(attach.loadDate, tr);
                    //     createTd(attach.comment, tr);
                    // }
                    //
                    // function createTd(text, tr) {
                    //     var td = document.createElement('td');
                    //     td.innerHTML = text;
                    //     tr.appendChild(td);
                    // }
                }
            }
        }
        request.open('GET', 'http://localhost:8080/view_war/contacts/' + id);
        request.send();
    }
}

autofill();

var createContact = document.querySelector('#cancelEdit');
createContact.addEventListener('click', function () {
    document.location.href = "../index.html";
})
