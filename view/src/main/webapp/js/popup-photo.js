//всплывающее окошко при загрузке фото:

'use strict';

var popupPhoto = document.querySelector('#messagePhoto');
var photo = document.querySelector('.popupPhoto');
var closePhoto = document.querySelectorAll('.close');

photo.addEventListener('click', function () {
    popupPhoto.style.display = "block";
})

closePhoto.forEach(btn => {
    btn.addEventListener('click', () => {
        popupPhoto.style.display = "none";
    })
})

window.onclick = function (event) {
    if (event.target === popupPhoto) {
        popupPhoto.style.display = "none";
    }
}