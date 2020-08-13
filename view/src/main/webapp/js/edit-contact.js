//скрипт для редактирования контакта

"use strict";

//форма
var th;
var tr;
var checkbox;
var phoneCheckboxes = [];
var attachmentCheckboxes = [];
var form = document.querySelector('#contactSave');
var phonesTable = document.querySelector("#phonesTable");
var attachTable = document.querySelector("#attachmentsTable");
var errDeleteEdit = document.querySelector('#errorDeleteEdit');
var errEdit = document.querySelector('#errorEdit');
var phoneCounter = 0;
var attachmentCounter = 0;
//поля
var idContact = document.querySelector('#idField');
var idPhoto = document.querySelector('#idPhoto');
var photo = document.querySelector('#photo');
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
var photoStatus;
var phoneCountry;
var phoneOperator;
var phoneNumber;
var phoneType;
var phoneComment;
var phoneId;
var phoneStatus;
var attachmentComment;
var attachmentId;
var attachmentStatus;
var attachmentFile;
var attachmentPath;
var loadDate;

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

function createTableHead() {
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

//добавление каждого телефона в скрытую форму
function addPhoneForm() {
    var divPhone = document.createElement('div');
    divPhone.classList.add('divPhone');
    divPhone.setAttribute('id', 'phone' + ++phoneCounter);
    divPhone.innerHTML = "<label>\n" +
        "            <input form=\"contactSave\" type=\"text\" id=\"phoneId\" name=\"phones[][phoneId]\" value=\"\" hidden>\n" +
        "        </label>\n" +
        "        <table>\n" +
        "            <tr>\n" +
        "                <td>\n" +
        "                    Код страны\n" +
        "                </td>\n" +
        "                <td><label>\n" +
        "                    <input form=\"contactSave\" class=\"field\" id=\"phoneCountry\" type=\"text\" name=\"phones[][countryCode]\" value=\"\"/>\n" +
        "                </label></td>\n" +
        "            </tr>\n" +
        "            <tr>\n" +
        "                <td>\n" +
        "                    Код оператора\n" +
        "                </td>\n" +
        "                <td><label>\n" +
        "                    <input form=\"contactSave\" class=\"field\" id=\"phoneOperator\" type=\"text\" name=\"phones[][operatorCode]\" value=\"\"/>\n" +
        "                </label></td>\n" +
        "            </tr>\n" +
        "            <tr>\n" +
        "                <td>\n" +
        "                    Номер\n" +
        "                </td>\n" +
        "                <td><label>\n" +
        "                    <input form=\"contactSave\" class=\"field\" id=\"phoneNumber\" type=\"text\" name=\"phones[][number]\" value=\"\"/>\n" +
        "                </label></td>\n" +
        "            </tr>\n" +
        "            <tr>\n" +
        "                <td>\n" +
        "                    Тип\n" +
        "                </td>\n" +
        "                <td><label>\n" +
        "                    <select form=\"contactSave\" class=\"field\" id=\"phoneType\" name=\"phones[][phoneType]\">\n" +
        "                        <option>иной</option>\n" +
        "                        <option>домашний</option>\n" +
        "                        <option>рабочий</option>\n" +
        "                        <option>мобильный</option>\n" +
        "                    </select>\n" +
        "                </label></td>\n" +
        "            </tr>\n" +
        "            <tr>\n" +
        "                <td>\n" +
        "                    Комментарий\n" +
        "                </td>\n" +
        "                <td><label>\n" +
        "                    <input form=\"contactSave\" class=\"field\" id=\"phoneComment\" type=\"text\" name=\"phones[][phoneComment]\" value=\"\"/>\n" +
        "                </label></td>\n" +
        "            </tr>\n" +
        "        </table>\n" +
        "        <label>\n" +
        "            <input form=\"contactSave\" type=\"text\" id=\"phoneStatus\" name=\"phones[][phoneStatus]\" value=\"\" hidden>\n" +
        "        </label>";
    var modalForm = popupPhone.querySelector('.modalContent').querySelector('.modalForm');
    modalForm.appendChild(divPhone);
    phoneId = divPhone.querySelector('#phoneId');
    phoneCountry = divPhone.querySelector("#phoneCountry");
    phoneOperator = divPhone.querySelector("#phoneOperator");
    phoneNumber = divPhone.querySelector("#phoneNumber");
    phoneType = divPhone.querySelector("#phoneType");
    phoneComment = divPhone.querySelector("#phoneComment");
    phoneStatus = divPhone.querySelector('#phoneStatus');
}

//добавление каждого вложения в скрытую форму
function addAttachmentForm() {
    var divAttachment = document.createElement('div');
    divAttachment.classList.add('divAttachment');
    divAttachment.setAttribute('id', 'attachment' + ++attachmentCounter);
    divAttachment.innerHTML =  "<label>\n" +
        "            <input form=\"contactSave\" class=\"field\" id=\"attachmentFile\" name=\"attachments[][file]\" size=\"5000\" type=\"file\"/>\n" +
        "        </label>" +
        "        <label>\n" +
        "            <input form=\"contactSave\" type=\"text\" id=\"attachmentId\" name=\"attachments[][attachmentId]\" value=\"\" hidden>\n" +
        "        </label>\n" +
        "        <table>\n" +
        "            <tr>\n" +
        "                <td>\n" +
        "                    Комментарий\n" +
        "                </td>\n" +
        "                <td><label>\n" +
        "                    <input form=\"contactSave\" class=\"field\" id=\"attachmentComment\" type=\"text\" name=\"attachments[][attachmentComment]\" value=\"\"/>\n" +
        "                </label></td>\n" +
        "            </tr>\n" +
        "        </table>\n" +
        "        <br>\n" +
        "        <label>\n" +
        "            <input form=\"contactSave\" type=\"text\" id=\"attachmentPath\" name=\"attachments[][attachmentPath]\" value=\"\" hidden>\n" +
        "        </label>\n" +
        "        <label>\n" +
        "            <input form=\"contactSave\" class=\"field\" id=\"loadDate\" name=\"attachments[][loadDate]\" type=\"text\" hidden />\n" +
        "        </label>\n" +
        "        <br><br>\n" +
        "        <label>\n" +
        "            <input form=\"contactSave\" type=\"text\" id=\"attachmentStatus\" name=\"attachments[][attachmentStatus]\" value=\"\" hidden>\n" +
        "        </label>";

    var modalForm = popupAttachment.querySelector('.modalContent').querySelector('.modalForm');
    modalForm.appendChild(divAttachment);
    attachmentId = divAttachment.querySelector('#attachmentId');
    attachmentFile = divAttachment.querySelector('#attachmentFile');
    attachmentComment = divAttachment.querySelector('#attachmentComment');
    attachmentStatus = divAttachment.querySelector('#attachmentStatus');
    attachmentPath = divAttachment.querySelector('#attachmentPath');
    loadDate = divAttachment.querySelector('#loadDate');
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
                            idPhoto.value = person.idPhoto;
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
                                addPhoneForm();
                                phoneCountry.value = phone.p_country;
                                phoneOperator.value = phone.p_operator;
                                phoneNumber.value = phone.p_number;
                                phoneType.value = phone.p_type;
                                phoneComment.value = phone.p_comments;
                                phoneId.value = phone.id_phone;

                                tr = document.createElement('tr');
                                phonesTable.appendChild(tr);
                                checkbox = '<label><input type="checkbox" class="checkbox" value="'
                                    + phone.id_phone + '"/></label>';
                                var phoneNum = phone.p_country + '/' + phone.p_operator + '/' + phone.p_number;
                                createTd(checkbox, tr);
                                createTd(phoneNum, tr);
                                createTd(phone.p_type, tr);
                                createTd(phone.p_comments, tr);
                            }
                        }
                        phoneCheckboxes = phonesTable.querySelectorAll('.checkbox');
                        popupPhone.querySelectorAll('.divPhone').forEach(div => {
                                div.style.display = 'none';
                        });

                        if (person.attachments) {
                            for (var attach of person.attachments) {
                                addAttachmentForm();
                                attachmentId.value = attach.id_attachment;
                                attachmentComment.value = attach.a_comments;
                                attachmentPath.value = attach.a_path;
                                loadDate.value = attach.a_date;

                                tr = document.createElement('tr');
                                attachTable.appendChild(tr);
                                checkbox = '<label><input type="checkbox" class="checkbox" value="'
                                    + attach.id_attachment + '"/></label>';
                                var pathLink = '<a href="' + attach.a_path +
                                    '" class="buttonLink" id="attackLink" download>' + attach.a_path + '</a>';
                                createTd(checkbox, tr);
                                createTd(pathLink, tr);
                                createTd(attach.a_date, tr);
                                createTd(attach.a_comments, tr);
                            }
                        }
                        attachmentCheckboxes = attachTable.querySelectorAll('.checkbox');
                        popupAttachment.querySelectorAll('.divAttachment').forEach(div => {
                            div.style.display = 'none';
                        });
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

