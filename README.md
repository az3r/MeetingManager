
# Meeting Mangager

Window application using [JavaFx](https://openjfx.io/) with [Hibernate ORM](https://hibernate.org/)


## Student's info

* Nguyễn Mạnh Tuấn

* 1712875

## Project's info

### Overview
* Database: MySql using MariaDB server

* Hibernate ORM for mapping domain model to relational database

* use FXML with [SceneBuilder](https://gluonhq.com/products/scene-builder/) support to create views

### Use case diagram
![]((https://github.com/Az3r/MeetingManager/blob/master/./document/use-case.png))

### [Requirement](https://github.com/Az3r/MeetingManager/blob/master/./document/requirement.pdf)

### [Figma prototype](https://www.figma.com/file/uHQH9yLd98ozFIYeMp0gET/Javafx?node-id=0%3A1)
> Because i didn't have enough time, some features in prototype maybe missing compared to actual application

## Installation

This application requires Java version 11, [Maven](https://maven.apache.org/download.cgi) and a version of MySql Server

If you haven't installed MySql Server yet then you can download [MariaDB Server](https://mariadb.com/downloads/)

1. Login to the server and create database named **MeetingManager**.

1. Clone the repository *git clone https://github.com/Az3r/MeetingManager.git*.

1. Open MeetingManager directory, then open *hibernate.cfg.xml* located in *./src/main/resources/hibernate.cfg.xml*:

    Find 2 properties called **connection.username** and **connection.password** and change them to your server's username and password.

    Depend on your version of MySql Server, you may need to edit the **dialect** property. Check [this list](https://docs.jboss.org/hibernate/orm/5.3/javadocs/org/hibernate/dialect/package-summary.html) to see which dialect you should use for your database server. I use MariaDB Server 10.5 so i choose *MariaDB103Dialect*.

    The **hbm2ddl.auto** tell hibernate what to do with the database when the ***Session Factory*** is created and closed. [Here are available options](https://docs.jboss.org/hibernate/orm/5.2/userguide/html_single/Hibernate_User_Guide.html#configurations-hbmddl). Some of them which i use are:
    * *create*: Database dropping will be generated followed by database creation.
    * *update*: Update the database schema.

1. Run *mvn clean install*.

1. Run *mvn javafx:run*.
    
## References
* [Hibernate: save, persist, update, merge, saveOrUpdate](https://www.baeldung.com/hibernate-save-persist-update-merge-saveorupdate)

* [Hibernate 5 Bootstrapping API](https://www.baeldung.com/hibernate-5-bootstrapping-api)

* [Hashing a Password in Java](https://www.baeldung.com/java-password-hashing)

* [Hibernate Inheritance Mapping](https://www.baeldung.com/hibernate-inheritance)

* Various Indian tutorials