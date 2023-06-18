INSERT INTO user (id, username, password) VALUES (NULL, 'maggie', '$2a$12$fzuUlf5Di.eFG01VsL0WI.j4vLDjL.DPpK952/U8HquzTEooUJ5n.');
INSERT INTO user (id, username, password) VALUES (NULL, 'Selam', '$2a$12$Fao0oL26ID6zUlVn2At0yeXYfNKdmGrD/MAx1uaKpwb34oKlxjupS');
-- Insert more user records as needed


-- Roles
--INSERT INTO role (id, name) VALUES (NULL, 'ADMIN');
--INSERT INTO role (id, name) VALUES (NULL, 'USER');
-- Insert more role records as needed

-- User Roles
INSERT INTO user_roles (user_id, roles) VALUES (1, "ADMIN"); -- John Doe has ADMIN role
INSERT INTO user_roles (user_id, roles) VALUES (2, USER); -- Jane Smith has USER role
-- Associate more roles with users as needed

-- Tasks
INSERT INTO task (id, title, description, due_date, priority, category, user_id) VALUES (NULL, 'Task 1', 'Description for Task 1', '2023-06-30', 'HIGH', 'WORK', 1);
INSERT INTO task (id, title, description, due_date, priority, category, user_id) VALUES (NULL, 'Task 2', 'Description for Task 2', '2023-07-15', 'MEDIUM', 'PERSONAL', 2);
-- Insert more task records as needed

-- Reminders
--INSERT INTO reminder (id, message, reminder_date,task_id) VALUES (NULL, 'Reminder for Task 1', '2023-06-25 10:00',1);
--INSERT INTO reminder (id, message, reminder_date,task_id) VALUES (NULL, 'Reminder for Task 2', '2023-07-10 10:00',2);
-- Insert more reminder records as needed