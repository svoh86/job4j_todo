create TABLE IF NOT EXISTS categories (
    id SERIAL PRIMARY KEY,
    name TEXT UNIQUE NOT NULL
);

comment on table categories is 'Категории задач';
comment on column categories.id is 'Идентификатор категории';
comment on column categories.name is 'Имя категории';