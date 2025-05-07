create table users
(
    id         bigint auto_increment
        primary key,
    username   varchar(255) not null,
    password   varchar(255) not null,
    nickname   varchar(255) null,
    avatar     varchar(255) null,
    created_at datetime(6)  null,
    updated_at datetime(6)  null,
    constraint UK_username
        unique (username)
);

create table schedules
(
    id         bigint auto_increment
        primary key,
    title      varchar(255) not null,
    content    text         null,
    start_time datetime     null,
    end_time   datetime     null,
    priority   varchar(50)  null,
    tags       varchar(255) null,
    group_id   bigint       null,
    user_id    bigint       not null,
    created_at datetime     null,
    updated_at datetime     null,
    constraint schedules_ibfk_1
        foreign key (user_id) references users (id)
);

create index user_id
    on schedules (user_id);

