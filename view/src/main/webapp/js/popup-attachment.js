//всплывающее окошко при загрузке приложения:

'use strict';

var popupAttachment = document.querySelector('#messageAttachment');
var attachment = document.querySelectorAll('.popupAttachment');
var closeAttachment = document.querySelectorAll('.close');

attachment.forEach(btn => {
    btn.addEventListener('click', () => {
        popupAttachment.style.display = "block";
    })
})

closeAttachment.forEach(btn => {
    btn.addEventListener('click', () => {
        popupAttachment.style.display = "none";
    })
})

window.onclick = function (event) {
    if (event.target === popupAttachment) {
        popupAttachment.style.display = "none";
    }
}
