CREATE TABLE todo.task (
                           id BIGINT auto_increment NOT NULL,
                           title VARCHAR(255) NOT NULL,
                           created_at BIGINT NOT NULL,
                           updated_at BIGINT NULL,
                           PRIMARY KEY `pk-task-id` (id)
)
    ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4;