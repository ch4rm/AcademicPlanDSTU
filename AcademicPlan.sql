﻿DROP TABLE IF EXISTS schedules_ap,
					 matrix_schedules_ap,directions,
					 sch_directions,
					 activity_types,
					 cycles,
					 parts,
					 state_certification_types,
					 profiles,
					 titles,
					 subjects,
					 subject_assignment,
					 state_certification,
					 pract_types,
					 departments,
					 time_budgets,pract;

create table schedules_ap ( -- графики_уп
	key_sch_ap_pk BIGSERIAL PRIMARY KEY , -- код_гр_уп_рк
	year_sap smallserial -- год
);

create table matrix_schedules_ap ( -- матрица_графики_уп
	key_matr_sch_ap_pk BIGSERIAL PRIMARY KEY, -- код_матр_гр_уп_pk
	key_sch_ap_fk bigint references schedules_ap(key_sch_ap_pk), -- код_гр_уп_fk
	class_msa smallint, -- курс
	month_msa smallserial, -- месяц
	week_msa smallint, -- неделя
	date_start smallint, -- датанач
	date_end smallint, -- датаок
	label_msa varchar(40) -- пометка
);

create table directions ( -- направления
	key_d_pk SERIAL4 PRIMARY KEY, -- кодн_рк
	key_d_nums varchar(40), -- коднцифр
	name_d varchar(40) -- наименованиен
);

create table sch_directions ( -- гр_направления
	key_sch_d_pk SERIAL4 PRIMARY KEY, -- код_г_н_рк
	key_sch_d_nums varchar(40), -- код_г_н_цифр
	name_sch_d varchar(40) -- наименование_г_н
);

create table activity_types ( -- виды_занятий
	key_activity_type_pk SMALLSERIAL PRIMARY KEY, -- код_вида_з_рк
	activity_type varchar(40) -- вид_занятия
);

create table cycles ( -- циклы
	key_cycle_pk BIGSERIAL PRIMARY KEY, -- код_цикла_рк
	key_cycle_let varchar(40), -- код_цикла_букв
	name_c varchar(80) -- название
);

create table parts ( -- части
	key_parts_pk BIGSERIAL PRIMARY KEY, -- код_части_рк
	key_cycle_fk bigserial references cycles(key_cycle_pk), -- код_цикла_fk
	key_parts_let varchar(40), -- кодчастибукв
	name_p varchar(80) -- название
);

create table state_certification_types ( -- виды_госаттестации
	key_sc_pk SMALLSERIAL PRIMARY KEY , -- код_га_рк
	form_sct varchar(40), -- форма
	name_sct varchar(80) -- название
);

create table profiles ( -- профили
	key_prof_pk SERIAL4 PRIMARY KEY, -- код_проф_рк
	key_prof_nums varchar(40), -- код_проф_цифр
	name_prof varchar(80) -- наименование_проф
);

create table titles ( -- титулы
	key_ap_pk BIGSERIAL PRIMARY KEY, -- код_уп_рк
	year_reception smallint, -- год_приема
	year_creation smallint, -- год_составления
	qualification varchar(40), -- квалификация
	terms_education smallint, -- ср_обучения
	lvl_education varchar(40), -- уров_образования
	key_sch_d_fk int, -- код_гр_направления_fk
	key_d_fk_ int, -- код_направления_fk
	key_profile_fk serial4 references profiles(key_prof_pk), -- код_профиля_fk
	form_education varchar(40), -- форма_обучения
	key_sch_ap_process_fk int -- код_гр_уч_процесса_fk
);

create table departments ( --кафедры
	key_department_pk SMALLSERIAL PRIMARY KEY, -- код_кафедры_рк
	key_department varchar(40) -- шифр_кафедры
);

create table subjects ( -- дисциплины
	key_subject_pk BIGSERIAL PRIMARY KEY, -- код_дисциплины_рк
	key_subject numeric(5,1), -- код_дисциплины
	key_ap_fk bigint references titles(key_ap_pk), -- кодуп_fk
	key_cycle_fk smallint references cycles(key_cycle_pk), -- кодцикла_fk
	key_parts_fk smallint references parts(key_parts_pk), -- кодчасти_fk
	name_s varchar(80), -- наименование
	key_department_fk smallint,
	exams_s smallint, -- экзамены
	setoff_s smallint, -- зачеты
	lect_s smallint, -- лекции
	lab_s smallint, -- лабработы
	pract_s smallint, -- практзанятия
	ksr_s smallint, -- кср
	bsr_s smallint -- бср
);

create table subject_assignment ( -- дисциплины_распределение
	key_sub_assignment_pk BIGSERIAL PRIMARY KEY, -- код_дисп_расп_рк
	key_subject_fk bigint references subjects(key_subject_pk), -- код_дисциплины
	course_num_sa smallint, -- курс_номер
	semester_num_sa smallint, -- семестр_номер
	key_type_fk smallint, -- код_вида_fk
	hour_lec_sa float, -- часы_лек 
	hour_lab_sa float, -- часы_лаб
	hour_prac_sa float, -- часы_практ
	hour_self_sa float, -- часы_сам
	hour_exam_sa float -- часы_мод_контр
);

create table state_certification ( -- гос_аттестация
	key_sc_pk BIGSERIAL PRIMARY KEY, -- код_га_рк
	key_ap_fk bigint references titles(key_ap_pk), -- код_уп_fk
	name_d_fk smallint references directions(key_d_pk), -- название_дисциплины_fk
	semester_sc smallint -- семестр
);

create table pract_types ( -- виды_практики
	key_pract_pk SMALLSERIAL PRIMARY KEY, -- код_практ_рк
	name_pt varchar(40) -- название
);

