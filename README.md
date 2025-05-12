## Название
Приложение GrandCapital (тестовое задание)
## Описание
Стек и тулы:
1. Java 17
2. Spring (Boot 3.4.5, JPA, Security, Cache)
3. JWT
4. PostgreSQL
5. Redis
6. Liquibase
7. Docker
8. Maven
9. Mapstruct
10. Lombok
11. JUnit, Mockito
12. Testcontainers
13. REST API
14. Swagger

Характеристики приложения:
1. 3 слоя - repo, service, controller
2. REST
3. Валидация входных данных
4. Основные сущности - User, Account, EmailData, PhoneData
5. Аутентификация с помощью JWT-токена
6. Операции CRUD для EmailData, PhoneData
7. Операция поиска для User с фильтрацией и постраничным выводом
8. Операция перевода средств между счетами пользователей с применением блокировок
9. Операция увеличения баланса на счетах пользователей с использованием планировщика и кэша

## Запуск
Пререквизиты:

1. JDK >= 17
2. Docker

Шаги:
1. Скачать код из репозитория в нужную директорию
```
git clone https://github.com/sun-serg/GrandCapital
```

2. Запустить Docker

3. Выполнить запуск Docker-контейнеров с базами данных PostgreSQL и Redis в директории проекта
```
docker-compose up -d 
```

4. Провести сборку проекта в директории проекта
При наличии установленного Maven:
```
mvn clean install
```
Через maven-wrapper
```
.\mvnw.cmd clean install
```

5. Запустить приложение из директории проекта, передав переменные окружения
```
java -jar target/GrandCapital-0.0.1-SNAPSHOT.jar --spring.datasource.url=jdbc:postgresql://localhost:5432/grand-capital --spring.datasource.username=user --spring.datasource.password=password --spring.data.redis.host=localhost
```


