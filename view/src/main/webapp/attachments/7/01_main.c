#include <stdio.h>
#include <stdlib.h>
#include <windows.h>

int main(void)
{
	setlocale(LOCALE_ALL, "RUSSIAN");
	char buf[20];
	printf("Hello World!\n");
#pragma warning(disable : 4996)
	SetConsoleCP(1251);
	scanf("%s", buf);
#pragma warning(enable : 4996)
	SetConsoleCP(866);
	printf("%s\n", buf);
	system("pause");
	return 0;
}
