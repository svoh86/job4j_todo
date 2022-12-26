ALTER TABLE tasks
ADD todo_user_id int REFERENCES todo_user(id)