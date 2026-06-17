alter table load_item
    add column available_quantity integer;

update load_item
set available_quantity = expected_quantity
where available_quantity is null;

alter table load_item
    alter column available_quantity set not null;
