create table if not exists files(
    file_id bigint not null primary key auto_increment,
    file_name varchar(50) not null,
    file_url varchar(50) not null,
    file_path varchar(100) not null,
    event_id bigint not null,
    foreign key (event_id) references events(id)
);