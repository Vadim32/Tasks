CREATE TABLE USER_INFO(
ID INT PRIMARY KEY NOT NULL,
FIRST_NAME VARCHAR(255) NOT NULL,
LAST_NAME VARCHAR(255) NOT NULL,
MIDDLE_NAME VARCHAR(255),
USER_NAME VARCHAR(255) NOT NULL,
PASSWORD VARCHAR(255) NOT NULL,
USER_POSITION VARCHAR(255) NOT NULL
);

insert into USER_INFO values(1,'Vadim', 'Demidov', 'Alexandrovich', 'Vad', '1234', 'REGULAR');