//по кнопке отмены и крестику
closeBtn.forEach(btn => {
    btn.addEventListener('click', () => {
        setNone();
        if (btn.closest('.modal') === popupPhone) {
            closeAddEditPhone();
        }
        if (btn.closest('.modal') === popupAttachment) {
            closeAddEditAttachment();
        }
    })
})

//по внешнему окну
window.onclick = function (event) {
    if (event.target === popupPhone
        || event.target === popupAttachment
        || event.target === popupPhoto
        || event.target === modalSend
        || event.target === errDeleteEdit
        || event.target === errEdit) {
        setNone();
        if (event.target === popupPhone) {
            closeAddEditPhone();
        }
        if(event.target === popupAttachment) {
            closeAddEditAttachment();
        }
    }
}

//удаление дива телефона при нажатии отмены на окне "добавить"
function closeAddEditPhone() {
    popupPhone.querySelectorAll('.divPhone').forEach(div => {
        if (phoneCounter === +div.id.substring(5)
            && div.querySelector('#phoneStatus').value !== 'updated'
            && div.querySelector('#phoneStatus').value !== 'deleted') {
            div.parentNode.removeChild(div);
        } else {
            div.style.display = 'none';
        }
    })
}

//удаление дива вложения при нажатии отмены на окне "добавить"
function closeAddEditAttachment() {
    popupAttachment.querySelectorAll('.divAttachment').forEach(div => {
        if (attachmentCounter === +div.id.substring(10)
            && div.querySelector('#attachmentStatus').value !== 'updated'
            && div.querySelector('#attachmentStatus').value !== 'deleted') {
            div.parentNode.removeChild(div);
        } else {
            div.style.display = 'none';
        }
    })
}

