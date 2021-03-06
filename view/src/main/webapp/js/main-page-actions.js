// function for main page generation

"use strict";

var checkboxes = []; //массив всех чекбоксов

//заполнение таблицы и пагинация
function fillTable(contacts) {
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
                var editLink = '<a href="html/contactForm.html?id=' + note.id + '" class="buttonLink">'
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

    function createTd(text, tr) {
        var td = document.createElement('td');
        td.innerHTML = text;
        tr.appendChild(td);
    }

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
}

//генерация таблички с контактами с пагинацией:
function loadContacts() {
    var spinnerDiv = document.createElement('div');
    spinnerDiv.classList.add('spinner');
    showSpinner(spinnerDiv);
    var request = new XMLHttpRequest();
    request.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            spinnerDiv.style.display = 'none';
            var contacts = JSON.parse(this.responseText);
            fillTable(contacts);
        }
    };
    request.open("GET", "contacts/", true);
    request.send();
}

//загрузка таблицы при старте:
loadContacts();

///////////////////////////////////////////////////////////////
//КНОПКИ МЕНЮ:
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
var deleteMsg = document.querySelector('#deleteMsg');
var modalContent = document.querySelectorAll('.modalContent');
var checkCounter = 0; //счетчик нажатых чекбоксов

//счетчик заполненных чекбоксов
function checkCheckboxes() {
    checkboxes = document.querySelectorAll('.checkbox');
    checkboxes.forEach(box => {
        box.addEventListener('change', event => {
            if (event.target.checked) {
                checkCounter++;
            } else {
                checkCounter--;
            }
        })
    })
}

//поменять фон для сообщений об ошибках
function changeBackground() {
    modalContent.forEach(modal => {
        modal.style.backgroundColor = 'Chocolate';
    })
}

///////////////////////////////////////////////////
//обработка нажатия кнопок, для которых нужны чекбоксы:

//ПОЧТА
function handleEmail() {
    var checkId = '';
    switch (checkCounter) {
        case 1:
            checkboxes.forEach(box => {
                if (box.checked) {
                    checkId = box.value;
                    box.checked = false;
                }
            })
            checkCounter = 0;
            document.location.href = "html/mailForm.html?id=" + checkId;
            break;
        case 0:
            changeBackground();
            errDeleteEdit.style.display = 'block';
            break;
        default:
            changeBackground();
            errEditMail.style.display = 'block';
            break;
    }
}

/////////////////////////////////////////////////////
//РЕДАКТИРОВАНИЕ
function handleEdit() {
    switch (checkCounter) {
        case 1:
            var checkId = '';
            checkboxes.forEach(box => {
                if (box.checked) {
                    checkId = box.value;
                    box.checked = false;
                }
            })
            checkCounter = 0;
            document.location.href = "html/contactForm.html?id=" + checkId;
            break;
        case 0:
            changeBackground();
            errDeleteEdit.style.display = 'block';
            break;
        default:
            changeBackground();
            errEditMail.style.display = 'block';
            break;
    }
}

///////////////////////////////////////////////
//УДАЛЕНИЕ
//массив айдишников для удаления
var checkIds = [];

//нажатие кнопки меню
function handleDelete() {
    if (checkCounter > 0) {
        checkboxes.forEach(box => {
            if (box.checked) {
                checkIds.push(box.value);
            }
        })
        deleteModal.style.display = 'block';
    } else {
        changeBackground();
        errDeleteEdit.style.display = 'block';
    }
}

//нажатие кнопки подтверждения
function deleteContact(event) {
    event.preventDefault();
    var request = new XMLHttpRequest();
    var ids = '';
    checkIds.forEach(id => {
        ids = ids + id + " ";
    })
    ids = 'ids=' + encodeURIComponent(ids);
    request.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            checkCounter = 0;
            checkIds = [];
            deleteMsg.querySelector('p').innerHTML = success;
            deleteMsg.style.display = 'block';
        }
        else if (this.status === 404 || this.status === 500) {
            checkCounter = 0;
            checkIds = [];
            changeBackground();
            deleteMsg.querySelector('p').innerHTML = fail;
            deleteMsg.style.display = 'block';
        }
    }
    request.open("post", "delete", true);
    request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    request.send(ids);
    deleteModal.style.display = 'none';
}

//элементы окошка подтверждения удаления
var success = "Контакты успешно удалены!";
var fail = "Что-то пошло не так";
var oks = document.querySelectorAll('.ok');
oks.forEach(ok => {
    ok.addEventListener('click', loadContacts);
})

//////////////////////////////////////////////////////////////
//закрытие модальных окошек
var closeBtn = document.querySelectorAll('.close');

function hideModals() {
    modalContent.forEach(modal => {
        modal.style.backgroundColor = '#31c3ff';
    })
    errEditMail.style.display = "none";
    errDeleteEdit.style.display = "none";
    deleteModal.style.display = "none";
    searchModal.style.display = "none";
    deleteMsg.style.display = "none";
    checkIds = [];
}

//по кнопке
closeBtn.forEach(btn => {
    btn.addEventListener('click', () => {
        hideModals();
    })
})

//по фону
window.onclick = function (event) {
    if (event.target === errEditMail ||
        event.target === errDeleteEdit ||
        event.target === deleteModal ||
        event.target === searchModal) {
        hideModals();
    }
    if (event.target === deleteMsg) {
        loadContacts();
        hideModals();
    }
}

//показ спиннера во время формирования таблицы контактов
function showSpinner(spinnerDiv) {
    var content = document.querySelector('.content');
    var spinner = document.createElement('img');
    spinner.src = 'image/spinner.svg';
    spinnerDiv.appendChild(spinner);
    var spinnerMsg = document.createElement('label');
    spinnerMsg.textContent = 'Ожидайте...';
    spinnerDiv.appendChild(spinnerMsg);
    content.insertAdjacentElement('afterbegin', spinnerDiv);
}