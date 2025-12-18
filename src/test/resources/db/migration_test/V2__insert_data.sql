insert into users (username, email, password) values
                                                  ('user1', 'user1@mail.com', 'password1'),
                                                  ('user2', 'user2@mail.com', 'password2');

insert into categories (name) values
                                  ('work'),
                                  ('study'),
                                  ('home');

insert into tasks (title, text, is_completed, user_id) values
                                                           ('task 1', 'text 1', false, 1),
                                                           ('task 2', 'text 2', true, 1),
                                                           ('task 3', 'text 3', false, 2);

insert into task_categories (task_id, category_id) values
                                                       (1, 1),
                                                       (1, 2),
                                                       (2, 1),
                                                       (3, 3);
