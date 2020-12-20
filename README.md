# GoodFood

Study web application for an imaginery food delivery service.

## Directory tree

* additions
  * __database.sql__ - Database dump
  * __items.sql__ - Sample food items
  * __international.sql__ - Sample i18n records
* SpringMvc
  * __Backend__ - Maven project of HTTP RESTful API
  * __Frontend__ - Maven project of Frontend web application

## Development and Usage

1. Install JRE and JDK.
2. Install Maven.
3. Install Tomcat.
4. Install PostgreSQL.
5. Import database.sql to psql. It will clean the current database and fill with GoodFood DB.
6. Import items.sql and international.sql to a PostgreSQL DB.
7. jdbc.properties for Backend.
8. Deploy Backend.
9. BASE_URL_REST in Constant.java of Frontend.
10. Deploy Frontend.
