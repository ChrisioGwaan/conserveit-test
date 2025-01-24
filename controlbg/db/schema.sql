CREATE TABLE `sys_user` (
                            `id` VARCHAR ( 255 ) NOT NULL COMMENT 'User UUID',
                            `username` VARCHAR ( 64 ) NOT NULL COMMENT 'Username',
                            `firstname` VARCHAR ( 64 ) DEFAULT NULL COMMENT 'User first name',
                            `lastname` VARCHAR ( 64 ) DEFAULT NULL COMMENT 'User last name',
                            `password` VARCHAR ( 512 ) NOT NULL COMMENT 'User password',
                            `email` VARCHAR ( 64 ) DEFAULT NULL COMMENT 'User email',
                            `is_del` CHAR ( 1 ) DEFAULT '0' COMMENT 'Deletion status',
                            PRIMARY KEY ( `id` )
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = 'System User Info';

ALTER TABLE sys_user ADD COLUMN create_time DATETIME NOT NULL COMMENT 'Creation Datetime' AFTER email;
ALTER TABLE sys_user ADD COLUMN modified_time DATETIME DEFAULT NULL COMMENT 'Modified Datetime' AFTER create_time;
ALTER TABLE sys_user ADD COLUMN role VARCHAR(64) DEFAULT NULL COMMENT 'Account Role' AFTER email;

ALTER TABLE sys_user ADD create_user VARCHAR ( 64 ) NOT NULL COMMENT 'Create user' AFTER role,
ADD modified_user VARCHAR ( 64 ) DEFAULT NULL COMMENT 'Modified user' AFTER create_user;