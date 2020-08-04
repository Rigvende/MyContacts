//���� 04_main.cpp
#include<iostream>
#include <windows.h>

using namespace std;
typedef double _type;	// ������ ����� ������������ ��� ������ double, ��� � ������ _type
						//��������� �������� �������
typedef struct Item
{
	_type			_data; //���� ������
	struct Item*	_next; //��������� �� ����� �� ���������

} Item, *pItem;

//��������� ���������� �������
typedef struct
{
	pItem _start;	//��������� �� ��������� ��������
	pItem _end;		//��������� �� ��������� ��������
	pItem _cur;		//������ ��� ������� (���������� ��� ������ � ��������)
	int _count;		//���������� ��������� � �������
	int _len;		//����������� ��������� ���������� ���������
}Queue, *pQueue;



void PrintMenu(void);
void ClsAndPrintMenu(void);
void Task1(pQueue pq);
void Task2(pQueue pq);
void Task3(pQueue pq);
void Task4(pQueue pq);
void Task5(pQueue pq);
void Task6(pQueue pq);

//������������� ������� ��������� �������
void InitQueue(pQueue pq, int len);
//���������� �������� � ����� �������
int DelFromStart(pQueue pq);
/*
������� ��������� ������ �� ������ �������
����:
pq		-	��������� �� ��������� �������
pData	-	��������� �� ����������, � ������� ����� �������� ������
�����:
0	- ��� �������� ����������
1	- � ������ ������
*/
int GetDataFromStart(pQueue pq, _type* pData);
int AddToEnd(pQueue pq, _type data);
//���������� ��������� � ������� � ������ ������������ �������
int AddToEndCheck1(pQueue pq, _type data);
void PrintQueue(pQueue pq);


#define N 4
/*--------------------------------------------------------
����� ����� � ���������
--------------------------------------------------------*/
void main(void)
{
	setlocale(LC_ALL, "Russian");
	//cout << "������!" << endl;
	//���������� ���������� ���� ������� 
	Queue q;
	//������������ ������ �������
	InitQueue(&q, N);
	cout << "������� �������." << endl;
	cout << "������������ ���������� ���������: " << N << endl;
	system("pause");

_menu:
	//������� ������ � ����� ���� ���������
	ClsAndPrintMenu();
	char c;
	cin >> c;
	//�������� ���������� ������ � ���������� ������
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
	case '7':	//������ ����� 7
	default:	//������ ������, �� ��������������� �� ������ �� �������
		goto _exit;
	}

_exit:
	//������� ������� ����� �������
	//��������� ���������� ���������
	int p = (&q)->_count;
	//� ������� ��
	for (int i = 0; i < p; i++)
		DelFromStart(&q);
	SetConsoleCP(866);
	cout << "������ ��������� ���������" << endl;
	system("pause");
}



