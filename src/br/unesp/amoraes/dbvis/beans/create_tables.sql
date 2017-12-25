CREATE TABLE databaseConnection
(
id integer not null 
    PRIMARY KEY GENERATED ALWAYS AS IDENTITY 
    (START WITH 1, INCREMENT BY 1),
name varchar(50) not null,
driver varchar(100) not null,
url varchar(100) not null,
username varchar(20) not null,
password varchar (50) not null
);

CREATE TABLE display
(
id integer not null
    PRIMARY KEY GENERATED ALWAYS AS IDENTITY 
    (START WITH 1, INCREMENT BY 1),
name varchar(50) not null,
width integer not null,
height integer not null,
active boolean
);

CREATE TABLE parameters
(
id integer not null
    PRIMARY KEY GENERATED ALWAYS AS IDENTITY 
    (START WITH 1, INCREMENT BY 1),
api_password varchar(32) not null
);