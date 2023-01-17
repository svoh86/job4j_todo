create TABLE IF NOT EXISTS tasks_categories (
    id SERIAL PRIMARY KEY,
    task_id int NOT NULL REFERENCES tasks(id),
    categories_id int NOT NULL REFERENCES categories(id)
);

comment on table tasks_categories is 'Таблица связей категорий и задач';
comment on column tasks_categories.task_id is 'Внешний ключ на задачу';
comment on column tasks_categories.categories_id is 'Внешний ключ на категорию';