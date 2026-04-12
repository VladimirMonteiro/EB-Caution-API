create table caution (
        caution_date timestamp(6) not null,
        id uuid not null,
        military_id uuid not null,
        user_id uuid not null,
        observations varchar(255),
        status varchar(255) check (status in ('ACTIVE','DELIVERED','PARTIALLY_DELIVERED','CANCELED')),
        primary key (id)
);