ALTER TABLE tasks
ADD COLUMN created TIMESTAMP WITHOUT TIME ZONE DEFAULT now();