//скрытие окошек
function setNone() {
    popupPhone.style.display = "none";
    popupAttachment.style.display = "none";
    popupPhoto.style.display = "none";
    modalSend.style.display = "none";
    errDeleteEdit.style.display = 'none';
    errEdit.style.display = 'none';
    addPhoneBtn.removeEventListener('click', editPhones);
    addPhoneBtn.addEventListener('click', addPhone);
    addAttachBtn.removeEventListener('click', editAttachments);
    addAttachBtn.addEventListener('click', addAttachment);
    removeValidation();
}

////////////////////////////////////////////////
//редактирование(создание) контакта
var errors;
form.addEventListener('submit', function (event) {
    event.preventDefault();
    validate();
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
        request.open("POST", "../contacts/", true);
        request.send(new FormData(form));
    }
})

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
            validatePhoto();
            errors = popupPhoto.querySelectorAll('.error');
            if (!errors || errors.length === 0) {
                photoSrc = e.target.result;
                $('#searchImage').attr('src', photoSrc);
                photoStatus = document.querySelector("#statusPhoto");
                photoStatus.value = 'updated';
            }
        });
        reader.readAsDataURL(this.files[0]);
    }
}
$("#photo").change(readURL);

//сброс выбора к первоначальному фото
var cancel = document.querySelector("#photoButton");
cancel.addEventListener('click', function () {
    contactPhoto.src = checkedSrc;
    photoStatus = document.querySelector("#statusPhoto");
    photoStatus.value = 'deleted';
    document.querySelector('#photo').value = '';
    popupPhoto.style.display = "none";
})

