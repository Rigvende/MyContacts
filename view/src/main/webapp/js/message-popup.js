"use strict";

var press = document.querySelector(".messageButton");
var popup = document.querySelector(".message");
var close = document.querySelector(".close");

press.addEventListener("click", function (evt) {
    evt.preventDefault();
    popup.style.display = 'flex';
    popup.style.flexDirection = 'column';
    popup.classList.add("message");
});

close.addEventListener("click", function (evt) {
    evt.preventDefault();
    popup.style.display = "none";
    popup.classList.remove("message");
});