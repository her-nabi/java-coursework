﻿# Content Management System
____

Разработка микросервиса ***«Content Management System»*** в рамках курсовой работы 
 ***«Система персонализированного контента»***.
____

### Технологии
Для разработки проекта были использованы:

* Spring Framework (Core, Boot, Data JPA, MVC, Security)
* Lombok
* PostgreSQL
* Swagger
* SLF4J-reload4j
* MapStruct
****
## #Запуск проекта
**Туториал по запуску проекта:**

1. Используя СУБД PostgreSQL создаем базу данных. 
2. В application.properties вносим следующие изменения:

    * spring.datasource.url=jdbc:postgresql://localhost:5432/Имя созданной бд

    * spring.datasource.username=Имя вашего пользователя СУБД

    * spring.datasource.password=Пароль

3. Команда для запуска проекта через терминал (предварительно убедитесь, что на вашем устройстве установлен Gradle):

        gradle bootRun

4. Для запуска тестов, введите:

       gradle clean test
---
### Общая информация
Основные функции:
1. Авторизация администратора CMS через	JWT (Spring Security)
2. Формирование	контента и связи контента с рекламными плейсментами (в
   авторизованном контексте)
3. Публикация контента – изменение статуса (DRAFT/PUBLISHED) и отправка
   информации о	контенте в CDS и AMS при переходе в	состояние PUBLISHED (в
   авторизованном контексте)
4. Необходимо реализовать тесты (или тестовое приложение для проверки
   функционала	приложения.	Покрыть	тестами	как	эндпоинты так и методы для
   работы с БД
