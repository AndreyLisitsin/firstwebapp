create table if not exists users(
    id bigint primary key auto_increment,
    first_name varchar(50) not null,
    last_name varchar(50) not null
);