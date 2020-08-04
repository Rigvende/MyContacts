#include<iostream>
#include <windows.h>
using namespace std;


/*--------------------------------------------------------
Точка входа в программу
--------------------------------------------------------*/
void main(void)
{
	setlocale(LC_ALL, "Russian");
	cout << "привет!" << endl;
	char a[20];
	SetConsoleCP(1251);
	cin >> a;
	SetConsoleCP(866);
	cout << a << endl;
	system("pause");
}


