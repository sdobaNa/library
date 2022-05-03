# Test project

#### Local start: `mvn spring-boot:run -P dev`
#### Deployment to wildfly `clean package install -P prod`  

Docker must be installed for the tests to work.

SQL scripts:  
`CREATE DATABASE library
WITH
OWNER = postgres
ENCODING = 'UTF8'
LC_COLLATE = 'Russian_Russia.1251'
LC_CTYPE = 'Russian_Russia.1251'
TABLESPACE = pg_default
CONNECTION LIMIT = -1;`

`CREATE EXTENSION IF NOT EXISTS "uuid-ossp";`

`create table categories (
uid uuid default uuid_generate_v4 (),
name varchar (100) not null,
primary key (uid)
);`

`create table books (
uid uuid default uuid_generate_v4 (),
name varchar (100) not null,
author varchar (100) not null,
issue_date date not null,
category_uid uuid not null,
primary key (uid),
CONSTRAINT fk_categories FOREIGN KEY(category_uid)
REFERENCES categories(uid)
);`


