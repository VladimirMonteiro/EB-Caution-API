create table load_responsibility (
        end_date timestamp(6),
        start_date timestamp(6) not null,
        id uuid not null,
        load_id uuid not null,
        role varchar(255) check (role in ('ARMORER','SUB_ARMORER')),
        primary key (id)
);