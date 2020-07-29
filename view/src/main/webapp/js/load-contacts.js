// function for main page generation

"use strict";

//обработчики событий для кнопок меню:
var createContact = document.querySelector('#createContact');
createContact.addEventListener('click', function () {
    document.location.href = "html/contactForm.html";
})
// var editContact = document.querySelector('#editContact');
// editContact.addEventListener('click', function () {
//     document.location.href = "html/contactForm.html";
// })
var sendEmail = document.querySelector('#sendEmail');
sendEmail.addEventListener('click', function () {
    document.location.href = "html/mailForm.html";
})
var searchContact = document.querySelector('#searchContact');
searchContact.addEventListener('click', function () {
    document.location.href = 'html/searchForm.html';
})

//всплывающие окошки
var deleteBtn = document.querySelector("#deleteContact");
var editBtn = document.querySelector("#editContact");
var modalErr = document.querySelector('#errorDeleteEdit');
var modal = document.querySelector('#messageDelete');
var closeBtn = document.querySelectorAll('.close');
var checkboxes = document.querySelectorAll("input[type='checkbox']");

deleteBtn.addEventListener('click', showModals);
editBtn.addEventListener('click', showModals);

function showModals() {
    var flag = false;
    checkboxes.forEach(box => {
        if (box.checked) {
            flag = true;
        }
    })
    if (flag) {
        modal.style.display = "block";//если это кнопка удалить fixme
        //document.location.href = "html/contactForm.html"; //если это кнопка редактировать
    } else {
        modalErr.style.display = "block";
    }
}

//закрытие окошек
closeBtn.forEach(btn => {
    btn.addEventListener('click', () => {
        modal.style.display = "none";
        modalErr.style.display = "none";
    })
})

window.onclick = function (event) {
    if (event.target === modal || event.target === modalErr) {
        modal.style.display = "none";
        modalErr.style.display = "none";
    }
}

//всплывающее окошко для кнопки удаления контакта:
// var modal = document.querySelector('#messageDelete');
// var btn = document.querySelector('#deleteContact');
// var closeBtn = document.querySelectorAll('.close');
// btn.addEventListener('click', function () {
//     modal.style.display = "block";
// })
// closeBtn.forEach(btn => {
//     btn.addEventListener('click', () => {
//         modal.style.display = "none";
//     })
// })
// window.onclick = function (event) {
//     if (event.target === modal) {
//         modal.style.display = "none";
//     }
// }

//генерация таблички с контактами с пагинацией:
function loadContacts() {
    var request = new XMLHttpRequest();
    request.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            var contacts = JSON.parse(this.responseText);
            var table = document.querySelector("#contactsList");
            var pagination = document.querySelector("#pagination");
            var notesOnPage = 2;
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

//загрузка таблицы при старте:
loadContacts();