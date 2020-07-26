// function for loading contacts on the main page

"use strict";

function loadContacts() {
    var request = new XMLHttpRequest();
    request.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            var contacts = JSON.parse(this.responseText);
            var table = document.querySelector("#contactsList");
            var pagination = document.querySelector("#pagination");
            var notesOnPage = 2;
            var size = Math.ceil(contacts.length / notesOnPage);

            var showActive = (function() {
                var active;
                return function(item) {
                    if (active) {
                        active.classList.remove('active');
                    }
                    active = item;
                    item.classList.add('active');

                    var pageNum = +item.innerHTML;
                    var start = (pageNum - 1) * notesOnPage;
                    var end = start + notesOnPage;
                    var notes = contacts.slice(start, end);

                    table.innerHTML = '';
                    var th = document.createElement('tr');
                    table.appendChild(th);
                    createTd('<b>#</b>', th);
                    createTd('<b>Имя контакта</b>', th);
                    createTd('<b>Дата рождения</b>', th);
                    createTd('<b>Адрес</b>', th);
                    createTd('<b>Место работы</b>', th);

                    for (var note of notes) {
                        var tr = document.createElement('tr');
                        table.appendChild(tr);
                        var checkbox = '<label><input type="checkbox" value="' + note.id + '"/></label>';
                        var editLink = '<a href="html/contactForm.html" class="buttonLink" id="' + note.id + '">'
                                       + note.surname + ' ' + note.name + ' ' + note.patronymic + '</a>';
                        var location = note.country + ', ' + note.city + ', ' + note.address;
                        createTd(checkbox, tr);
                        createTd(editLink, tr);
                        createTd(note.birthday, tr);
                        createTd(location, tr);
                        createTd(note.work, tr);
                    }
                };
            }());

            var items = [];
            pagination.innerHTML = '';
            for (var i = 1; i <= size; i++) {
                var li = document.createElement('li');
                li.innerHTML = i;
                pagination.appendChild(li);
                items.push(li);
            }

            showActive(items[0]);

            for (var item of items) {
                item.addEventListener('click', function () {
                    showActive(this);
                });
            }

            function createTd(text, tr) {
                var td = document.createElement('td');
                td.innerHTML = text;
                tr.appendChild(td);
            }
        }
    };
    request.open("GET", "http://localhost:8080/view_war/contacts/", true);
    request.send();
}

loadContacts();