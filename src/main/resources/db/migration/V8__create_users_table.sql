 create table users (
        active boolean not null,
        created_at timestamp(6) not null,
        id uuid not null,
        email varchar(255) not null unique,
        grad varchar(255) not null,
        password varchar(255) not null,
        role varchar(255) not null check (role in ('ARMORER','SUB_ARMORER')),
        war_name varchar(255) not null,
        primary key (id)
);