# Voting System 
(REST service to vote for restaurants)

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/d0fa5de3eee74ec6876edd92e4fc2f4e)](https://www.codacy.com/manual/GlyzinAI/votingsystem?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=GlyzinAI/votingsystem&amp;utm_campaign=Badge_Grade)
[![Build Status](https://travis-ci.org/GlyzinAI/votingsystem.svg?branch=master)](https://travis-ci.org/GlyzinAI/votingsystem)

<h2>Постановка задачи:</h2>

Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) without frontend.

Build a voting system for deciding where to have lunch.

* 2 types of users: admin and regular users
* Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
* Menu changes each day (admins do the updates)
* Users can vote on which restaurant they want to have lunch at
* Only one vote counted per user
* If user votes again the same day:
    - If it is before 11:00 we asume that he changed his mind.
    - If it is after 11:00 then it is too late, vote can't be changed
    
Each restaurant provides new menu each day.

## Запуск
```
$ mvn package
$ mvn cargo:run
```

## Users
After start available 2 users

Username | Password | Role
-------- | -------- | ----
admin@gmail.com | admin | ADMIN
user@gmail.com | password | USER

After this commands you will start hosting website on page `localhost:8080/voter`

# API.

## Profile API

### URL Pages 

Description | Method | URL | Body | User
----------- | ------ | --- | ---- | ----
Get current | GET | `{URL}/users` | none | Authorized
Update current | PUT | `{URL}/users` | Update Body | Authorized
Delete current | DELETE | `{URL}/users` | none | Authorized
Get All |GET |`{URL}/admin/users` | none |Only Admin
Get  | GET | `{URL}/admin/users/{ID}` | none | Only Admin
Delete  | DELETE | `{URL}/admin/users/(ID}` | none | Only Admin
Update | PUT | `{URL}/admin/users` |Update Body| Only Admin
Create | POST | `{URL}/admin/users` | Create Body | Only Admin
Register Profile | POST | `{URL}/users/register` | Register Body | Not Authorized

## Bodies
#### Register Body
```json
{
    "name": "New User",
    "email": "newUser@gmail.com",
    "password": "12345",
    "roles": [
            "ROLE_USER"
        ]
}
``` 

#### Update Body
```json
{
    "id"  : 102,
    "name": "Anton",
    "email": "newmail@gmail.com",
    "password": "newPass",
    "roles": [
                "ROLE_USER"
            ]
}
```

#### Create Body
```json
{
    "name": "Nikita",
    "email": "nikita@gmail.com",
    "password": "12345",
    "roles": [
            "ROLE_USER"
        ]
}
``` 
or
```json
{
    "name": "Nikita",
    "email": "nikita@gmail.com",
    "password": "12345",
    "roles": [
            "ROLE_USER", "ROLE_ADMIN"
        ]
}
```

## Restaurant API

### URL Pages 

Description | Method | URL | Body | User
----------- | ------ | --- | ---- | ----
Get restaurants with menu | GET | `{URL}/restaurants/today` | none | Authorized
Get | GET | `{URL}/restaurants/{ID}` | none | Authorized
Get All |GET |`{URL}/admin/restaurants` | none |Only Admin
Delete  | DELETE | `{URL}/admin/restaurants/(ID}` | none | Only Admin
Update | PUT | `{URL}/admin/restaurants/{ID}` | Update Body |Only Admin
Create | POST | `{URL}/admin/restaurants` | Create Body | Only Admin

## Bodies
#### Update Body
```json
{
    "name": "Update restaurant"
}
```

#### Create Body
```json
{
    "name": "New restaurant"
}
```

## Dishes API

### URL Pages 

Description | Method | URL | Body | User
----------- | ------ | --- | ---- | ----
Get All dishes by restaurant | GET | `{URL}/admin/restaurants/{ID}/dishes` | none | Only Admin
Get Dish By Restaurant| GET | `{URL}/admin/restaurants/{restID}/dishes/{dishID}` | none | Only Admin
Get Today Dish By Restaurant  |GET |`{URL}/admin/restaurants/{userID}/dishes/today` | none |Only Admin
Delete  | DELETE | `{URL}/admin/restaurants/(restID}/dishes/{dishID}` | none | Only Admin
Update | PUT | `{URL}/admin/restaurants/{restID}/dishes/{dishID}` | Update Body |Only Admin
Create | POST | `{URL}/admin/restaurants/{restID}/dishes` | Create Body | Only Admin

## Bodies
#### Update Body
```json
{
    "name": "Update dish",
    "price" : 222,
    "date" : "2019-09-17"
}
```

#### Create Body
```json
{
    "name"  : "New dish",
    "price" :  543,
    "date"  : "2019-09-15"
}
```

## Vote API

### URL Pages 

Description | Method | URL | Body | User
----------- | ------ | --- | ---- | ----
Show personal today vote | GET | `{URL}/votes` | none | Authorized
Vote for the restaurant| POST | `{URL}/votes/{restID}` | none | Authorized
Get All  |GET |`{URL}/admin/votes` | none |Only Admin
Get  | GET | `{URL}/admin/votes/{ID}` | none | Only Admin
Get All today | GET | `{URL}/admin/votes/today` | none |Only Admin
Delete | DELETE | `{URL}/admin/votes/{ID}` | none | Only Admin
