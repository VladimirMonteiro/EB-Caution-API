create table load (
        active boolean not null,
        created_at timestamp(6) not null,
        id uuid not null,
        cia varchar(255) not null,
        pel_name varchar(255) not null,
        primary key (id)
);