create table time_budgets ( -- бюджеты_времени
	key_tb_pk BIGSERIAL PRIMARY KEY , -- код_бв_рк
	key_ap_fk bigint references titles(key_ap_pk), -- код_уп_fk
	course_tb smallint, -- курс
	weeks_theory_tb smallint, -- недели_теория
	weeks_session_tb smallint, -- недели_сессия
	weeks_pract_tb smallint, -- недели_практика
	weeks_diploma_tb smallint, -- недели_диплом
	weeks_state_exam_tb smallint, -- недели_гос_экзамен
	weeks_holiday_tb smallint -- недели_каникулы
);

create table pract ( -- практики
	key_pract_pk BIGSERIAL PRIMARY KEY, -- код_пр_рк
	key_ap_fk bigint references titles(key_ap_pk), -- код_уп_fk
	name_pract_fk smallint, -- название_практики_fk
	key_week_count smallint -- кол_недель
);

-- Таблица виды_практики
INSERT INTO pract_types (name_pt)
		VALUES ('Учебная'),('Производственная'),('Преддипломная');

-- Таблица циклы
INSERT INTO cycles (key_cycle_let, name_c)
		VALUES ('Б1','ГУМАНИТАРНЫЙ, СОЦИАЛЬНЫЙ и ЭКОНОМИЧЕСКИЙ ЦИКЛ'),
			   ('Б2','МАТЕМАТИЧЕСКИЙ и ЕСТЕСТВЕННО-НАУЧНЫЙ ЦИКЛ'),
			   ('Б3','ПРОФЕССИОНАЛЬНЫЙ ЦИКЛ');

-- Таблица части
INSERT INTO parts (key_cycle_fk,key_parts_let, name_p)
		VALUES ('1','Б','Базовая часть'),
			   ('1','В','Вариативная часть, в том числе по выбору обучающегося'),
			   ('2','Б','Базовая часть'),
			   ('2','В','Вариативная часть, в том числе по выбору обучающегося'),
			   ('3','Б','Базовая часть'),
			   ('3','В','Вариативная часть, в том числе по выбору обучающегося');

-- Таблица виды гос аттестации
INSERT INTO state_certification_types (form_sct, name_sct)
		VALUES ('Государственный экзамен','Государственная итоговая аттестация'),
			   ('Выпускная квалификационная работа','Государственная итоговая аттестация');

-- Таблица бюджеты времени
INSERT INTO time_budgets (key_ap_fk, course_tb, weeks_theory_tb, weeks_session_tb, weeks_pract_tb, weeks_diploma_tb, weeks_state_exam_tb, weeks_holiday_tb)
		VALUES (NULL,'1','36','4','0','0','0','12'),
			   (NULL,'2','36','4','2','0','0','10'),
			   (NULL,'3','36','4','3','0','0','9'),
			   (NULL,'4','27','4','3','6','1','11');

-- Таблица графики уп
INSERT INTO schedules_ap (year_sap) VALUES ('2017');

