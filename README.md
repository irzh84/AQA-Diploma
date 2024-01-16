[![Java CI with Gradle](https://github.com/irzh84/AQA-Diploma/actions/workflows/gradle.yml/badge.svg)](https://github.com/irzh84/AQA-Diploma/actions/workflows/gradle.yml)

# Дипломный проект по профессии «Тестировщик»
[План автоматизации тестирования веб-сервиса покупки тура](https://github.com/irzh84/AQA-Diploma/blob/main/docs/Plan.md)

[Отчёт по итогам тестирования](https://github.com/irzh84/AQA-Diploma/blob/main/docs/Report.md)

[Отчёт по итогам автоматизации](https://github.com/irzh84/AQA-Diploma/blob/main/docs/Summary.md)

Дипломный проект представлен автоматизацией тестирования комплексного веб-сервиса, взаимодействующего с эмулятором банковских сервисов. Веб-сервис предлагает купить тур по определённой цене двумя способами: обычная оплата по дебетовой карте и покупка в кредит по данным банковской карты. Приложение в собственной СУБД должно сохранять информацию о том, успешно ли был совершён платёж и каким способом. Данные карт при этом сохранять не допускается.  

## Процедура запуска автотестов 

Для запуска автотестов необходимо склонировать репозиторий с кодом в вашу папку с проектом на локальный компьютер:

`git clone git@github.com:irzh84/AQA-Diploma.git`

Открыть папку в IDEA.

Для запуска нужно заранее установить, настроить и запустить Docker Desktop.

В терминале IDEA ввести команды, указанные ниже. 
             
### Команда для запуска контейнеров  

`docker-compose up --build`

### Команда для запуска веб-сервиса с указанием пути к базе данных: 

1. Для mysql:

`java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar`
   
2. Для postgresql

`java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar`

### Команда для запуска тестов с параметрами и указанием пути к базе данных: 

1. Для mysql

`./gradlew clean test "-Ddb.url=jdbc:mysql://localhost:3306/app"`

2. Для postgresql

`./gradlew clean test "-Ddb.url=jdbc:postgresql://localhost:5432/app"` 

### Команда для генерации отчёта в Allure и автоматическое открытие в браузере:

`./gradlew allureServe`

## Завершение работы

После автотестов и завершения работы можно использовать в терминале IDEA команды, указанные ниже.

### Команда для отключения работы веб-сервиса:

`сочетание клавиш CTRL+C` в окне терминала, где был запущен веб-сервис.

### Команда для остановки и удаления контейнеров: 

`docker-compose down`