//////////////////////////////
//обработка вложений
var popupAttachment = document.querySelector('#messageAttachment');
var createAttachment = document.querySelector('#createAttachment');
var editAttachment = document.querySelector('#editAttachment');
var deleteAttachment = document.querySelector('#deleteAttachment');
var ATable = document.querySelector('#attachmentsTable');

createAttachment.addEventListener('click', () => {
    addAttachmentForm();
    popupAttachment.style.display = "block";
})

deleteAttachment.addEventListener('click', () => {
    deleteRows(ATable, attachmentCheckboxes);
    attachmentCheckboxes = [];
    attachmentCheckboxes = attachTable.querySelectorAll('.checkbox');
})

//добавляем новое вложение
var addAttachBtn = document.querySelector('#attachmentButton');

//получить сегодняшнюю дату загрузки
function getToday() {
    var today = new Date();
    var dd = String(today.getDate()).padStart(2, '0');
    var mm = String(today.getMonth() + 1).padStart(2, '0');
    var yyyy = today.getFullYear();
    today = yyyy + "-" + mm + "-" + dd;
    return today;
}

function addAttachment(event) {
    event.preventDefault();
    validateAttachment();
    errors = popupAttachment.querySelectorAll('.error');
    if (!errors || errors.length === 0) {
        tr = document.createElement('tr');
        attachTable.appendChild(tr);
        var idChbx = attachmentComment.closest('div').id;
        checkbox = '<label><input type="checkbox" class="checkbox" id="' + idChbx + '" value=""/></label>';
        var today = getToday();
        createTd(checkbox, tr);
        createTd(attachmentFile.value, tr);
        createTd(today, tr);
        createTd(attachmentComment.value, tr);
        attachmentCheckboxes = [];
        attachmentCheckboxes = attachTable.querySelectorAll('.checkbox');
        attachmentComment.closest('div').style.display = 'none';
        popupAttachment.style.display = 'none';
    }
}

addAttachBtn.addEventListener('click', addAttachment);

//редактирование по чекбоксу
var chboxAttachment;
editAttachment.addEventListener('click', () => {
    var checkedBoxes = [];
    attachmentCheckboxes.forEach(box => {
        if (box.checked) {
            checkedBoxes.push(box);
        }
    });
    switch (checkedBoxes.length) {
        case 1:
            chboxAttachment = checkedBoxes[0];
            var editDiv;
            var divs = popupAttachment.querySelectorAll('.divAttachment');
            divs.forEach(div => {
                if (chboxAttachment.id === div.querySelector('#attachmentId').value
                    || chboxAttachment.id === div.id) {
                    editDiv = div;
                }
            });
            if (editDiv) {
                attachmentId = editDiv.querySelector('#attachmentId');
                attachmentPath = editDiv.querySelector('#attachmentPath');
                attachmentComment = editDiv.querySelector('#attachmentComment');
                attachmentStatus = editDiv.querySelector('#attachmentStatus');
                var tr = chboxAttachment.closest('tr');
                var tds = tr.querySelectorAll('td');
                attachmentComment.value = tds[3].innerHTML;
                attachmentStatus.value = 'updated';
                popupAttachment.style.display = 'block';
                editDiv.style.display = 'block';
                addAttachBtn.removeEventListener('click', addAttachment);
                addAttachBtn.addEventListener('click', editAttachments);
            }
            break;
        case 0:
            showWarning();
            break;
        default:
            errEdit.querySelector('.modalContent').style.backgroundColor = 'chocolate';
            errEdit.style.display = 'block';
            break;
    }
})

