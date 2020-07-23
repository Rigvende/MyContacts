"use strict";

//todo написать аналогично созданию, но с автозаполнением полей



function editContact() {

    //todo создать попап об успешном сохранении
    //вернуться к списку
}

function getContact(id) {
    var request = new XMLHttpRequest();
    request.onreadystatechange = function () {
        if (request.readyState == 4) {
            if (request.status == 200) {
                var person = JSON.parse(request.responseText);

                if(person.photo != null && person.photo !== '') {
                    document.getElementById('contactPhoto').src = person.photo;
                }
                document.getElementById('contactSurname').value = person.surname;
                document.getElementById('contactName').value = person.name;
                document.getElementById('contactPatronymic').value = person.patronymic;
                document.getElementById('contactBirthday').value = person.birthday;
                var gender = document.getElementById(person.gender);
                if (!gender.checked) {
                    gender.checked = true;
                }
                document.getElementById('contactStatus').value = person.status;
                document.getElementById('contactCitizenship').value = person.citizenship;
                document.getElementById('contactWebsite').value = person.website;
                document.getElementById('contactEmail').value = person.email;
                document.getElementById('contactWork').value = person.work;
                document.getElementById('contactCountry').value = person.country;
                document.getElementById('contactCity').value = person.city;
                document.getElementById('contactAddress').value = person.address;
                document.getElementById('contactZipcode').value = person.zipcode;

                var phonesTable = document.querySelector("#phonesTable");
                phonesTable.innerHTML = '';
                var tr;
                var checkbox;
                for (var phone of person.phones) {
                    tr = document.createElement('tr');
                    phonesTable.appendChild(tr);

                    checkbox = '<label><input type="checkbox" value="' + phone.id + '"/></label>';
                    var phoneNumber = phone.countryCode + '(' + phone.operatorCode + ')' + phone.number;
                    createTd(checkbox, tr);
                    createTd(phoneNumber, tr);
                    createTd(phone.type, tr);
                    createTd(phone.comment, tr);
                }

                var attachTable = document.querySelector("#attachTable");
                attachTable.innerHTML = '';
                for (var attach of person.attachments) {
                    tr = document.createElement('tr');
                    attachTable.appendChild(tr);

                    checkbox = '<label><input type="checkbox" value="' + attach.id + '"/></label>';
                    createTd(checkbox, tr);
                    createTd(attach.name, tr);
                    createTd(attach.loadDate, tr);
                    createTd(attach.comment, tr);
                }

                function createTd(text, tr) {
                    var td = document.createElement('td');
                    td.innerHTML = text;
                    tr.appendChild(td);
                }
            }
        }
    }
    request.open('GET', 'http://localhost:8080/contacts/' + id);
    request.send();
}

var links = document.querySelectorAll(".editLink");
for (var nameLink of links) {
    nameLink.addEventListener('click', function () {
        var id = +this.getAttribute('id');
        getContact(id);
        //берем контакста из бд по id ссылки
        //загружаем contactForm со значениями из контакта
    });
}

