//скрипт для автозаполнения полей при выборе шаблона

'use strict';

var templates = {
    "template 1" : "Поздравляю с днём рожденья! Желаю счастья, здоровья и успехов в делах!",
    "template 2" : "Привет! Давно не виделись! Может быть, ты не против встретиться в ближайшее время?"
}
var choice = document.querySelector('#templateField');
choice.addEventListener('change', function () {
    var template = choice.options[choice.selectedIndex];
    var textarea = document.querySelector("#messageField");
    var header = document.querySelector("#headerField");
    if (templates.hasOwnProperty(template.value)) {
        header.value = template.text;
        textarea.value = templates[template.value];
    } else {
        header.value = ""
        textarea.value = "";
    }
})