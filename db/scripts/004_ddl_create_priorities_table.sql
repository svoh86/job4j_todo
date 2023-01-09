create TABLE IF NOT EXISTS priorities (
    id SERIAL PRIMARY KEY,
    name TEXT UNIQUE NOT NULL,
    position int NOT NULL
);

comment on table priorities is 'Приоритеты';
comment on column priorities.id is 'Идентификатор приоритета';
comment on column priorities.name is 'Имя приоритета';
comment on column priorities.position is 'Уровень приоритета';