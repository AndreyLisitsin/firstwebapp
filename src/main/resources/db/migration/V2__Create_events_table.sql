create table if not exists events(
    id bigint not null auto_increment primary key,
    event_name varchar(50) not null,
    user_id bigint not null,
    foreign key (user_id) references users(id)
);