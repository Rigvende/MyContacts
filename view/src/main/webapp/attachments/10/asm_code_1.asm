name "asm_code"
; ��� ������ ����������� (�� ����� ������)
org 100h    ; �������� ������� ��������� � ����� ������� ������
            ; 64�� �� ��� (��� + ������ + ����)
jmp start ; ����������� ������� �� ����� start (������ goto label;)
; ��� ��� ������ - ���������� ��������� 5, ������ �������� 1 ����
; ������ ������, ��� unsigned char arr[5]= {1, 0, 5, 6,1};
arr db 10, -1, 5, 6,7
min db 0 
;����� ��������� ����������� ������� � �������
start:
; bx ��� index:
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
; ���������� ������������ �������� � ������  
mov min, al
ret