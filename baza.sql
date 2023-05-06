DROP database IF EXISTS firma;
CREATE DATABASE firma;
USE firma;

CREATE TABLE positions(
    id_position int(11) primary key not null auto_increment,
    position_name varchar(128) not null,
    description text not null
);

CREATE TABLE users(
    id_user int(11) primary key not null auto_increment,
    name varchar(50) not null,
    surname varchar(128) not null,
    address varchar(255) not null,
    zip varchar(6) not null,
    place varchar(128) not null,
    phone_num int(9) not null UNIQUE,
    position_id int(11) REFERENCES positions.id_position,
    login int(11) REFERENCES login.user_id,
    groups int(11) not null
);

CREATE TABLE login(
    id_login int(11) primary key not null auto_increment,
    user_id int(11) not null UNIQUE,
    token varchar(250) not null,
    email varchar(128) not null UNIQUE,
    password varchar(255) not null
);

CREATE TABLE tasks_history(
    id_task_history int(11) primary key not null auto_increment UNIQUE,
    start_date DATE not null,
    end_date DATE not null,
    tasks_id int(11) UNIQUE REFERENCES tasks.id_task,
    planned_end DATE not null
);

CREATE TABLE statuses(
    id_status int(1) primary key not null auto_increment,
    name varchar(128) not null
);

CREATE TABLE tasks(
    id_task int(11) primary key not null auto_increment,
    title VARCHAR(50) not null,
    description MEDIUMTEXT not null,
    status_id int(1) REFERENCES statuses.id_status,
    user_id int(11) REFERENCES user.id_user
);

ALTER TABLE tasks add CONSTRAINT user_id FOREIGN KEY (user_id) REFERENCES users(id_user);
ALTER TABLE tasks_history add CONSTRAINT tasks_id FOREIGN KEY (tasks_id) REFERENCES tasks(id_task);
ALTER TABLE users add CONSTRAINT position_id FOREIGN KEY (position_id) REFERENCES positions(id_position);
ALTER TABLE users add CONSTRAINT login FOREIGN KEY (login) REFERENCES login(user_id);
ALTER TABLE tasks add CONSTRAINT status_id FOREIGN KEY (status_id) REFERENCES statuses(id_status);