-- Таблица матрицы графики уп
INSERT INTO matrix_schedules_ap (key_sch_ap_fk, class_msa, month_msa, week_msa, date_start, date_end, label_msa)
		VALUES ('1','1','1','1','29','4',NULL),
				 ('1','1','1','2','5','11',NULL),
				 ('1','1','1','3','12','18',NULL),
				 ('1','1','1','4','19','25',NULL),
				 ('1','1','2','5','26','2',NULL),
				 ('1','1','2','6','3','9',NULL),
				 ('1','1','2','7','10','16',NULL),
				 ('1','1','2','8','17','23',NULL),
				 ('1','1','2','9','24','30','СК'),
				 ('1','1','3','10','31','6',NULL),
				 ('1','1','3','11','7','13',NULL),
				 ('1','1','3','12','14','20',NULL),
				 ('1','1','3','13','21','27',NULL),
				 ('1','1','4','14','28','4',NULL),
				 ('1','1','4','15','5','11',NULL),
				 ('1','1','4','16','12','18',NULL),
				 ('1','1','4','17','19','25',NULL),
				 ('1','1','5','18','26','1','СК'),
				 ('1','1','5','19','2','8','С'),
				 ('1','1','5','20','9','15','С'),
				 ('1','1','5','21','16','22','К'),
				 ('1','1','5','22','23','29','К'),
				 ('1','1','6','23','30','5',NULL),
				 ('1','1','6','24','6','12',NULL),
				 ('1','1','6','25','13','19',NULL),
				 ('1','1','6','26','20','26',NULL),
				 ('1','1','7','27','27','5',NULL),
				 ('1','1','7','28','6','12',NULL),
				 ('1','1','7','29','13','19',NULL),
				 ('1','1','7','30','20','26',NULL),
				 ('1','1','8','31','27','2','СК'),
				 ('1','1','8','32','3','9',NULL),
				 ('1','1','8','33','10','16',NULL),
				 ('1','1','8','34','17','23',NULL),
				 ('1','1','9','35','24','30',NULL),
				 ('1','1','9','36','1','7',NULL),
				 ('1','1','9','37','8','14',NULL),
				 ('1','1','9','38','15','21',NULL),
				 ('1','1','9','39','22','28',NULL),
				 ('1','1','10','40','29','4','СК'),
				 ('1','1','10','41','5','11','С'),
				 ('1','1','10','42','12','18','С'),
				 ('1','1','10','43','19','25','К'),
				 ('1','1','11','44','26','2','К'),
				 ('1','1','11','45','3','9','К'),
				 ('1','1','11','46','10','16','К'),
				 ('1','1','11','47','17','23','К'),
				 ('1','1','12','48','24','30','К'),
				 ('1','1','12','49','31','6','К'),
				 ('1','1','12','50','7','13','К'),
				 ('1','1','12','51','14','20','К'),
				 ('1','1','12','52','21','27','К'),
				 
				 ('1','2','1','1','29','4',NULL),
				 ('1','2','1','2','5','11',NULL),
				 ('1','2','1','3','12','18',NULL),
				 ('1','2','1','4','19','25',NULL),
				 ('1','2','2','5','26','2',NULL),
				 ('1','2','2','6','3','9',NULL),
				 ('1','2','2','7','10','16',NULL),
				 ('1','2','2','8','17','23',NULL),
				 ('1','2','2','9','24','30','СК'),
				 ('1','2','3','10','31','6',NULL),
				 ('1','2','3','11','7','13',NULL),
				 ('1','2','3','12','14','20',NULL),
				 ('1','2','3','13','21','27',NULL),
				 ('1','2','4','14','28','4',NULL),
				 ('1','2','4','15','5','11',NULL),
				 ('1','2','4','16','12','18',NULL),
				 ('1','2','4','17','19','25',NULL),
				 ('1','2','5','18','26','1','СК'),
				 ('1','2','5','19','2','8','С'),
				 ('1','2','5','20','9','15','С'),
				 ('1','2','5','21','16','22','К'),
				 ('1','2','5','22','23','29','К'),
				 ('1','2','6','23','30','5',NULL),
				 ('1','2','6','24','6','12',NULL),
				 ('1','2','6','25','13','19',NULL),
				 ('1','2','6','26','20','26',NULL),
				 ('1','2','7','27','27','5',NULL),
				 ('1','2','7','28','6','12',NULL),
				 ('1','2','7','29','13','19',NULL),
				 ('1','2','7','30','20','26',NULL),
				 ('1','2','8','31','27','2','СК'),
				 ('1','2','8','32','3','9',NULL),
				 ('1','2','8','33','10','16',NULL),
				 ('1','2','8','34','17','23',NULL),
				 ('1','2','9','35','24','30',NULL),
				 ('1','2','9','36','1','7',NULL),
				 ('1','2','9','37','8','14',NULL),
				 ('1','2','9','38','15','21',NULL),
				 ('1','2','9','39','22','28',NULL),
				 ('1','2','10','40','29','4','СК'),
				 ('1','2','10','41','5','11','С'),
				 ('1','2','10','42','12','18','С'),
				 ('1','2','10','43','19','25','П'),
				 ('1','2','11','44','26','2','П'),
				 ('1','2','11','45','3','9','К'),
				 ('1','2','11','46','10','16','К'),
				 ('1','2','11','47','17','23','К'),
				 ('1','2','12','48','24','30','К'),
				 ('1','2','12','49','31','6','К'),
				 ('1','2','12','50','7','13','К'),
				 ('1','2','12','51','14','20','К'),
				 ('1','2','12','52','21','27','К'),
				 
				 ('1','3','1','1','29','4',NULL),
				 ('1','3','1','2','5','11',NULL),
				 ('1','3','1','3','12','18',NULL),
				 ('1','3','1','4','19','25',NULL),
				 ('1','3','2','5','26','2',NULL),
				 ('1','3','2','6','3','9',NULL),
				 ('1','3','2','7','10','16',NULL),
				 ('1','3','2','8','17','23',NULL),
				 ('1','3','2','9','24','30','СК'),
				 ('1','3','3','10','31','6',NULL),
				 ('1','3','3','11','7','13',NULL),
				 ('1','3','3','12','14','20',NULL),
				 ('1','3','3','13','21','27',NULL),
				 ('1','3','4','14','28','4',NULL),
				 ('1','3','4','15','5','11',NULL),
				 ('1','3','4','16','12','18',NULL),
				 ('1','3','4','17','19','25',NULL),
				 ('1','3','5','18','26','1','СК'),
				 ('1','3','5','19','2','8','С'),
				 ('1','3','5','20','9','15','С'),
				 ('1','3','5','21','16','22','К'),
				 ('1','3','5','22','23','29','К'),
				 ('1','3','6','23','30','5',NULL),
				 ('1','3','6','24','6','12',NULL),
				 ('1','3','6','25','13','19',NULL),
				 ('1','3','6','26','20','26',NULL),
				 ('1','3','7','27','27','5',NULL),
				 ('1','3','7','28','6','12',NULL),
				 ('1','3','7','29','13','19',NULL),
				 ('1','3','7','30','20','26',NULL),
				 ('1','3','8','31','27','2','СК'),
				 ('1','3','8','32','3','9',NULL),
				 ('1','3','8','33','10','16',NULL),
				 ('1','3','8','34','17','23',NULL),
				 ('1','3','9','35','24','30',NULL),
				 ('1','3','9','36','1','7',NULL),
				 ('1','3','9','37','8','14',NULL),
				 ('1','3','9','38','15','21',NULL),
				 ('1','3','9','39','22','28',NULL),
				 ('1','3','10','40','29','4','СК'),
				 ('1','3','10','41','5','11','С'),
				 ('1','3','10','42','12','18','С'),
				 ('1','3','10','43','19','25','П'),
				 ('1','3','11','44','26','2','П'),
				 ('1','3','11','45','3','9','П'),
				 ('1','3','11','46','10','16','К'),
				 ('1','3','11','47','17','23','К'),
				 ('1','3','12','48','24','30','К'),
				 ('1','3','12','49','31','6','К'),
				 ('1','3','12','50','7','13','К'),
				 ('1','3','12','51','14','20','К'),
				 ('1','3','12','52','21','27','К'),
				 
				 ('1','3','1','1','29','4',NULL),
				 ('1','3','1','2','5','11',NULL),
				 ('1','3','1','3','12','18',NULL),
				 ('1','3','1','4','19','25',NULL),
				 ('1','3','2','5','26','2',NULL),
				 ('1','3','2','6','3','9',NULL),
				 ('1','3','2','7','10','16',NULL),
				 ('1','3','2','8','17','23',NULL),
				 ('1','3','2','9','24','30','СК'),
				 ('1','3','3','10','31','6',NULL),
				 ('1','3','3','11','7','13',NULL),
				 ('1','3','3','12','14','20',NULL),
				 ('1','3','3','13','21','27',NULL),
				 ('1','3','4','14','28','4',NULL),
				 ('1','3','4','15','5','11',NULL),
				 ('1','3','4','16','12','18',NULL),
				 ('1','3','4','17','19','25',NULL),
				 ('1','3','5','18','26','1','СК'),
				 ('1','3','5','19','2','8','С'),
				 ('1','3','5','20','9','15','С'),
				 ('1','3','5','21','16','22','К'),
				 ('1','3','5','22','23','29','К'),
				 ('1','3','6','23','30','5',NULL),
				 ('1','3','6','24','6','12',NULL),
				 ('1','3','6','25','13','19',NULL),
				 ('1','3','6','26','20','26',NULL),
				 ('1','3','7','27','27','5',NULL),
				 ('1','3','7','28','6','12',NULL),
				 ('1','3','7','29','13','19',NULL),
				 ('1','3','7','30','20','26',NULL),
				 ('1','3','8','31','27','2','СК'),
				 ('1','3','8','32','3','9','С'),
				 ('1','3','8','33','10','16','С'),
				 ('1','3','8','34','17','23','П'),
				 ('1','3','9','35','24','30','П'),
				 ('1','3','9','36','1','7','П'),
				 ('1','3','9','37','8','14','Г'),
				 ('1','3','9','38','15','21','Д'),
				 ('1','3','9','39','22','28','Д'),
				 ('1','3','10','40','29','4','Д'),
				 ('1','3','10','41','5','11','Д'),
				 ('1','3','10','42','12','18','Д'),
				 ('1','3','10','43','19','25','Д'),
				 ('1','3','11','44','26','2','К'),
				 ('1','3','11','45','3','9','К'),
				 ('1','3','11','46','10','16','К'),
				 ('1','3','11','47','17','23','К'),
				 ('1','3','12','48','24','30','К'),
				 ('1','3','12','49','31','6','К'),
				 ('1','3','12','50','7','13','К'),
				 ('1','3','12','51','14','20','К'),
				 ('1','3','12','52','21','27','К');

