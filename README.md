# Design Office Management System
> Design Office Management System is a web application designed to support design companies in managing human and financial resources. Developed in Java, this application offers comprehensive support for project managers,
> enabling real-time control over project budgets and employee engagement in task execution. An additional feature includes monitoring software licenses used by individual employees.

 <!-- If you have the project hosted somewhere, include the link here. > Live demo [_here_](https://www.example.com). -->

## Table of Contents
* [Technologies Used](#technologies-used)
* [Features](#features)
* [Screenshots](#screenshots)
* [Setup](#setup)
<!-- * [License](#license) -->

## Technologies Used
- Java 17 
- Maven 
- SpringBoot 3 (Spring Core, SpringData JPA, SpringSecurity)
- MySQL 8.0.31
- Liquibase
- Hibernate 
- Junit 5 
- MockMvc
- Lombok


## Features
- Employee Management - The employee profile is linked to a technical user of the application (registration, login), who can make modifications within the employee data, including: adding, editing employee profiles, 
reassigning projects, assigning employees to industry teams, and monitoring their activity (timesheet).
- Software License Management - The system allows assigning each employee a software license along with its cost. The application supports multi-currency operations - it continuously calculates the cost based on the exchange rate automatically retrieved from the NBP API.
- Project Management - The system enables adding, editing new projects, and ongoing monitoring of budget and numerical engagement of employees.
- Budget Monitoring - The application allows monitoring the budget for each project within a selected time period.


## Screenshots
Database diagram:
![DB_diagram](https://github.com/ADobrowolska/design-office-management-system/assets/146584571/356a5138-c52d-49e2-b990-76bca29859e6)
<!-- If you have screenshots you'd like to share, include them here. ![Example screenshot](./img/screenshot.png) -->


## Setup
In order to run this application you will need:
- Java 17 - Main Backend Language
- Maven - Dependency Management
- MySQL - The default RDBMS.

Before starting the application, you will need to set up the database. By default, application attempts to connect to 
a database called design-office-ms in localhost:3306 or one called design-office-ms-test for tests. You can change 
the default location and name of the databases in application.properties.



<!-- Optional -->
<!-- ## License -->
<!-- This project is open source and available under the [... License](). -->

<!-- You don't have to include all sections - just the one's relevant to your project -->
