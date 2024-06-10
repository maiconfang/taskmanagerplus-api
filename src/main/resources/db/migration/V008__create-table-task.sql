
CREATE TABLE task (
  id int NOT NULL AUTO_INCREMENT COMMENT ' An auto-incremented primary key to uniquely identify each task.',
  title varchar(255) NOT NULL COMMENT 'The title of the task, which is a string up to 255 characters long and cannot be null.',
  description text COMMENT 'The description of the task, which is longer text.',
  due_date datetime DEFAULT NULL COMMENT 'The due date of the task, stored in date format (YYYY-MM-DD).',
  completed tinyint(1) NOT NULL DEFAULT '0' COMMENT 'A boolean field indicating whether the task has been completed or not. The default value is FALSE.',
  created_at datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'The timestamp of the date and time when the task was created. It is automatically set to the current moment when a new task is inserted.',
  updated_at datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'The timestamp of the most recent date and time when the task was updated. It is automatically updated to the current moment whenever the task is modified.',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci