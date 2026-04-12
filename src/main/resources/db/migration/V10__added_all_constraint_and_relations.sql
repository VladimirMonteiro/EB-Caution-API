alter table if exists caution
       add constraint FK445t0ulajwauc1s0yl2xgtd7r
       foreign key (military_id)
       references military;

alter table if exists caution
       add constraint FK46onvxh01r95d2xm9t7tdyvl2
       foreign key (user_id)
       references users;

alter table if exists caution_item
       add constraint caution_fk
       foreign key (caution_id)
       references caution;

alter table if exists caution_item
       add constraint material_fk
       foreign key (material_id)
       references material;

alter table if exists load
       add constraint FK9xp1fc8e2idrqrr35y7i0koin
       foreign key (user_id)
       references users;

alter table if exists load_item
       add constraint load_fk
       foreign key (load_id)
       references load;

alter table if exists load_item
       add constraint material_fk
       foreign key (material_id)
       references material;

alter table if exists load_responsibility
       add constraint FK970qt40oiikepc2gvfwjvfcg1
       foreign key (load_id)
       references load;

alter table if exists users_militaries
       add constraint FK75sovuc719rcmyruks44xqsu0
       foreign key (military_id)
       references military;

alter table if exists users_militaries
       add constraint FK2h6d4bt8s59agbbrscrdifrdf
       foreign key (user_id)
       references users;