 create table caution_item (
        quantity integer not null,
        delivery_date timestamp(6) not null,
        caution_id uuid not null,
        material_id uuid not null,
        status varchar(255) check (status in ('ACTIVE','RETURNED','LOST','DAMAGED')),
        primary key (caution_id, material_id)
 );