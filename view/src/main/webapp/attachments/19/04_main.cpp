//Файл 04_main.cpp
#include<iostream>
#include <windows.h>

using namespace std;
typedef double _type;	// теперь можно пользоваться как словом double, иак и словом _type
						//Структура элемента очереди
typedef struct Item
{
	_type			_data; //Поле данных
	struct Item*	_next; //Указатель на такую же структуру

} Item, *pItem;

//Структура собственно очереди
typedef struct
{
	pItem _start;	//Указаткль на начальный элементт
	pItem _end;		//Указаткль на последний элементт
	pItem _cur;		//Курсор или каретка (принодится для работы с очередью)
	int _count;		//Количество элементов в очереди
	int _len;		//Максимально возможное количество элементов
}Queue, *pQueue;



void PrintMenu(void);
void ClsAndPrintMenu(void);
void Task1(pQueue pq);
void Task2(pQueue pq);
void Task3(pQueue pq);
void Task4(pQueue pq);
void Task5(pQueue pq);
void Task6(pQueue pq);

//Инициализация очереди заданного размера
void InitQueue(pQueue pq, int len);
//Добавление элемента в конец очереди
int DelFromStart(pQueue pq);
/*
Функция получения данных из начала очереди
Вход:
pq		-	Указатель на структуру очереди
pData	-	указатель на переменную, в которую будут записаны данные
Выход:
0	- при успешном завершении
1	- в случае ошибки
*/
int GetDataFromStart(pQueue pq, _type* pData);
int AddToEnd(pQueue pq, _type data);
//Добавление элементов с отказом в случае заполнености очереди
int AddToEndCheck1(pQueue pq, _type data);
void PrintQueue(pQueue pq);


#define N 4
/*--------------------------------------------------------
Точка входа в программу
--------------------------------------------------------*/
void main(void)
{
	setlocale(LC_ALL, "Russian");
	//cout << "привет!" << endl;
	//Объявление переменной типа очередь 
	Queue q;
	//Инициализаия пустой очереди
	InitQueue(&q, N);
	cout << "Очередь создана." << endl;
	cout << "Максимальное количество элементов: " << N << endl;
	system("pause");

_menu:
	//Очистка экрана и вывод меню программы
	ClsAndPrintMenu();
	char c;
	cin >> c;
	//Проверка выбранного режима и реализация задачи
	switch (c)
	{
	case '1':
		Task1(&q); goto _menu;
	case '2':
		Task2(&q); goto _menu;
	case '3':
		Task3(&q); goto _menu;
	case '4':
		Task4(&q); goto _menu;
	case '5':
		Task5(&q); goto _menu;
	case '6':
		Task6(&q); goto _menu;
	case '7':	//Выбран режим 7
	default:	//Введен символ, не соответствующий не одному из режимов
		goto _exit;
	}

_exit:
	//Очистка очереди перед выходом
	//Запомнили количество элементов
	int p = (&q)->_count;
	//И удаляем их
	for (int i = 0; i < p; i++)
		DelFromStart(&q);
	SetConsoleCP(866);
	cout << "Раьота программы завершена" << endl;
	system("pause");
}



void PrintMenu(void)
{
	setlocale(LC_ALL, "Russian");
	cout << "1. Добавление элемента" << endl;
	cout << "2. Удаление элемента" << endl;
	cout << "3. Вывод содержимого очереди на экран" << endl;
	cout << "4. Нахождение второго положительного элемента и замена его на -100" << endl;
	cout << "5. Обмен местами первого положительного и последнего отрицательного элемента" << endl;
	cout << "6. Нахождение суммы однозначных отрицательных элементов" << endl;
	cout << "7. Выход" << endl;
}
void ClsAndPrintMenu(void)
{
	system("CLS");
	PrintMenu();

}

