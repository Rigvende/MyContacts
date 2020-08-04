name "asm_code"
; Это просто комментарий (до конца строки)
org 100h    ; Указание создать программу с малой моделью памяти
            ; 64Кб на все (код + данные + стек)
jmp start ; Безусловный переход на метку start (аналог goto label;)
; это наш массив - количество элементов 5, каждый размером 1 байт
; Можете думать, как unsigned char arr[5]= {1, 0, 5, 6,1};
arr db 10, -1, 5, 6,7
min db 0 
;Будем вычислять минимальный элемент в массиве
start:
; bx как index:
mov bx, 0
mov al, arr[bx]
inc bx
loop:
    cmp bx, 5
    jge out
        cmp al, arr[bx]
    jl not
        mov al, arr[bx]
    not:
        inc bx
        jmp loop

out:
; сохранение минимального значения в памяти  
mov min, al
ret