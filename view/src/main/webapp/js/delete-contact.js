//удаление контактов

"use strict";

var checkboxes;
//когда выбираем чекбоксы, их id сохраняются в массив
var trigger = document.querySelectorAll(".checkbox");
trigger.forEach(box => {
    box.addEventListener('click', () => {
        checkboxes.push(box.getAttribute('id'));
    })
})

var reallyDelete = document.querySelector("#deleteButton");
reallyDelete.addEventListener('click', function () {

})

//потом нажимается delete - всплывает окошко с подтверждением
//если да - вызываем deleteContacts() и передаем ей чекбоксы

function deleteContacts() {
    //запихиваем в json айди чекбоксов и передаем в запросе

    var xhttp = new XMLHttpRequest();
    xhttp.open("POST", "http://localhost:8080/view_war/delete", true);
    xhttp.send();

    //очистить чекбоксы
    checkboxes = null;
    //вернуться к списку
    loadContacts();
}