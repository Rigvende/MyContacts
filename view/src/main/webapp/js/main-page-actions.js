// function for main page generation

"use strict";
var checkboxes = [];

//генерация таблички с контактами с пагинацией:
function loadContacts() {
    var request = new XMLHttpRequest();
    request.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            var contacts = JSON.parse(this.responseText);
            var table = document.querySelector("#contactsList");
            var pagination = document.querySelector("#pagination");
            var notesOnPage = 10;
            var size = Math.ceil(contacts.length / notesOnPage);

            var showActive = (function () {
                var active;
                return function (item) {
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
                        var checkbox = '<label><input type="checkbox" class="checkbox" value="' + note.id + '"/></label>';
                        var editLink = '<a href="html/contactForm.html" class="buttonLink" id="' + note.id + '">'
                            + note.surname + ' ' + note.name + ' ' + note.patronymic + '</a>';
                        var location = note.country + ' ' + note.city + ' ' + note.address;
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
            checkCheckboxes();

            for (var item of items) {
                item.addEventListener('click', function () {
                    showActive(this);
                    checkCheckboxes();
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

//загрузка таблицы при старте:
loadContacts();


//кнопки меню:

//создать
var createContact = document.querySelector('#createContact');
createContact.addEventListener('click', function () {
    document.location.href = "html/contactForm.html";
})

//поиск
var searchContact = document.querySelector('#searchContact');
var searchModal = document.querySelector('#searching');
searchContact.addEventListener('click', function () {
    searchModal.style.display = 'block';
})

//редактировать
var editBtn = document.querySelector('#editContact');
editBtn.addEventListener('click', handleEdit);

//удалить
var deleteBtn = document.querySelector('#deleteContact');
deleteBtn.addEventListener('click', handleDelete);

//почта
var mailBtn = document.querySelector('#sendEmail');
mailBtn.addEventListener('click', handleEmail);

//кнопка удаления во всплывающем окошке
var reallyDelete = document.querySelector('#deleteButton');
reallyDelete.addEventListener('click', deleteContact);

//всплывающие окошки
var deleteModal = document.querySelector('#messageDelete');
var errDeleteEdit = document.querySelector('#errorDeleteEdit');
var errEditMail = document.querySelector('#errorEditMail');
var counter = 0; //счетчик нажатых чекбоксов

function checkCheckboxes() {
    checkboxes = document.querySelectorAll(".checkbox");
    checkboxes.forEach(box => {
        box.addEventListener('change', event => {
            if (event.target.checked) {
                counter++;
            } else {
                counter--;
            }
        })
    })
}

//обработка нажатия кнопок, для которых нужны чекбоксы
function handleEmail() {
    var checkId = '';
    switch (counter) {
        case 1:
            checkboxes.forEach(box => {
                if (box.checked) {
                    checkId = box.value;
                }
            })
            document.location.href = "html/mailForm.html?id=" + checkId;
            break;
        case 0:
            document.location.href = "html/mailForm.html?id=" + checkId;
            break;
        default:
            errEditMail.style.display = 'block';
            break;
    }
}

function handleEdit() {
    switch (counter) {
        case 1:
            var checkId = '';
            checkboxes.forEach(box => {
                if (box.checked) {
                    checkId = box.value;
                }
            })
            document.location.href = "html/contactForm.html?id=" + checkId;
            break;
        case 0:
            errDeleteEdit.style.display = 'block';
            break;
        default:
            errEditMail.style.display = 'block';
            break;
    }
}

var checkIds = [];

function handleDelete() {
    if (counter > 0) {
        checkboxes.forEach(box => {
            if (box.checked) {
                checkIds.push(box.getAttribute('value'));
            }
        })
        deleteModal.style.display = 'block';
    } else {
        errDeleteEdit.style.display = 'block';
    }
}

function deleteContact() {
    var request = new XMLHttpRequest();
    var ids = '';
    checkIds.forEach(id => {
        ids = ids + id + " ";
    })
    ids = 'ids=' + encodeURIComponent(ids);
    request.open("post", "http://localhost:8080/view_war/delete", true);
    request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    request.send(ids);
    loadContacts();
}

//закрытие окошек
var closeBtn = document.querySelectorAll('.close');

closeBtn.forEach(btn => {
    btn.addEventListener('click', () => {
        errEditMail.style.display = "none";
        errDeleteEdit.style.display = "none";
        deleteModal.style.display = "none";
        searchModal.style.display = "none";
        alert(checkIds.length);
        checkIds = [];
    })
})

window.onclick = function (event) {
    if (event.target === errEditMail ||
        event.target === errDeleteEdit ||
        event.target === deleteModal ||
        event.target === searchModal) {
        errEditMail.style.display = "none";
        errDeleteEdit.style.display = "none";
        deleteModal.style.display = "none";
        searchModal.style.display = "none";
        checkIds = [];
    }
}