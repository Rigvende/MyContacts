//todo пока только для фото, сделать универсальным

'use strict';

var showPopup = document.querySelector('.popupForm');
var hidePopup = document.querySelector('.hidePhoto');

showPopup.addEventListener('click', openForm);
hidePopup.addEventListener('click', closeForm);

function openForm() {
    document.querySelector('#photoForm').style.display = 'block';
}
function closeForm() {
    document.querySelector('#photoForm').style.display = 'none';
}