-- Таблица направления
INSERT INTO directions (key_d_nums, name_d)
	VALUES ('0000','Информатика и вычислительная техника');
	
-- Таблица гр_направления
INSERT INTO sch_directions (key_sch_d_nums, name_sch_d)
	VALUES ('0000','Информатика и вычислительная техника');

-- Таблица профили
INSERT INTO profiles (key_prof_nums, name_prof)
		VALUES (NULL,'Автоматизированные системы обработки информации и управления');

-- Таблица виды_занятий
INSERT INTO activity_types(activity_type)
		VALUES ('Лекционное занятие'),('Лабораторная работа'),('Практическое занятие');

-- Таблица титулы
INSERT INTO titles(year_reception, year_creation, qualification, terms_education, lvl_education, key_sch_d_fk, key_d_fk_, form_education, key_sch_ap_process_fk)
		VALUES ('2017','2017','бакалавр','4', 'бакалавриат','1','1','дневная',NULL);

-- Таблица практики
INSERT INTO pract (key_ap_fk, name_pract_fk, key_week_count)
		VALUES ('1','1','2'),('1','2','3'),('1','3','3');

-- Таблица гос_аттестация
INSERT INTO state_certification (key_ap_fk, name_d_fk, semester_sc)
		VALUES ('1','1','8'), ('1','1','8');

-- Таблица кафедры
INSERT INTO departments(key_department)
		VALUES ('СГД'), ('ТППиОЯ'), ('ЭУ'),
			   ('ВМ'), ('РФ'), ('ЭБЖД'), ('СКС'),
			   ('ТОЭ'), ('АПИГ');
		
