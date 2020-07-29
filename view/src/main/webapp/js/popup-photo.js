//обработка действий по загрузке пользовательского фото//

'use strict';

//вызов всплывающего окошка для загрузки фото
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

//загрузка фото по умолчанию, если его нет
var contactPhoto = document.querySelector('#searchImage');
var checkedSrc = contactPhoto.getAttribute('src');

//предпросмотр выбранного фото без сохранения на сервер
var photoSrc;
function readURL() {
    if (this.files && this.files[0]) {
        var reader = new FileReader();
        $(reader).on('load', function (e) {
            photoSrc = e.target.result;
            $('#searchImage').attr('src', photoSrc);
        });
        reader.readAsDataURL(this.files[0]);
    }
}

$("#photo").change(readURL);

//сброс выбора к первоначальному фото
var cancel = document.querySelector("#photoButton");
cancel.addEventListener('click', function () {
    contactPhoto.src = checkedSrc;
    document.querySelector('#photo').value = '';
    popupPhoto.style.display = "none";
})


