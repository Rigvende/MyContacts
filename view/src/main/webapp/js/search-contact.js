//обработчик сброса фильтра
var removeFilter = document.querySelector('#filterContact');
removeFilter.addEventListener('click', function () {
    removeFilter.style.display = 'none';
    searchContact.style.display = 'block';
    loadContacts();
})

//обработка полей фильтра
function processFields() {
    var nameContact = document.querySelector('#nameField').value;
    var surname = document.querySelector('#surnameField').value;
    var patronymic = document.querySelector('#patronymicField').value;
    var birthday = document.querySelector('#birthdayField').value;
    var condition = document.querySelector('input[name=day]:checked').value;
    var gender = document.querySelector('input[name=gender]:checked').value;
    var status = document.querySelector('#statusField').value;
    var citizenship = document.querySelector('#citizenshipField').value;
    var country = document.querySelector('#countryField').value;
    var city = document.querySelector('#cityField').value;
    var street = document.querySelector('#addressField').value;

    var searchData = '';
    if (nameContact != null && nameContact.trim()) {
        searchData += '&contactName=' + encodeURIComponent(nameContact);
    }
    if (surname != null && surname.trim()) {
        searchData += '&surname=' + encodeURIComponent(surname);
    }
    if (patronymic != null && patronymic.trim()) {
        searchData += '&patronymic=' + encodeURIComponent(patronymic);
    }
    if (birthday != null && birthday.trim()) {
        searchData += '&birthday=' + birthday;
    }
    if (condition != null && condition.trim()) {
        searchData += '&condition=' + encodeURIComponent(condition);
    }
    if (gender != null && gender.trim() && gender !== 'unknown') {
        searchData += '&gender=' + encodeURIComponent(gender);
    }
    if (status != null && status.trim()) {
        searchData += '&status=' + encodeURIComponent(status);
    }
    if (citizenship != null && citizenship.trim()) {
        searchData += '&citizenship=' + encodeURIComponent(citizenship);
    }
    if (country != null && country.trim()) {
        searchData += '&country=' + encodeURIComponent(country);
    }
    if (city != null && city.trim()) {
        searchData += '&city=' + encodeURIComponent(city);
    }
    if (street != null && street.trim()) {
        searchData += '&street=' + encodeURIComponent(street);
    }
    return searchData.substr(1, searchData.length - 1);
}

//запрос на получение результатов поиска и отрисовки таблицы
function searchContacts(event) {
    event.preventDefault();
    var data = processFields();
    var searchRequest = new XMLHttpRequest();
    searchRequest.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            searchModal.style.display = 'none';
            searchContact.style.display = 'none';
            removeFilter.style.display = 'block';
            if (this.responseText != null && this.responseText.trim()) {
                var contacts = JSON.parse(this.responseText);
                fillTable(contacts);
            } else {
                var table = document.querySelector("#contactsList");
                var pagination = document.querySelector('#pagination');
                table.innerHTML = '';
                pagination.innerHTML = '';
                var tr = document.createElement('tr');
                table.appendChild(tr);
                var td = document.createElement('td');
                td.innerHTML = 'К СОЖАЛЕНИЮ, НИЧЕГО НЕ НАЙДЕНО :(';
                td.style.backgroundColor = 'Chocolate';
                td.style.fontSize = '18px'
                tr.appendChild(td);
            }
            clearFilter();
        }
    };
    var url = "http://localhost:8080/view_war/search?" + data;
    searchRequest.open("GET", url, true);
    searchRequest.send();
}

//очистка фильтра
function clearFilter() {
    var clearFields = document.querySelectorAll('.field');
    clearFields.forEach(field => {
        field.value = '';
    })
    var clearCondition = document.querySelectorAll('input[name="day"]');
    clearCondition.forEach(condition => {
        condition.checked = condition.value === 'strict';
    })
    var clearGender = document.querySelectorAll('input[name="gender"]');
    clearGender.forEach(gender => {
        gender.checked = gender.value === 'unknown';
    })
}

//нажатие на кнопку поиска по полям
var searchBtn = document.querySelector('#searchBtn');
searchBtn.addEventListener('click', searchContacts);
