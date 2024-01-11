-- Remove previous database
drop schema if exists big_event;

-- Creating a new database
create database big_event;

-- Using the database
use big_event;



-- --------------------------------------------------------------------------------------------------------------------------------------------------- --
-- --------------------------------------------------------------------------------------------------------------------------------------------------- --
-- User Table
create table user (
                      id int unsigned primary key auto_increment comment 'ID',
                      username varchar(30) not null unique comment 'Username',
                      password varchar(32)  comment 'Password',
                      nickname varchar(30)  default '' comment 'Nickname',
                      email varchar(256) default '' comment 'Email',
                      user_pic varchar(256) default '' comment 'Profile Picture',
                      create_time datetime not null comment 'Creation Time',
                      update_time datetime not null comment 'Modification Time'
) comment 'User Table';


-- --------------------------------------------------------------------------------------------------------------------------------------------------- --
-- --------------------------------------------------------------------------------------------------------------------------------------------------- --
-- Category Table
create table category(
                         id int unsigned primary key auto_increment comment 'ID',
                         category_name varchar(32) not null comment 'Category Name',
                         category_alias varchar(32) not null comment 'Category Alias',
                         create_user int unsigned not null comment 'Creator ID',
                         create_time datetime not null comment 'Creation Time',
                         update_time datetime not null comment 'Modification Time',
                         constraint fk_category_user foreign key (create_user) references user(id) -- Foreign Key Constraint
) comment 'Category Table';

-- Article Table
create table article(
                        id int unsigned primary key auto_increment comment 'ID',
                        title varchar(100) not null comment 'Article Title',
                        content varchar(10000) not null comment 'Article Content',
                        cover_img varchar(256) not null  comment 'Article Cover Image',
                        state varchar(9) default 'Draft' comment 'Article Status: Can only be [Published] or [Draft]',
                        category_id int unsigned comment 'Article Category ID',
                        create_user int unsigned not null comment 'Creator ID',
                        create_time datetime not null comment 'Creation Time',
                        update_time datetime not null comment 'Modification Time',
                        constraint fk_article_category foreign key (category_id) references category(id), -- Foreign Key Constraint
                        constraint fk_article_user foreign key (create_user) references user(id) -- Foreign Key Constraint
) comment 'Article Table';


