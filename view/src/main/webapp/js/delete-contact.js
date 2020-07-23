function deleteContact(contactId) {
    "use strict";

    var xhttp = new XMLHttpRequest();
    xhttp.open("POST", "http://localhost:8080/" + contactId, true); //прописать урл к контроллеру удаления контактов
    xhttp.send();

    //todo создать попап об успешном завершении

    //вернуться к списку
    loadContacts();
}