function editAttachments() {
    var tr = chboxAttachment.closest('tr');
    var tds = tr.querySelectorAll('td');
    tds[1].innerHTML = attachmentFile.value;
    tds[2].innerHTML = getToday();
    tds[3].innerHTML = attachmentComment.value;
    attachmentComment.closest('div').style.display = 'none';
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

//удаляем телефон
deletePhone.addEventListener('click', () => {
    deleteRows(PTable, phoneCheckboxes);
    phoneCheckboxes = [];
    phoneCheckboxes = phonesTable.querySelectorAll('.checkbox');
})

//добавляем новый телефон
createPhone.addEventListener('click', () => {
    addPhoneForm();
    popupPhone.style.display = "block";
})

var addPhoneBtn = document.querySelector('#phoneButton');
function addPhone(event) {
    event.preventDefault();
    validatePhone();
    errors = popupPhone.querySelectorAll('.error');
    if (!errors || errors.length === 0) {
        tr = document.createElement('tr');
        phonesTable.appendChild(tr);
        var idChbx = phoneCountry.closest('div').id;
        checkbox = '<label><input type="checkbox" class="checkbox" id="' + idChbx + '" value=""/></label>';
        var number = phoneCountry.value + '/' + phoneOperator.value + '/' + phoneNumber.value;
        createTd(checkbox, tr);
        createTd(number, tr);
        createTd(phoneType.value, tr);
        createTd(phoneComment.value, tr);
        phoneCheckboxes = [];
        phoneCheckboxes = phonesTable.querySelectorAll('.checkbox');
        phoneCountry.closest('div').style.display = 'none';
        popupPhone.style.display = 'none';
    }
}
addPhoneBtn.addEventListener('click', addPhone);

//редактирование по чекбоксу
var chboxPhone;
editPhone.addEventListener('click', () => {
    var checkedBoxes = [];
    phoneCheckboxes.forEach(box => {
        if (box.checked) {
            checkedBoxes.push(box);
        }
    });
    switch (checkedBoxes.length) {
        case 1:
            chboxPhone = checkedBoxes[0];
            var editDiv;
            var divs = popupPhone.querySelectorAll('.divPhone');
            divs.forEach(div => {
                if (chboxPhone.id === div.querySelector('#phoneId').value || chboxPhone.id === div.id) {
                    editDiv = div;
                }
            });
            if (editDiv) {
                phoneId = editDiv.querySelector('#phoneId');
                phoneCountry = editDiv.querySelector("#phoneCountry");
                phoneOperator = editDiv.querySelector("#phoneOperator");
                phoneNumber = editDiv.querySelector("#phoneNumber");
                phoneType = editDiv.querySelector("#phoneType");
                phoneComment = editDiv.querySelector("#phoneComment");
                phoneStatus = editDiv.querySelector('#phoneStatus');
                var tr = chboxPhone.closest('tr');
                var tds = tr.querySelectorAll('td');
                var splitNumber = tds[1].innerHTML.split("/");
                phoneCountry.value = splitNumber[0];
                phoneOperator.value = splitNumber[1];
                phoneNumber.value = splitNumber[2];
                phoneType.value = tds[2].innerHTML;
                phoneComment.value = tds[3].innerHTML;
                phoneStatus.value = 'updated';
                popupPhone.style.display = 'block';
                editDiv.style.display = 'block';
                addPhoneBtn.removeEventListener('click', addPhone);
                addPhoneBtn.addEventListener('click', editPhones);
            }
            break;
        case 0:
            showWarning();
            break;
        default:
            errEdit.querySelector('.modalContent').style.backgroundColor = 'chocolate';
            errEdit.style.display = 'block';
            break;
    }
})

//изменение таблицы телефонов при изменении данных
function editPhones() {
    var tr = chboxPhone.closest('tr');
    var tds = tr.querySelectorAll('td');
    tds[1].innerHTML = phoneCountry.value + '/' + phoneOperator.value + '/' + phoneNumber.value;
    tds[2].innerHTML = phoneType.value;
    tds[3].innerHTML = phoneComment.value;
    phoneCountry.closest('div').style.display = 'none';
    popupPhone.style.display = 'none';
    chboxPhone.checked = false;
    addPhoneBtn.removeEventListener('click', editPhones);
    addPhoneBtn.addEventListener('click', addPhone);
}

//окошко предупреждения, если не выбраны чекбоксы
function showWarning() {
    errDeleteEdit.querySelector('.modalContent').style.backgroundColor = 'chocolate';
    errDeleteEdit.style.display = 'block';
}

//проверка нажаты ли чекбоксы для редактирования, удаления
function checkChoice(checkboxes) {
    var checkedBoxes = [];
    checkboxes.forEach(box => {
        if (box.checked) {
            checkedBoxes.push(box);
        }
    });
    if (checkedBoxes.length === 0) {
        showWarning(checkedBoxes);
    }
}

//удаление строк таблиц по чекбоксам
function deleteRows(table, checkboxes) {
    var i = checkboxes.length;
    checkChoice(checkboxes);
    var ids = [];
    while (i-- && i >= 0) {
        var chbox = checkboxes[i];
        if (chbox.checked === true) {
            if (chbox.value.trim()) {
                ids.push(chbox.value);
            }
            if (chbox.id.trim()) {
                ids.push(chbox.id);
            }
            var tr = chbox.closest('tr');
            table.deleteRow(tr.rowIndex);
        }
    }

    function deletePhoneForm() {
        var divs = popupPhone.querySelectorAll('.divPhone');
        divs.forEach(div => {
            for (var i = 0; i < ids.length; i++) {
                if (ids[i] === div.querySelector('#phoneId').value) {
                    div.querySelector('#phoneStatus').value = 'deleted';
                }
                if (ids[i] === div.id) {
                    div.parentNode.removeChild(div);
                }
            }
        })
    }

    deletePhoneForm();

    function deleteAttachForm() {
        var divs = popupAttachment.querySelectorAll('.divAttachment');
        divs.forEach(div => {
            for (var i = 0; i < ids.length; i++) {
                if (ids[i] === div.querySelector('#attachmentId').value) {
                    div.querySelector('#attachmentStatus').value = 'deleted';
                }
                if (ids[i] === div.id) {
                    div.parentNode.removeChild(div);
                }
            }
        })
    }

    deleteAttachForm();
}

//валидация полей основной части
function validate() {
    removeValidation();
    validateName(nameField);
    validateName(surnameField);
    validatePatronymic(patronymicField);
    validateBirthday(birthdayField);
    validateEmail(emailField);
    validateSite(siteField);
    validateData(workField);
    validateData(statusField);
    validateData(citizenshipField);
    validateData(countryField);
    validateData(cityField);
    validateData(addressField);
    validateZip(zipField);
}

//валидация телефона
function validatePhone() {
    errors = popupPhone.querySelectorAll('.error');
    for (var i = 0; i < errors.length; i++) {
        errors[i].remove();
    }
    validateCode(phoneCountry);
    validateCode(phoneOperator);
    validateNumber(phoneNumber);
    validateLength(phoneComment);
}

//валидация вложения
function validateAttachment() {
    errors = popupAttachment.querySelectorAll('.error');
    for (var i = 0; i < errors.length; i++) {
        errors[i].remove();
    }
    checkFieldPresence(attachmentFile);
    validateSize(attachmentFile);
    validateLength(attachmentComment);
}

//валидация фото
function validatePhoto() {
    errors = popupPhoto.querySelectorAll('.error');
    for (var i = 0; i < errors.length; i++) {
        errors[i].remove();
    }
    validateSize(photo);
}
