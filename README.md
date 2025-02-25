# Task Manager API

Этот проект представляет собой REST API для управления задачами. Пользователи с разными ролями могут создавать, назначать, редактировать и выполнять задачи.

## 📌 Иерархия пользователей

- **Chief**: Создаёт пользователей.
- **Senior Manager**: Создаёт задачи, редактирует их и назначает исполнителей.
- **Manager**: Назначает задачи исполнителям.
- **Senior Employee**: Может посмотреть все задачи, выбирать себе и выполнять их.
- **Employee**: Видит только свои задачи и выполняет их.


## 🔧 Запуск проекта

### 1️⃣ Настроить базу данных

Необходимо создать базу данных PostgreSQL и обновить `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.jpa.generate-ddl=true
spring.jpa.hibernate.ddl-auto=update
```
### 2️⃣ Соберите приложение с помощью maven

  ```bash
  mvn clean install
  mvn spring-boot:run
```

### 3️⃣ Тестирование API

Используйте postman или curl для отправки запросов.

## API Эндпоинты

### 🔹 Chief Controller (`/api/chief`)

| Метод | Эндпоинт   | Описание             | Доступ  |
|--------|------------|----------------------|---------|
| `POST` | `/add-user` | Создать пользователя | `CHIEF` |

### 🔹 Manager Controller (`/api/manager`)

| Метод  | Эндпоинт       | Описание                         | Доступ                        |
|--------|---------------|----------------------------------|-------------------------------|
| `POST` | `/create-task` | Создать задачу                  | `SENIOR_MANAGER`, `CHIEF`     |
| `GET`  | `/tasks`       | Получить список всех задач      | `SENIOR_MANAGER`, `MANAGER`   |
| `GET`  | `/workers`     | Получить список работников      | `SENIOR_MANAGER`, `MANAGER`   |
| `PUT`  | `/set-worker`  | Назначить исполнителя на задачу | `SENIOR_MANAGER`, `MANAGER`   |
| `PUT`  | `/set-subject` | Изменить задачу                 | `SENIOR_MANAGER`              |

### 🔹 Employee Controller (`/api/employee`)

| Метод  | Эндпоинт         | Описание                   | Доступ                       |
|--------|-----------------|----------------------------|------------------------------|
| `GET`  | `/mytasks`      | Посмотреть свои задачи       | `EMPLOYEE`, `SENIOR_EMPLOYEE` |
| `GET`  | `/tasks`        | Посмотреть все доступные задачи  | `SENIOR_EMPLOYEE`            |
| `PUT`  | `/take-task/{id}` | Взять задачу              | `SENIOR_EMPLOYEE`            |
| `PUT`  | `/set-solve/{id}` | Завершить задачу          | `EMPLOYEE`, `SENIOR_EMPLOYEE` |

### 🎯 TODO

- Добавить JWT для аутентификации.
- Улучшить логирование.
