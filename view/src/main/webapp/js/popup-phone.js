//всплывающее окошко при загрузке телефона:

'use strict';

var popupPhone = document.querySelector('#messagePhone');
var phone = document.querySelectorAll('.popupPhone');
var closePhone = document.querySelectorAll('.close');

phone.forEach(btn => {
    btn.addEventListener('click', () => {
        popupPhone.style.display = "block";
    })
})

closePhone.forEach(btn => {
    btn.addEventListener('click', () => {
        popupPhone.style.display = "none";
    })
})

window.onclick = function (event) {
    if (event.target === popupPhone) {
        popupPhone.style.display = "none";
    }
}