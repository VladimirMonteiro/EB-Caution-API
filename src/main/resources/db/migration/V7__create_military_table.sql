create table military (
        created_at timestamp(6) not null,
        id uuid not null,
        cia varchar(255) not null,
        cpf varchar(255) not null unique,
        email varchar(255) not null unique,
        grad varchar(255) not null,
        pel varchar(255) not null,
        phone varchar(255) not null,
        status varchar(255) check (status in ('ACTIVE','RESERVE')),
        war_name varchar(255) not null,
        primary key (id)
);