void Task1(pQueue pq)
{
	SetConsoleCP(866);
	system("CLS");
	cout << "Реализация пункта 1" << endl;
	cout << "До выполнения задачи:" << endl << endl;
	PrintQueue(pq);
	cout << "Введмте вещественное число:" << endl;
	_type a = 0.0;
	cin >> a;
	AddToEndCheck1(pq, a);
	cout << "После выполнения задачи:" << endl;
	PrintQueue(pq);
}
void Task2(pQueue pq)
{
	SetConsoleCP(866);
	system("CLS");
	cout << "Реализация пункта 2" << endl;
	cout << "До выполнения задачи:" << endl << endl;
	PrintQueue(pq);
	DelFromStart(pq);
	cout << "После выполнения задачи:" << endl;
	PrintQueue(pq);
}
void Task3(pQueue pq)
{
	SetConsoleCP(866);
	system("CLS");
	cout << "Реализация пункта 3" << endl;
	PrintQueue(pq);
}
void Task4(pQueue pq)
{
	SetConsoleCP(866);
	system("CLS");
	cout << "Реализация пункта 4" << endl;
	cout << "До выполнения задачи:" << endl << endl;
	PrintQueue(pq);
	if (pq->_count < 2)
	{
		cout << "В такой очереди не может быть второго положительного элемента" << endl;
		system("PAUSE");
		return;
	}
	int count = 0; //Счетчик положительных элементов
	_type val; //Значение элемента
	pq->_cur = pq->_start;
	int n = pq->_count;
	for (int i = 0; i < n; i++)
	{
		val = pq->_cur->_data;
		if (val > 0.0)
		{
			count++;
			if (count == 2)
			{
				pq->_cur->_data = -100.0;
				break;
			}
		}
		pq->_cur = pq->_cur->_next;
	}
	if (count < 2)
	{
		cout << "В очереди неn второго положительного элемента" << endl;
		system("pause");
		return;
	}
	else
	{
		cout << "После выполнения задачи:" << endl;
		PrintQueue(pq);
	}
	pq->_cur = pq->_start;
}
void Task5(pQueue pq)
{
	SetConsoleCP(866);
	system("CLS");
	cout << "Реализация пункта 5" << endl;
	cout << "До выполнения задачи:" << endl << endl;
	PrintQueue(pq);
	if (pq->_count < 2)
	{
		cout << "В такой очереди нечего менять местами" << endl;
		system("PAUSE");
		return;
	}
	//Ищем первый положительный элемент и фиксируем его адрес
	int count_p = 0; //Счетчик положительных элементов
	_type val; //Значение элемента
	pq->_cur = pq->_start;
	pItem i_p = nullptr, i_n = nullptr;
	int n = pq->_count;
	for (int i = 0; i < n; i++)
	{
		//Забираем данные
		val = pq->_cur->_data;
		//Работаем с положительными числами
		if (val > 0.0)
		{
			count_p++;
			if (count_p == 1)
			{
				//Запоминаем адрес первого положительного
				i_p = pq->_cur;
			}
		}
		//А теперь с отрицательными
		if (val < 0)
		{
			i_n = pq->_cur;
		}
		//Идем дальше до конца очереди
		pq->_cur = pq->_cur->_next;
	}
	//А теперь 
	if ((i_p == nullptr) || (i_n == nullptr))
	{
		cout << "Нет данных для обмена" << endl;
		system("pause");
		return;
	}
	else
	{
		//Обмен данными через третью переменную
		_type tmp;
		tmp = i_p->_data;
		i_p->_data = i_n->_data;
		i_n->_data = tmp;
		cout << "После выполнения задачи:" << endl;
		PrintQueue(pq);
	}
	//Курсор на место
	pq->_cur = pq->_start;
}
void Task6(pQueue pq)
{
	SetConsoleCP(866);
	system("CLS");
	cout << "Реализация пункта 6" << endl;
	cout << "Имеется неоднозначность!" << endl;
	cout << "В такой очереди:" << endl;
	cout << "2.0 -6.3 -6.3 1.0 - 6.3" << endl;
	cout << "понятно, что считать" << endl << endl;
	cout << "А в такой очереди:" << endl;
	cout << "2.0 -6.3 -6.3 1.0 - 6.3 12.0 -8.3 -8.3 1.0 - 8.3" << endl;
	cout << "не понятно, т.к. две группы однозначных" << endl;
	cout << "отрицательных элементов" << endl;
	system("pause");
}
//---------------------------------------------------------------------
void InitQueue(pQueue pq, int len)
{
	if (pq == nullptr)
		return;
	pq->_len = len;
	pq->_count = 0;
	pq->_start = pq->_end = pq->_cur = nullptr;
}

int DelFromStart(pQueue pq)
{
	if (pq == nullptr)
		return 0;
	if (pq->_count == 0)
		return 0;
	if (pq->_count == 1)
	{
		delete pq->_start;
		pq->_start = pq->_end = pq->_cur = nullptr;
		pq->_count = 0;
		//InitQueue(pq, pq->_len);
		return 1;
	}
	if (pq->_count == 2)
	{
		delete pq->_start;
		pq->_start = pq->_cur = pq->_end;
		pq->_count = 1;
		return 1;
	}
	pq->_cur = pq->_start->_next;
	delete pq->_start;
	pq->_start = pq->_cur;
	(pq->_count)--;
	return 1;
}
int GetDataFromStart(pQueue pq, _type* pData)
{
	//Если передали нулевой адрес
	if (pq == nullptr)
		return 1;
	//Если в очереди нет элементов или создали ранее очередь
	//с числом элементов <=0
	if ((pq->_count == 0) || (pq->_len <= 0))
		return 1;
	*pData = pq->_start->_data;
	return 0;
}
int AddToEnd(pQueue pq, _type data)
{
	if (pq == nullptr)
		return 0;
	if (pq->_count == 0)
	{
		pItem pi = new Item;
		pi->_data = data;
		pi->_next = nullptr;
		pq->_start = pq->_end = pq->_cur = pi;
		pq->_count = 1;
		return 1;
	}
	if (pq->_count == 1)
	{
		pItem pi = new Item;
		pi->_data = data;
		pi->_next = nullptr;
		pq->_end = pi;
		pq->_start->_next = pi;
		(pq->_count)++;
		return 1;
	}
	pItem pi = new Item;
	pi->_data = data;
	pi->_next = nullptr;
	pq->_cur = pq->_end;
	pq->_end->_next = pi;
	pq->_end = pi;
	pq->_cur = pq->_start;
	(pq->_count)++;
	return 1;




	//return 0;
}

int AddToEndCheck1(pQueue pq, _type data)
{
	//Если количество элементов в очереди меньше ее длины
	if (pq->_count < pq->_len)
		//Работаем обычным образом
		return (AddToEnd(pq, data));
	else
		//Количество доьавленных элементов 0
		return 0;
}
void PrintQueue(pQueue pq)
{
	SetConsoleCP(866);
	//system("CLS");
	cout << "Состояние очереди:" << endl;
	if (pq->_count == 0)
	{
		cout << "Нет элементов в очереди" << endl;
	}
	else
	{
		cout << "Количество элементов в очереди: " << pq->_count << endl;
		int n = (pq->_count);
		pq->_cur = pq->_start;
		for (int i = 0; i < n; i++)
		{
			cout << pq->_cur->_data << endl;
			pq->_cur = pq->_cur->_next;
		}
		pq->_cur = pq->_start;
	}
	system("pause");
}




