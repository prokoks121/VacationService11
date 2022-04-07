create sequence hibernate_sequence start 1 increment 1;

    create table employee (
       id int8 not null,
        email varchar(255),
        password varchar(255),
        primary key (id)
    );

    create table used_vacation (
       id int8 not null,
        employee_email varchar(255),
        vacation_end date,
        vacation_start date,
        primary key (id)
    );

    create table vacation (
       id int8 not null,
        employee_email varchar(255),
        total_days int4 not null,
        year varchar(255),
        primary key (id)
    );
create sequence hibernate_sequence start 1 increment 1;

    create table employee (
       id int8 not null,
        email varchar(255),
        password varchar(255),
        primary key (id)
    );

    create table used_vacation (
       id int8 not null,
        employee_email varchar(255),
        vacation_end date,
        vacation_start date,
        primary key (id)
    );

    create table vacation (
       id int8 not null,
        employee_email varchar(255),
        total_days int4 not null,
        year varchar(255),
        primary key (id)
    );
create sequence hibernate_sequence start 1 increment 1;

    create table employee (
       id int8 not null,
        email varchar(255),
        password varchar(255),
        primary key (id)
    );

    create table used_vacation (
       id int8 not null,
        employee_email varchar(255),
        vacation_end date,
        vacation_start date,
        primary key (id)
    );

    create table vacation (
       id int8 not null,
        employee_email varchar(255),
        total_days int4 not null,
        year varchar(255),
        primary key (id)
    );
