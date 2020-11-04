USE hrinkov_social_network;

ALTER TABLE locations AUTO_INCREMENT = 1;

INSERT INTO locations VALUES
(NULL, 'Belarus', 'Minsk'),
(NULL, 'Belarus', 'Gomel'),
(NULL, 'Belarus', 'Vitebsk'),
(NULL, 'Belarus', 'Grodno'),
(NULL, 'Belarus', 'Brest'),
(NULL, 'Belarus', 'Bobruisk'),
(NULL, 'Belarus', 'Baranovichi');

ALTER TABLE schools AUTO_INCREMENT = 1;

INSERT INTO schools VALUES
(NULL, "Secondary school number 104", 1),
(NULL, "Secondary school number 95", 1),
(NULL, "Secondary school number 21", 2),
(NULL, "Secondary school number 69", 2),
(NULL, "Secondary school number 2", 3),
(NULL, "Secondary school number 4", 3),
(NULL, "Secondary school number 38", 4),
(NULL, "Secondary school number 31", 4),
(NULL, "Secondary school number 32", 4),
(NULL, "Secondary school number 8", 5),
(NULL, "Secondary school number 9", 5),
(NULL, "Secondary school number 1", 6),
(NULL, "Secondary school number 3", 6),
(NULL, "Secondary school number 12", 7),
(NULL, "Secondary school number 22", 7);

ALTER TABLE universities AUTO_INCREMENT = 1;

INSERT INTO universities VALUES
(NULL, "Academy of Management under the President of the Republic of Belarus", 1),
(NULL, "Belarusian State University", 1),
(NULL, "Belarusian State University of Transport", 2),
(NULL, "Gomel State Medical University", 2),
(NULL, "Vitebsk State University named after P.M. Masherova", 3),
(NULL, "Vitebsk State Technological University", 3),
(NULL, "Grodno State Agrarian University", 4),
(NULL, "Grodno State Medical University", 4),
(NULL, "Baranovichi State University", 5),
(NULL, "Brest State Technical University", 5);

ALTER TABLE users AUTO_INCREMENT = 1;

INSERT INTO users VALUES
(NULL, 'user1@test.com', '$2a$10$ehU.2fP2fFtcFB9Fti8u2unzBrCIzQRvUei8r/ppUzxlBxSP86eH2', 'ROLE_USER'),
(NULL, 'user2@test.com', '$2a$10$ehU.2fP2fFtcFB9Fti8u2unzBrCIzQRvUei8r/ppUzxlBxSP86eH2', 'ROLE_USER'),
(NULL, 'user3@test.com', '$2a$10$ehU.2fP2fFtcFB9Fti8u2unzBrCIzQRvUei8r/ppUzxlBxSP86eH2', 'ROLE_USER'),
(NULL, 'user4@test.com', '$2a$10$ehU.2fP2fFtcFB9Fti8u2unzBrCIzQRvUei8r/ppUzxlBxSP86eH2', 'ROLE_USER'),
(NULL, 'admin@test.com', '$2a$10$ehU.2fP2fFtcFB9Fti8u2unzBrCIzQRvUei8r/ppUzxlBxSP86eH2', 'ROLE_ADMIN');

ALTER TABLE user_profiles AUTO_INCREMENT = 1;

INSERT INTO user_profiles VALUES
(NULL, 1, '2020-07-11 10:00', '1990-07-28 03:45', 'Petya', 'Buhmetovich', '+375297665423', 1, 1, 2007, 2, 2013),
(NULL, 2, '2020-08-11 10:00', '1993-11-09 03:45', 'Artem', 'Karchevskiy', '+375294673462', 2, 3, '2007', 3, 2013),
(NULL, 3, '2020-09-11 10:00', '1996-09-21 03:45', 'Kiril', 'Myagkov', '+375291234167', 3, 5, 2007, 4, 2013),
(NULL, 4, '2019-02-05 09:53', '2001-11-11 10:00', 'Vasya', 'Potryopovich', '+375335673409', 4, 7, 2010,5, 2015);

ALTER TABLE public_messages AUTO_INCREMENT = 1;

INSERT INTO public_messages VALUES
(NULL, '2020-11-11 10:00', 1, NULL, 'Тренировки состоятся при любой погоде. Даже при землетрясении. (М. Перейра)', false),
(NULL, '2020-11-12 10:00', 1, 'К/ф "Москва слезам не верит"', 'Я предпочитаю делать в своей жизни то, что я люблю. А не то, что модно, престижно или положено.', false),
(NULL, '2020-11-13 10:00', 2, 'Autumn. September.', NULL, false),
(NULL, '2020-11-14 10:00', 2, 'Ясный теплый осенний денёк!)', 'Когда в выходной весь день работала, но к вечеру солнце выманило выйти прогуляться)', false);

ALTER TABLE public_message_comments AUTO_INCREMENT = 1;