-- Таблица дисциплины
INSERT INTO subjects (key_subject, key_ap_fk, key_cycle_fk, key_parts_fk, name_s,
					 key_department_fk, exams_s, setoff_s, lect_s, lab_s, pract_s, ksr_s, bsr_s)
		VALUES -- ГСЭ
			   ('1.0','1','1','1','История','1','2','0','0','0','0','0','0'),
			   ('2.0','1','1','1','Философия','2','4','0','0','0','0','0','0'),
			   ('3.0','1','1','1','Иностранный язык','2','3','1','0','0','0','0','0'),
			   ('4.0','1','1','1','Иностранный язык','2','3','1','0','0','0','0','0'),
			   ('5.0','1','1','1','Иностранный язык','2','3','1','0','0','0','0','0'),
			   ('6.0','1','1','1','Экономика','3','0','5','0','0','0','0','0'),
			   
			   ('1.0','1','1','2','Социология','1','0','5','0','0','0','0','0'),
			   ('2.0','1','1','2','Правоведение','1','0','3','0','0','0','0','0'),
			   ('3.0','1','1','2','Культурология','1','0','5','0','0','0','0','0'),
			   ('4.0','1','1','2','Экономика предприятия','3','0','6','0','0','0','0','0'),
			   ('5.0','1','1','2','Русский язык','2','0','2','0','0','0','0','0'),
			   ('6.0','1','1','2','Русский язык','2','0','2','0','0','0','0','0'),
			   
			   ('7.0','1','1','2','Дисциплина по выбору №1','3','7','0','0','0','0','0','0'),
			   ('7.1','1','1','2','Основы организации хозяйственной деятельности (ОХД)','3','0','0','0','0','0','0','0'),
			   ('7.2','1','1','2','Организация производства','3','0','0','0','0','0','0','0'),
			   ('8.0','1','1','2','Дисциплина по выбору №2','1','0','7','0','0','0','0','0'),
			   ('8.1','1','1','2','Психология','1','1','1','0','0','0','0','0'),
			   ('8.2','1','1','2','Педагогика','1','1','1','0','0','0','0','0'),
			   
			   ('9.0','1','1','2','Дисциплина по выбору №3','3','0','8','0','0','0','0','0'),
			   ('9.1','1','1','2','ОХД','3','0','0','0','0','0','0','0'),
			   ('9.2','1','1','2','Организация производства','3','1','1','0','0','0','0','0'),
			   
			   -- МЕН
			   ('1.0','1','2','3','Математика','4','1','0','0','0','0','0','0'),
			   ('2.0','1','2','3','Физика','5','1','0','0','0','0','0','0'),
			   ('3.0','1','2','3','Информатика','7','1','2','0','0','0','0','0'),
			   ('4.0','1','2','3','Экология','6','0','3','0','0','0','0','0'),
			   
			   ('1.0','1','2','4','Компьютерная логика','7','2','0','0','0','0','0','0'),
			   ('2.0','1','2','4','Теория вероятности и математическая статистика','4','0','3','0','0','0','0','0'),
			   ('3.0','1','2','4','Алгоритмы и методы вычислений','7','3','0','0','0','0','0','0'),
			   ('4.0','1','2','4','Методы передачи и обработки информации','7','5','0','0','0','0','0','0'),
			   ('5.0','1','2','4','Курсовой проект. Методы передачи и обработки информации','7','0','6','0','0','0','0','0'),
			   
			   -- МЕН Выбор
			   ('6.0','1','2','4','Дисциплина по выбору №1','7','0','4','0','0','0','0','0'),
			   ('6.1','1','2','4','Технологии программирования','7','1','1','0','0','0','0','0'),
			   ('6.2','1','2','4','Программирование на языке С++','7','2','2','0','0','0','0','0'),
			   ('7.0','1','2','4','Дисциплина по выбору №2','7','0','1','0','0','0','0','0'),
			   ('7.1','1','2','4','Оформление технической документации','7','1','2','0','0','0','0','0'),
			   ('7.2','1','2','4','Документирование программного обеспечения','7','1','1','0','0','0','0','0'),
			   ('8.0','1','2','4','Дисциплина по выбору №3 Курсовая работа','7','0','5','0','0','0','0','0'),
			   ('8.1','1','2','4','Технологии программирования','7','2','2','0','0','0','0','0'),
			   ('8.2','1','2','4','Программирование на языке С++','7','1','2','0','0','0','0','0'),
			   
			   -- ПРОФ
			   ('1.0','1','3','5','Электротехника, электроника и схемотехника (электротехника)','8','3','0','0','0','0','0','0'),
			   ('2.0','1','3','5','Электротехника, электроника и схемотехника (электротехника)','7','4','0','0','0','0','0','0'),
			   ('3.0','1','3','5','Курсовой проект. Схемотехника','7','0','5','0','0','0','0','0'),
			   ('4.0','1','3','5','Программирование','7','1','0','0','0','0','0','0'),
			   ('5.0','1','3','5','Курсовая работа. Программирование','7','0','3','0','0','0','0','0'),
			   ('6.0','1','3','5','Инженерная и компьютерная графика','9','0','1','0','0','0','0','0'),
			   ('7.0','1','3','5','Защита информации','7','8','0','0','0','0','0','0'),
			   ('8.0','1','3','5','ЭВМ и периферийные устройства','7','7','0','0','0','0','0','0'),
			   ('9.0','1','3','5','Операционные системы','7','6','0','0','0','0','0','0'),
			   ('10.0','1','3','5','Базы данных','7','6','0','0','0','0','0','0'),
			   ('11.0','1','3','5','Курсовая работа. Базы данных','7','0','7','0','0','0','0','0'),
			   ('12.0','1','3','5','Компьютерные сети и телекоммуникации','7','7','0','0','0','0','0','0'),
			   ('13.0','1','3','5','Курсовой проект. Компьютерные сети и телекоммуникации','7','0','7','0','0','0','0','0'),
			   ('14.0','1','3','5','Безопасность жизнедеятельности','6','0','2','0','0','0','0','0'),
			   ('15.0','1','3','5','Метрология, стандартизация и сертификация','7','0','4','0','0','0','0','0'),
			   
			   ('1.0','1','3','6','Системное программирование','7','4','3','0','0','0','0','0'),
			   ('2.0','1','3','6','Курсовая работа. Системное программирование.','7','0','4','0','0','0','0','0'),
			   ('3.0','1','3','6','Системное программное обеспечение','7','6','0','0','0','0','0','0'),
			   ('4.0','1','3','6','Курсовая работа. Системное программное обеспечение','7','0','6','0','0','0','0','0'),
			   ('5.0','1','3','6','Архитектура компьютеров','7','5','0','0','0','0','0','0'),
			   ('6.0','1','3','6','Прогаммно-аппаратная организация компьютеров','7','0','1','0','0','0','0','0'),
			   ('7.0','1','3','6','Прикладная теория цифровых автоматов (ПТЦА)','7','3','0','0','0','0','0','0'),
			   ('8.0','1','3','6','Курсовая работа. ПТЦА','7','0','4','0','0','0','0','0'),
			   ('9.0','1','3','6','Параллельные и распределенные вычисления','7','7','0','0','0','0','0','0'),
			   ('10.0','1','3','6','Курсовой проект. Параллельные и распределенные вычисления','7','0','8','0','0','0','0','0'),
			   ('11.0','1','3','6','Проектирование микропроцессорных систем','7','7','0','0','0','0','0','0'),
			   ('12.0','1','3','6','Курсовой проект. Проектирование микропроцессорных систем','7','0','7','0','0','0','0','0'),
			   ('13.0','1','3','6','Инженерия программного обеспечения','7','5','0','0','0','0','0','0'),
			   ('14.0','1','3','6','Управление в технических системах','7','6','0','0','0','0','0','0'),
			   ('15.0','1','3','6','Научно-исследовательская работа студентов','7','0','8','0','0','0','0','0'),
			   
			   -- ПРОФ Выбор
			   ('16.0','1','3','6','Дисциплина по выбору №1','7','8','0','0','0','0','0','0'),
			   ('17.1','1','3','6','Технологии проектирования компьютерных систем','7','5','5','0','0','0','0','0'),
			   ('18.2','1','3','6','Моделирование','7','5','5','0','0','0','0','0'),
			   ('19.0','1','3','6','Дисциплина по выбору №2','7','8','0','0','0','0','0','0'),
			   ('20.1','1','3','6','Специализированные архитектуры ЭВМ','7','5','5','0','0','0','0','0'),
			   ('20.2','1','3','6','Адаптивные интеллектуальные системы','7','5','5','0','0','0','0','0'),
			   ('21.0','1','3','6','Дисциплина по выбору №3','7','4','0','0','0','0','0','0'),
			   ('22.1','1','3','6','Web-программирование','7','5','5','0','0','0','0','0'),
			   ('22.2','1','3','6','Информационные технологии','7','5','5','0','0','0','0','0'),
			   ('23.0','1','3','6','Дисциплина по выбору №4','7','8','0','0','0','0','0','0'),
			   ('23.1','1','3','6','Интеллектуальные системы','7','5','5','0','0','0','0','0'),
			   ('23.2','1','3','6','Основы теории  нейронных сетей','7','5','5','0','0','0','0','0'),
			   ('24.0','1','3','6','Дисциплина по выбору №5','7','0','6','0','0','0','0','0'),
			   ('24.1','1','3','6','Программирование для мобильных устройств','7','5','5','0','0','0','0','0'),
			   ('24.2','1','3','6','Системы реального времени','7','5','5','0','0','0','0','0');


