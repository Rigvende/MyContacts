#include<iostream>
#include <windows.h>
using namespace std;


/*--------------------------------------------------------
����� ����� � ���������
--------------------------------------------------------*/
void main(void)
{
	setlocale(LC_ALL, "Russian");
	cout << "������!" << endl;
	char a[20];
	SetConsoleCP(1251);
	cin >> a;
	SetConsoleCP(866);
	cout << a << endl;
	system("pause");
}


