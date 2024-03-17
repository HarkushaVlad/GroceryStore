# Grocery Store Management System

This Java project is a simple grocery store management system implemented using JDBC MySQL databases and JavaFX for the user interface. It serves as an educational project for learning database management and GUI development.

## Overview

The system is designed to manage client data, product information, and purchase records. It provides functionalities like user authentication, database navigation, query execution, search capability, and a graphical user interface.

## Database Setup

To set up the database for this project, you can use the SQL files provided in the `GroceryStoreDump` directory. These SQL files contain scripts for creating and populating the necessary tables.

### Instructions:

1. Execute the SQL scripts in your MySQL database management tool to create the tables and populate them with sample data.

### User Accounts:

You will need to create user accounts with appropriate access to the tables. You can create one user with full access to all tables or separate users for different roles.

### Connection Configuration:

Configure the database connection settings in the `src/main/resources/application.properties` file using the following format:

```properties
db.host=your_database_host
db.port=your_database_port
db.name=your_database_name
db.user_authentication.login.name=authentication_user_name
db.user_authentication.password=authentication_user_password
db.client.login.name=client_user_name
db.client.password=client_user_password
db.employee.login.name=employee_user_name
db.employee.password=employee_user_password
```

Ensure that the users have the necessary privileges to access the database and perform operations on the tables.