INSERT INTO subject_assignment (key_subject_fk, course_num_sa, semester_num_sa,
								key_type_fk, hour_lec_sa, hour_lab_sa, hour_prac_sa,
								hour_self_sa, hour_exam_sa)
		VALUES -- ГСЭ
				 ('1','1','2','1','1','0','1','2','0'),
				 ('2','2','4','1','2','0','1','1','0'),
				 ('3','1','1','1','0','0','2','2','0'),
				 ('4','1','1','1','1','0','1','2','0'),
			   
				 ('5','3','5','1','1','0','1','1','0'),
				 ('6','2','3','1','1','0','1','1','0'),
				 ('7','3','5','1','1','0','1','1','0'),
				 ('8','3','6','1','2','0','1','2','0'),
				 ('9','1','1','1','2','0','1','1','0'),
				
				 ('10','4','7','1','2','0','1','3','0'),
				 ('11','4','7','1','2','0','1','3','0'),
				 ('12','4','7','1','2','0','1','3','0'),
				 ('13','4','7','1','1','0','3','1','0'),
				 ('14','4','7','1','1','0','3','1','0'),
		 		 ('15','4','7','1','1','0','3','1','0'),
				 ('16','4','8','1','0','0','1','1','0'),  
				 ('17','4','8','1','0','0','1','1','0'),   
				 ('18','4','8','1','0','0','1','1','0'),   				
				 ('19','1','1','1','2','0','2','4','0'),
				 ('20','1','2','1','2','1','1','4','0'),
				 ('21','1','1','1','2','0','1','2','0'),
				 -- МЕН
				 ('22','2','3','2','1','0','3','1','0'),
				 ('23','1','2','2','2','0','2','3','0'),
				 ('24','2','3','2','1','0','1','2','0'),
				 ('25','2','3','2','2','1','1','4','0'),
				 ('26','3','5','2','2','2','0','4','0'),
				 ('27','3','6','2','0','0','1','1','0'),

				 ('28','2','4','2','2','2','0','3','0'),
				 ('29','2','4','2','2','2','0','3','0'),
				 ('30','2','4','2','2','2','0','3','0'),
				 ('31','1','1','2','1','0','1','3','0'),
				 ('32','1','1','2','1','0','1','3','0'),
				 ('33','1','1','2','1','0','1','3','0'),
				 ('34','3','5','2','0','0','1','1','0'),			
				 ('35','1','1','2','1','0','1','3','0'),
				 ('36','1','1','2','1','0','1','3','0'),				 
				 ('37','2','3','2','2','1','1','3','0'),
				 ('38','2','4','2','2','2','1','4','0'),
				 ('39','3','5','2','0','0','0','2','0'),
				 -- ПРОФ
				 ('40','1','1','3','2','2','1','4','0'),
				 ('41','2','3','3','0','0','0','2','0'),
				 ('42','1','1','3','1','1','0','2','0'),
				 ('43','4','8','3','3','3','0','6','0'),
				 ('44','4','7','3','2','2','0','5','0'),
				 ('45','3','6','3','2','2','0','4','0'),
				 ('46','3','6','3','2','2','0','4','0'),
				 ('47','4','7','3','0','0','1','2','0'),
				 ('48','4','7','3','2','2','1','5','0'),
				 ('49','4','7','3','0','0','0','2','0'),
				 ('50','1','2','3','1','0','1','3','0'),
				 ('51','2','4','3','1','1','0','1','0'),

				 ('52','2','3','3','2','2','0','3','0'),
				 ('53','2','4','3','0','0','0','2','0'),
				 ('54','3','5','3','2','2','0','4','0'),
				 ('55','3','6','3','0','0','0','2','0'),
				 ('56','3','5','3','2','2','0','4','0'),
				 ('57','1','1','3','1','1','0','2','0'),
				 ('58','2','3','3','2','2','0','4','0'),
				 ('59','2','4','3','0','0','1','2','0'),
				 ('60','4','7','3','2','2','0','4','0'),
				 ('61','4','8','3','0','0','1','3','0'),
				 ('62','4','7','3','2','2','1','4','0'),
				 ('63','2','3','3','0','0','0','2','0'),
				 ('64','2','4','3','1','2','0','3','0'),
				 ('65','3','6','3','1','2','0','3','0'),
				 ('66','4','7','3','0','0','0','2','0'),

				 ('67','4','8','3','4','4','0','8','0'),
				 ('68','4','8','3','4','4','0','8','0'),
				 ('69','4','8','3','4','4','0','8','0'),
				 ('70','4','8','3','2','2','0','4','0'),
				 ('71','4','8','3','2','2','0','4','0'),
				 ('72','4','8','3','2','2','0','4','0'),
				 ('73','2','4','3','2','3','0','4','0'),
				 ('74','2','4','3','2','3','0','4','0'),
				 ('75','2','4','3','2','3','0','4','0'),
				 ('76','4','8','3','2','2','0','4','0'),
				 ('77','4','8','3','2','2','0','4','0'),
				 ('78','4','8','3','2','2','0','4','0'),
				 ('79','3','6','3','2','2','0','3','0');
			   -- +3
			  
			  
