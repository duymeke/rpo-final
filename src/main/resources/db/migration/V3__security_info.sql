create table permissions (
                             id bigserial primary key,
                             name varchar not null
);

create table users_permissions (
                                   user_model_id bigint not null references users(id) on delete cascade,
                                   permissions_id bigint not null references permissions(id) on delete cascade,
                                   primary key (user_model_id, permissions_id)
);

insert into permissions (name) values ('ROLE_USER'),
                                      ('ROLE_ADMIN');