create table load_item (
        expected_quantity integer not null,
        load_id uuid not null,
        material_id uuid not null,
        description varchar(255),
        primary key (load_id, material_id)
);