CREATE OR REPLACE FUNCTION create_sub(cyclefk int)
  RETURNS VOID AS
	$func$
	BEGIN
	EXECUTE format('
	DROP TABLE IF EXISTS get_subjects;
   		CREATE TABLE get_subjects AS
		select
	key_subject_pk
  , key_parts_fk	
  , key_subject
  , name_s
  , key_department_fk
  , exams_s
  , setoff_s
  , lect_s + lab_s + pract_s + ksr_s + bsr_s as sum_all
  , lect_s + lab_s + pract_s as sum_llp
  , lect_s
  , lab_s
  , pract_s
  , ksr_s + bsr_s as sum_kb
  , ksr_s
  , bsr_s
  
  , sum(case when semester_num_sa = 1 then hour_lec_sa end) as hour_lec_sa_1
  , sum(case when semester_num_sa = 1 then hour_lab_sa end) as hour_lab_sa_1
  , sum(case when semester_num_sa = 1 then hour_prac_sa end) as hour_prac_sa_1 
  , sum(case when semester_num_sa = 1 then hour_self_sa end) as hour_self_sa_1

  , sum(case when semester_num_sa = 2 then hour_lec_sa end) as hour_lec_sa_2
  , sum(case when semester_num_sa = 2 then hour_lab_sa end) as hour_lab_sa_2
  , sum(case when semester_num_sa = 2 then hour_prac_sa end) as hour_prac_sa_2 
  , sum(case when semester_num_sa = 2 then hour_self_sa end) as hour_self_sa_2

  , sum(case when semester_num_sa = 3 then hour_lec_sa end) as hour_lec_sa_3
  , sum(case when semester_num_sa = 3 then hour_lab_sa end) as hour_lab_sa_3
  , sum(case when semester_num_sa = 3 then hour_prac_sa end) as hour_prac_sa_3 
  , sum(case when semester_num_sa = 3 then hour_self_sa end) as hour_self_sa_3

  , sum(case when semester_num_sa = 4 then hour_lec_sa end) as hour_lec_sa_4
  , sum(case when semester_num_sa = 4 then hour_lab_sa end) as hour_lab_sa_4
  , sum(case when semester_num_sa = 4 then hour_prac_sa end) as hour_prac_sa_4
  , sum(case when semester_num_sa = 4 then hour_self_sa end) as hour_self_sa_4
  
  , sum(case when semester_num_sa = 5 then hour_lec_sa end) as hour_lec_sa_5
  , sum(case when semester_num_sa = 5 then hour_lab_sa end) as hour_lab_sa_5
  , sum(case when semester_num_sa = 5 then hour_prac_sa end) as hour_prac_sa_5 
  , sum(case when semester_num_sa = 5 then hour_self_sa end) as hour_self_sa_5

  , sum(case when semester_num_sa = 6 then hour_lec_sa end) as hour_lec_sa_6
  , sum(case when semester_num_sa = 6 then hour_lab_sa end) as hour_lab_sa_6
  , sum(case when semester_num_sa = 6 then hour_prac_sa end) as hour_prac_sa_6 
  , sum(case when semester_num_sa = 6 then hour_self_sa end) as hour_self_sa_6

  , sum(case when semester_num_sa = 7 then hour_lec_sa end) as hour_lec_sa_7
  , sum(case when semester_num_sa = 7 then hour_lab_sa end) as hour_lab_sa_7
  , sum(case when semester_num_sa = 7 then hour_prac_sa end) as hour_prac_sa_7 
  , sum(case when semester_num_sa = 7 then hour_self_sa end) as hour_self_sa_7

  , sum(case when semester_num_sa = 8 then hour_lec_sa end) as hour_lec_sa_8
  , sum(case when semester_num_sa = 8 then hour_lab_sa end) as hour_lab_sa_8
  , sum(case when semester_num_sa = 8 then hour_prac_sa end) as hour_prac_sa_8
  , sum(case when semester_num_sa = 8 then hour_self_sa end) as hour_self_sa_8
from
  subjects s
  inner join subject_assignment a
    on s.key_subject_pk = a.key_subject_fk
  where s.key_cycle_fk = '||cyclefk||'
group by
  key_subject_pk
order by key_subject;');
END
$func$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION summ_sub(keyf integer) RETURNS TABLE (summ smallint)
AS $$  
BEGIN
	SELECT SUM(exams_s) INTO summ FROM subjects WHERE key_cycle_fk = keyf;	RETURN NEXT;
	SELECT SUM(setoff_s) INTO summ FROM subjects WHERE key_cycle_fk = keyf;	RETURN NEXT;
	SELECT SUM(lect_s) + SUM(lab_s) + SUM(pract_s) + SUM(ksr_s) + SUM(bsr_S) INTO summ FROM subjects WHERE key_cycle_fk = keyf;	RETURN NEXT;
	SELECT SUM(lect_s) + SUM(lab_s) + SUM(pract_s) INTO summ FROM subjects WHERE key_cycle_fk = keyf;	RETURN NEXT;
	SELECT SUM(lect_s) INTO summ FROM subjects WHERE key_cycle_fk = keyf;	RETURN NEXT;
	SELECT SUM(lab_s) INTO summ FROM subjects WHERE key_cycle_fk = keyf;	RETURN NEXT;
	SELECT SUM(pract_s) INTO summ FROM subjects WHERE key_cycle_fk = keyf;	RETURN NEXT;
	SELECT SUM(ksr_s) + SUM(bsr_S) INTO summ FROM subjects WHERE key_cycle_fk = keyf;	RETURN NEXT;
	SELECT SUM(ksr_s) INTO summ FROM subjects WHERE key_cycle_fk = keyf;	RETURN NEXT;
	SELECT SUM(bsr_s) INTO summ FROM subjects WHERE key_cycle_fk = keyf;	RETURN NEXT;
	
	SELECT SUM(hour_lec_sa) INTO summ FROM subject_assignment WHERE key_type_fk = keyf AND semester_num_sa=1;	RETURN NEXT;
	SELECT SUM(hour_lab_sa) INTO summ FROM subject_assignment WHERE key_type_fk = keyf AND semester_num_sa=1;	RETURN NEXT;
	SELECT SUM(hour_prac_sa) INTO summ FROM subject_assignment WHERE key_type_fk = keyf AND semester_num_sa=1;	RETURN NEXT;
	SELECT SUM(hour_self_sa) INTO summ FROM subject_assignment WHERE key_type_fk = keyf AND semester_num_sa=1;	RETURN NEXT;

	SELECT SUM(hour_lec_sa) INTO summ FROM subject_assignment WHERE key_type_fk = keyf AND semester_num_sa=2;	RETURN NEXT;
	SELECT SUM(hour_lab_sa) INTO summ FROM subject_assignment WHERE key_type_fk = keyf AND semester_num_sa=2;	RETURN NEXT;
	SELECT SUM(hour_prac_sa) INTO summ FROM subject_assignment WHERE key_type_fk = keyf AND semester_num_sa=2;	RETURN NEXT;
	SELECT SUM(hour_self_sa) INTO summ FROM subject_assignment WHERE key_type_fk = keyf AND semester_num_sa=2;	RETURN NEXT;

	SELECT SUM(hour_lec_sa) INTO summ FROM subject_assignment WHERE key_type_fk = keyf AND semester_num_sa=3;	RETURN NEXT;
	SELECT SUM(hour_lab_sa) INTO summ FROM subject_assignment WHERE key_type_fk = keyf AND semester_num_sa=3;	RETURN NEXT;
	SELECT SUM(hour_prac_sa) INTO summ FROM subject_assignment WHERE key_type_fk = keyf AND semester_num_sa=3;	RETURN NEXT;
	SELECT SUM(hour_self_sa) INTO summ FROM subject_assignment WHERE key_type_fk = keyf AND semester_num_sa=3;      RETURN NEXT;

	SELECT SUM(hour_lec_sa) INTO summ FROM subject_assignment WHERE key_type_fk = keyf AND semester_num_sa=4;	RETURN NEXT;
	SELECT SUM(hour_lab_sa) INTO summ FROM subject_assignment WHERE key_type_fk = keyf AND semester_num_sa=4;	RETURN NEXT;
	SELECT SUM(hour_prac_sa) INTO summ FROM subject_assignment WHERE key_type_fk = keyf AND semester_num_sa=4;	RETURN NEXT;
	SELECT SUM(hour_self_sa) INTO summ FROM subject_assignment WHERE key_type_fk = keyf AND semester_num_sa=4;	RETURN NEXT;

	SELECT SUM(hour_lec_sa) INTO summ FROM subject_assignment WHERE key_type_fk = keyf AND semester_num_sa=5;	RETURN NEXT;
	SELECT SUM(hour_lab_sa) INTO summ FROM subject_assignment WHERE key_type_fk = keyf AND semester_num_sa=5;	RETURN NEXT;
	SELECT SUM(hour_prac_sa) INTO summ FROM subject_assignment WHERE key_type_fk = keyf AND semester_num_sa=5;	RETURN NEXT;
	SELECT SUM(hour_self_sa) INTO summ FROM subject_assignment WHERE key_type_fk = keyf AND semester_num_sa=5;	RETURN NEXT;

	SELECT SUM(hour_lec_sa) INTO summ FROM subject_assignment WHERE key_type_fk = keyf AND semester_num_sa=6;	RETURN NEXT;
	SELECT SUM(hour_lab_sa) INTO summ FROM subject_assignment WHERE key_type_fk = keyf AND semester_num_sa=6;	RETURN NEXT;
	SELECT SUM(hour_prac_sa) INTO summ FROM subject_assignment WHERE key_type_fk = keyf AND semester_num_sa=6;	RETURN NEXT;
	SELECT SUM(hour_self_sa) INTO summ FROM subject_assignment WHERE key_type_fk = keyf AND semester_num_sa=6;	RETURN NEXT;

	SELECT SUM(hour_lec_sa) INTO summ FROM subject_assignment WHERE key_type_fk = keyf AND semester_num_sa=7;	RETURN NEXT;
	SELECT SUM(hour_lab_sa) INTO summ FROM subject_assignment WHERE key_type_fk = keyf AND semester_num_sa=7;	RETURN NEXT;
	SELECT SUM(hour_prac_sa) INTO summ FROM subject_assignment WHERE key_type_fk = keyf AND semester_num_sa=7;	RETURN NEXT;
	SELECT SUM(hour_self_sa) INTO summ FROM subject_assignment WHERE key_type_fk = keyf AND semester_num_sa=7;	RETURN NEXT;

	SELECT SUM(hour_lec_sa) INTO summ FROM subject_assignment WHERE key_type_fk = keyf AND semester_num_sa=8;	RETURN NEXT;
	SELECT SUM(hour_lab_sa) INTO summ FROM subject_assignment WHERE key_type_fk = keyf AND semester_num_sa=8;	RETURN NEXT;
	SELECT SUM(hour_prac_sa) INTO summ FROM subject_assignment WHERE key_type_fk = keyf AND semester_num_sa=8;	RETURN NEXT;
	SELECT SUM(hour_self_sa) INTO summ FROM subject_assignment WHERE key_type_fk = keyf AND semester_num_sa=8;	RETURN NEXT;
END
$$ 
LANGUAGE plpgsql;





