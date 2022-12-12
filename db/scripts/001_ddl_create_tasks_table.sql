create TABLE IF NOT EXISTS tasks (
  id SERIAL PRIMARY KEY,
   description TEXT NOT NULL,
   created TIMESTAMP,
   done BOOLEAN
);

comment on table tasks is 'Задачи';
comment on column tasks.id is 'Идентификатор задачи';
comment on column tasks.description is 'Описание задачи';
comment on column tasks.created is 'Дата создания задачи';
comment on column tasks.done is 'Статус выполнения задачи';