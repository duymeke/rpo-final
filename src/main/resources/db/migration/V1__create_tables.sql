create table users (
                       id bigserial primary key,
                       username varchar(255),
                       email varchar(255),
                       password varchar(255)
);

create table tasks (
                       id bigserial primary key,
                       title varchar(255),
                       text varchar(255),
                       is_completed boolean,
                       user_id bigint,
                       foreign key (user_id) references users(id) on delete cascade
);

create table categories (
                            id bigserial primary key,
                            name varchar(255)
);

create table task_categories (
                                 task_id bigint,
                                 category_id bigint,
                                 foreign key (task_id) references tasks(id) on delete cascade,
                                 foreign key (category_id) references categories(id) on delete cascade
);
