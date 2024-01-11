# Big Event
> Big Event is a web-based platform focusing on social interactions, article sharing, and local service discovery. This project utilizes front-end technologies like HTML/CSS/JavaScript, and frameworks such as Vue.js and Element-Plus. The backend is built with Java's Spring Boot framework, and MySQL is used for database management.

## Table of Contents
- [Introduction](#introduction)
    - [Key Features](#key-features)
    - [Technology Stack](#technology-stack)
    - [Future Goals](#future-goals)
- [Installation and Running](#installation-and-running)
  - [Setup](#setup)
    - [Local MySQL Database](#local-mysql-database)
    - [Redis Server](#redis-server)
  - [Run](#run)
    - [Back-end Service](#back-end-service)
    - [Front-end Service](#front-end-service)
- [How to Use the Software](#how-to-use-the-software)
  - [Registration and Login](#registration-and-login)
  - [Main Interface](#main-interface)
    - [Article Category Management](#article-category-management)
    - [Article Management](#article-management)
    - [User Information Management](#user-information-management)
- [Contact](#contact)

---

## Introduction
### Key Features
- **User Authentication**: Supports user login functionality.
- **Article Management**: Allows users to add, edit, delete articles, and manage article categories.
- **Personal Information Configuration**: Users can configure personal information such as avatar, nickname, and password.

### Technology Stack
- **Front-end**: HTML/CSS/JavaScript, Vue.js, Element-Plus
- **Back-end**: Java, Spring Boot, Redis, Aliyun OSS
- **Database**: MySQL

### Future Goals
1. **Location and Contact**: Enable users to locate each other and make contact through information in their profiles.
2. **Local Service Search**: Users can search for needed services on a map and quickly locate nearby related individuals or businesses, like cement factory owners.
3. **Extended Social Features**: Add the ability to add friends and pay special attention to friends' articles. Users can add friends and follow their articles.
4. **Content Browsing**: Supports browsing articles by region, country, or globally, and filtering articles by category.

---

## Installation and Running
> Here provides the step-by-step setup for prerequisites of running this project on your system.
### Setup
#### *local MySQL Database*
Before you begin, ensure you have met the following requirements:
- MySQL installed on your machine with:
  - Port: `3306`
  - Username: `root`
  - Password: `123456`
1. Start your MySQL service and log in to the MySQL server using the credentials provided above.
2. Navigate to the `Big Event\MySQL setup` directory.
3. Run the `big_event.sql` script to set up the required database and tables for the Big Event project.
   ```bash
   mysql -u root -p123456 < mysql -u root -p123456 < big_event.sql
   ```
#### *Redis Server*
- Redis Server installed and configured. You can find installation guides for `Linux`, `macOS`, and `Windows` on the [Redis official documentation](https://redis.io/docs/install/install-redis/). The Redis Server should be running on port `6379`.
- Start your Redis server on the default port 6379.
    - On `Linux` or `macOS`:
    ```bash
    redis-server
    ```
    - On `Windows`, use the Redis application you installed: `redis-server.exe` or redis server on WSL Subsystem on `Windows`.
### Run
#### *Back-end Service*
1. Navigate to the `"Big Event\Back-end\big-event\target"` directory using command line.
2. Run the `big-event-1.0.jar` file using `Java`:
    ```bash
    java -jar big-event-1.0.jar
    ```
    Make sure that the Java Runtime Environment (JRE) or Java Development Kit (JDK) is **version 8 or higher**.
3. Wait until the screen shows that the Spring Boot project has been successfully initiated, which should look like this:
    ```bash
     Started BigEventApplication in xxx seconds (process running for xxx)
    ```
#### *Front-end Service*
1. Re-navigate to the `"Big Event\Front-end\big-event"` directory in your command line.
2. If a `dist` folder does not exist in the current directory, you shall need to build the project:
    - Run the build command in the current folder, which is usually one of the following:
        ```bash
        npm run build
        ```
3. Once the `dist` folder is in place (either it was already there or you have built the project), you can start the front-end server for this project by:
    ```bash
    npm start
    ```
    This command will start the server that serves the front-end files of big-event project.
    \
    If the project uses a tool like serve to statically serve the files, you might have a script in your package.json file that looks something like:
    ```bash
    "scripts": {
        "start": "serve -s dist"
    }
    ```
    In this case, running `npm start` will serve the contents of the `dist` folder.

## How to Use the Software
> Here shows how to use this software.
### Registration and Login

- **Registration**: All the users will be navigated to the login screen upon launching the application. To register, enter a `username` (3-30 chars, no spaces) and a `password` (5-32 chars, no spaces), and then submit your registration by clicking the "Sign-Up" button.
- **Login**: After successful registration, log in with your new `username` and `password` to access the main interface.

### Main Interface

The main interface consists of 3 primary management sections:

#### Article Category Management
   - Use the "Add Category" button in the top right corner to create a new article category by specifying a `category name` (3-32 chars) and an `category alias` (1-32 chars).
   - Besides adding new categories, you can browse, "Edit" (blue button on the right), and "Delete"  (red button on the right) categories to manage them efficiently on this page.

#### Article Management
   - After creating at least one category, click "Add Article" in the top right corner to create new articles. Specify the `title` (3-100 chars), `category`, `cover image` (upload from local files), and `content` (0-10000 chars, including HTML tags like `<p></p>` generated by the rich text editor).
   - Decide whether to publish the article immediately by clicking "Publish" button or save it as a draft with "Save Draft" button. 
   - You can manage your articles by filtering based on `category` and `state` (*Published* or *Draft*) and clicking "Search" or "Reset" button to proceed your filtering.
   - Also, you can perform actions like "Edit" or "Delete" as in the category management interface.

#### User Information Management
   - Modify your personal information by clicking on your avatar in the top right corner of the page or selecting "Personal Center" from the left sidebar.
   - You can update your `avatar` (profile image), change your `password`, and add a `nickname` and `email` as you want.

---

## Contact
**Name**: Zixiang Xu  
**Email**: [1250105753@qq.com](mailto:1250105753@qq.com)  
**LinkedIn**: [Zixiang Xu](https://www.linkedin.com/in/zixiang-xu-9aa309232/)  
**Github**: [SuperDim666](https://github.com/SuperDim666)