INSERT INTO public_message_comments VALUES
(NULL, '2020-11-11 12:00', 2, 1, 'хорош!', false),
(NULL, '2020-11-11 13:00', 2, 1, 'Так что сегодня на треню))', false),
(NULL, '2020-11-12 13:00', 2, 2, 'И что ты любишь?', false),
(NULL, '2020-11-12 14:00', 1, 2, 'Ничего не делать))', false),
(NULL, '2020-11-13 13:00', 1, 3, 'и дождь(', false),
(NULL, '2020-11-13 14:00', 2, 3, 'Так это же прикольно...))', false),
(NULL, '2020-11-13 15:00', 1, 3, 'Я не люблю дождь', false),
(NULL, '2020-11-14 13:00', 1, 4, 'А где ты вышка погулять???', false),
(NULL, '2020-11-14 14:00', 2, 4, 'В Минске, парк победы))', false);

ALTER TABLE private_messages AUTO_INCREMENT = 1;

INSERT INTO private_messages VALUES
(NULL, '2020-11-11 12:00', 2, 1, 'Хай', true, false),
(NULL, '2020-11-11 12:00', 2, 1, 'У тебя есть насос для лясика?', true, false),
(NULL, '2020-11-11 12:01', 1, 2, 'привет!)', true, false),
(NULL, '2020-11-11 12:01', 1, 2, 'у меня есть автомобильный насос', true, false),
(NULL, '2020-11-11 12:01', 1, 2, 'я ним качаю велосипед', true, false),
(NULL, '2020-11-11 12:02', 2, 1, 'Он типа от прикуривателя?', true, false),
(NULL, '2020-11-11 12:02', 2, 1, 'Или ногами?)', true, false),
(NULL, '2020-11-11 12:03', 1, 2, 'руками', true, false),
(NULL, '2020-11-11 12:04', 2, 1, 'Можешь завтра взять, или он громоздкий?', true, false),
(NULL, '2020-11-11 12:05', 1, 2, 'да, хорошо, возьму))', true, false),
(NULL, '2020-11-12 12:00', 2, 1, 'Привет, ты сделал домашку?', false, false),
(NULL, '2020-11-11 12:00', 2, 1, 'У меня чет тут к БД не подключается...', false, false),
(NULL, '2020-11-11 12:00', 2, 1, 'Можешь скинуть свое подключение??', false, false);

ALTER TABLE communities AUTO_INCREMENT = 1;

INSERT INTO communities VALUES
(NULL, '2020-10-01 12:00', 1, 'SPORT', 'ENGIRUNNERS команда бегущих инженеров', 'Любим бегать. Стараемся делать это быстро. Не гуманитарии. В этой группе мы будем делиться своими успехами. Кстати, в документах у нас много хорошей спортивной литературы, пользуйтесь на здоровье!', false);

ALTER TABLE posts AUTO_INCREMENT = 1;

INSERT INTO posts VALUES
(NULL, '2020-10-28 20:11', 'Как мы сообщали ранее, несколько ребят из нашей команды были пейсмейкерами на Марьинском полумарафоне, прошедшем в минувшее воскресенье в парке 850-летия Москвы.', 'Самым быстрым пейсмейкером был Дмитрий Мартынов - он вёл участников полумарафона на результат 1:29. Фёдор Осипов бежал с темпом 4:42, что соответствовало итоговому времени 1:39, а Роман Зенов и Дмитрий Петров сопровождали желающих выбежать из двух часов, а точнее - получить итоговые цифры 1:59. И все наши ребята здорово справились с поставленной задачей и подтвердили свой статус чётких пейсеров', 1, 1, false),
(NULL, '2020-10-29 18:10', 'Очередные победы', 'Ольга Щукина в минувшее воскресенье пополнила коллекцию своих побед - она стала первой на Осеннем кроссе лыжников, проходившем близ деревни Борисово, что под Серпуховом. Оля бежала по лыжной трассе с очень непростым рельефом, суммарный набор высоты за выбранную Олей дистанцию в 5 километров составил аж 110 метров. Наша героиня прошедшей недели преодолела эту трассу за 25:59 и финишировала первой с более чем минутным отрывом от ближайшей преследовательницы! Мы поздравляем Олю и хотим радоваться её победам ещё и ещё!', 1, 1, false),
(NULL, '2020-11-11 10:00', 'Нам сегодня пять лет!', NULL, 1, 1, false);

ALTER TABLE post_comments AUTO_INCREMENT = 1;

INSERT INTO post_comments VALUES
(NULL, '2020-10-29 10:23', 2, 1, 'Круто!!!', false),
(NULL, '2020-10-30 20:02', 3, 2, 'Молодец!!!', false),
(NULL, '2020-10-30 20:02', 4, 3, 'Так держать!!!', false);

INSERT INTO friends VALUES
(1, 2), (1, 3);

COMMIT;