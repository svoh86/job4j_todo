# Приложение "TODO список"

[![Java CI with Maven](https://github.com/svoh86/job4j_todo/actions/workflows/maven.yml/badge.svg)](https://github.com/svoh86/job4j_todo/actions/workflows/maven.yml)

+ [О проекте](#О-проекте)
+ [Технологии](#Технологии)
+ [Требования к окружению](#Требования-к-окружению)
+ [Запуск проекта](#Запуск-проекта)
+ [Взаимодействие с приложением](#Взаимодействие-с-приложением)
+ [Контакты](#Контакты)

## О проекте

Web-приложение "TODO список". Показывает список дел. Возможность выбора категории задачи (все, новые, выполненные). 
Задачи можно редактировать, удалять, помечать как выполненные.
Для хранения данных используется Hibernate.
Для входа в систему пользователю необходимо зарегистрироваться. 

## Технологии

+ **Maven 3.8**
+ **Spring Boot 2.7.5**
+ **HTML 5**, **BOOTSTRAP 5**, **Thymeleaf 3.0.15**
+ **Hibernate 5.6.11**, **PostgreSQL 14**
+ **Тестирование:** **Mockito 4.0.0**, **Liquibase 3.6.2**, **H2 2.1.214**, **AssertJ 3.23.1**
+ **Java 17**
+ **Checkstyle 3.1.2**

## Требования к окружению
+ **Java 17**
+ **Maven 3.8**
+ **PostgreSQL 14**
+ **Hibernate 5.6.11**

## Запуск проекта
Перед запуском проекта необходимо настроить подключение к БД в соответствии с параметрами, 
указанными в src/main/resources/db.properties, или заменить на свои параметры.

Варианты запуска приложения:
1. Упаковать проект в jar архив (job4j_todo/target/job4j_todo-1.0.jar):
``` 
mvn package
``` 
Запустить приложение:
```
java -jar job4j_todo-1.0.jar 
```
2. Запустить приложение:
```
mvn spring-boot:run
```

## Взаимодействие с приложением
Начальная страница
![alt text](https://github.com/svoh86/job4j_todo/blob/master/img/start.png)

Все задачи конкретного пользователя. Здесь можно выбрать конкретную задачу.
![alt text](https://github.com/svoh86/job4j_todo/blob/master/img/allTasks.png)

Только выполненные задачи конкретного пользователя.
![alt text](https://github.com/svoh86/job4j_todo/blob/master/img/doneTasks.png)

Только новые задачи конкретного пользователя.
![alt text](https://github.com/svoh86/job4j_todo/blob/master/img/newTasks.png)

Для конкретной задачи можно установить состояние "Выполнено", отредактировать задачу или удалить
![alt text](https://github.com/svoh86/job4j_todo/blob/master/img/updateTask.png)

Редактирование задачи
![alt text](https://github.com/svoh86/job4j_todo/blob/master/img/editTask.png)

Регистрация пользователя
![alt text](https://github.com/svoh86/job4j_todo/blob/master/img/add.png)

Неудачная регистрация
![alt text](https://github.com/svoh86/job4j_todo/blob/master/img/failAdd.png)

Удачная регистрация
![alt text](https://github.com/svoh86/job4j_todo/blob/master/img/successAdd.png)

Авторизация
![alt text](https://github.com/svoh86/job4j_todo/blob/master/img/login.png)

Неудачная авторизация
![alt text](https://github.com/svoh86/job4j_todo/blob/master/img/failLogin.png)

## Контакты

Свистунов Михаил Сергеевич

[![Telegram](https://img.shields.io/badge/Telegram-blue?logo=telegram)](https://t.me/svoh86)

Email: sms-86@mail.ru