void PrintMenu(void)
{
	setlocale(LC_ALL, "Russian");
	cout << "1. ���������� ��������" << endl;
	cout << "2. �������� ��������" << endl;
	cout << "3. ����� ����������� ������� �� �����" << endl;
	cout << "4. ���������� ������� �������������� �������� � ������ ��� �� -100" << endl;
	cout << "5. ����� ������� ������� �������������� � ���������� �������������� ��������" << endl;
	cout << "6. ���������� ����� ����������� ������������� ���������" << endl;
	cout << "7. �����" << endl;
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
	cout << "���������� ������ 1" << endl;
	cout << "�� ���������� ������:" << endl << endl;
	PrintQueue(pq);
	cout << "������� ������������ �����:" << endl;
	_type a = 0.0;
	cin >> a;
	AddToEndCheck1(pq, a);
	cout << "����� ���������� ������:" << endl;
	PrintQueue(pq);
}
void Task2(pQueue pq)
{
	SetConsoleCP(866);
	system("CLS");
	cout << "���������� ������ 2" << endl;
	cout << "�� ���������� ������:" << endl << endl;
	PrintQueue(pq);
	DelFromStart(pq);
	cout << "����� ���������� ������:" << endl;
	PrintQueue(pq);
}
void Task3(pQueue pq)
{
	SetConsoleCP(866);
	system("CLS");
	cout << "���������� ������ 3" << endl;
	PrintQueue(pq);
}
void Task4(pQueue pq)
{
	SetConsoleCP(866);
	system("CLS");
	cout << "���������� ������ 4" << endl;
	cout << "�� ���������� ������:" << endl << endl;
	PrintQueue(pq);
	if (pq->_count < 2)
	{
		cout << "� ����� ������� �� ����� ���� ������� �������������� ��������" << endl;
		system("PAUSE");
		return;
	}
	int count = 0; //������� ������������� ���������
	_type val; //�������� ��������
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
		cout << "� ������� ��n ������� �������������� ��������" << endl;
		system("pause");
		return;
	}
	else
	{
		cout << "����� ���������� ������:" << endl;
		PrintQueue(pq);
	}
	pq->_cur = pq->_start;
}
void Task5(pQueue pq)
{
	SetConsoleCP(866);
	system("CLS");
	cout << "���������� ������ 5" << endl;
	cout << "�� ���������� ������:" << endl << endl;
	PrintQueue(pq);
	if (pq->_count < 2)
	{
		cout << "� ����� ������� ������ ������ �������" << endl;
		system("PAUSE");
		return;
	}
	//���� ������ ������������� ������� � ��������� ��� �����
	int count_p = 0; //������� ������������� ���������
	_type val; //�������� ��������
	pq->_cur = pq->_start;
	pItem i_p = nullptr, i_n = nullptr;
	int n = pq->_count;
	for (int i = 0; i < n; i++)
	{
		//�������� ������
		val = pq->_cur->_data;
		//�������� � �������������� �������
		if (val > 0.0)
		{
			count_p++;
			if (count_p == 1)
			{
				//���������� ����� ������� ��������������
				i_p = pq->_cur;
			}
		}
		//� ������ � ��������������
		if (val < 0)
		{
			i_n = pq->_cur;
		}
		//���� ������ �� ����� �������
		pq->_cur = pq->_cur->_next;
	}
	//� ������ 
	if ((i_p == nullptr) || (i_n == nullptr))
	{
		cout << "��� ������ ��� ������" << endl;
		system("pause");
		return;
	}
	else
	{
		//����� ������� ����� ������ ����������
		_type tmp;
		tmp = i_p->_data;
		i_p->_data = i_n->_data;
		i_n->_data = tmp;
		cout << "����� ���������� ������:" << endl;
		PrintQueue(pq);
	}
	//������ �� �����
	pq->_cur = pq->_start;
}
void Task6(pQueue pq)
{
	SetConsoleCP(866);
	system("CLS");
	cout << "���������� ������ 6" << endl;
	cout << "������� ���������������!" << endl;
	cout << "� ����� �������:" << endl;
	cout << "2.0 -6.3 -6.3 1.0 - 6.3" << endl;
	cout << "�������, ��� �������" << endl << endl;
	cout << "� � ����� �������:" << endl;
	cout << "2.0 -6.3 -6.3 1.0 - 6.3 12.0 -8.3 -8.3 1.0 - 8.3" << endl;
	cout << "�� �������, �.�. ��� ������ �����������" << endl;
	cout << "������������� ���������" << endl;
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
	//���� �������� ������� �����
	if (pq == nullptr)
		return 1;
	//���� � ������� ��� ��������� ��� ������� ����� �������
	//� ������ ��������� <=0
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
	//���� ���������� ��������� � ������� ������ �� �����
	if (pq->_count < pq->_len)
		//�������� ������� �������
		return (AddToEnd(pq, data));
	else
		//���������� ����������� ��������� 0
		return 0;
}
void PrintQueue(pQueue pq)
{
	SetConsoleCP(866);
	//system("CLS");
	cout << "��������� �������:" << endl;
	if (pq->_count == 0)
	{
		cout << "��� ��������� � �������" << endl;
	}
	else
	{
		cout << "���������� ��������� � �������: " << pq->_count << endl;
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




