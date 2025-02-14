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

CREATE TABLE `building` (
                            `id` BIGINT NOT NULL COMMENT 'Building ID',
                            `temperature` DECIMAL (5, 2) DEFAULT NULL COMMENT 'Temperature of the building',
                            `number_of_apartment` SMALLINT DEFAULT 0 COMMENT 'Number of Apartment',
                            `number_of_common_room` SMALLINT DEFAULT 0 COMMENT 'Number of Common Room',
                            `create_time` DATETIME NOT NULL COMMENT 'Creation Datetime',
                            `modified_time` DATETIME DEFAULT NULL COMMENT 'Modified Datetime',
                            `is_del` CHAR(1) DEFAULT '0' COMMENT 'Deletion status',
                            PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = 'Building Table';

CREATE TABLE `common_room` (
                            `id` BIGINT NOT NULL COMMENT 'Common Room ID',
                            `building_id` BIGINT NOT NULL COMMENT 'Building ID',
                            `type` INT (3) NOT NULL COMMENT 'Type of Room: 1-Gym, 2-Library, 3-Laundry',
                            `temperature` DECIMAL (5, 2) DEFAULT NULL COMMENT 'Temperature of the common room',
                            `is_heating` CHAR(1) DEFAULT '0' COMMENT 'Status of heating: 0-disable, 1-enable',
                            `is_cooling` CHAR(1) DEFAULT '0' COMMENT 'Status of cooling: 0-disable, 1-enable',
                            `create_time` DATETIME NOT NULL COMMENT 'Creation Datetime',
                            `modified_time` DATETIME DEFAULT NULL COMMENT 'Modified Datetime',
                            `is_del` CHAR(1) DEFAULT '0' COMMENT 'Deletion status',
                            PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = 'Common Room Table';

CREATE TABLE `apartment` (
                            `id` BIGINT NOT NULL COMMENT 'Apartment ID',
                            `building_id` BIGINT NOT NULL COMMENT 'Building ID',
                            `room_number` SMALLINT NOT NULL COMMENT 'Number of room',
                            `temperature` DECIMAL (5, 2) DEFAULT NULL COMMENT 'Temperature of the apartment',
                            `is_heating` CHAR(1) DEFAULT '0' COMMENT 'Status of heating: 0-disable, 1-enable',
                            `is_cooling` CHAR(1) DEFAULT '0' COMMENT 'Status of cooling: 0-disable, 1-enable',
                            `create_time` DATETIME NOT NULL COMMENT 'Creation Datetime',
                            `modified_time` DATETIME DEFAULT NULL COMMENT 'Modified Datetime',
                            `is_del` CHAR(1) DEFAULT '0' COMMENT 'Deletion status',
                            PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = 'Apartment Table';

CREATE TABLE `apartment_owner` (
                            `id` BIGINT NOT NULL COMMENT 'Apartment Owner ID',
                            `apartment_id` BIGINT NOT NULL COMMENT 'Apartment ID',
                            `first_name` VARCHAR(100) NOT NULL COMMENT 'First name of the owner',
                            `last_name` VARCHAR(100) NOT NULL COMMENT 'Last name of the owner',
                            `create_time` DATETIME NOT NULL COMMENT 'Creation Datetime',
                            `modified_time` DATETIME DEFAULT NULL COMMENT 'Modified Datetime',
                            `is_del` CHAR(1) DEFAULT '0' COMMENT 'Deletion status',
                            PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = 'Apartment Owner Table';

CREATE TABLE `room` (
                            `id` BIGINT NOT NULL COMMENT 'Room ID',
                            `apartment_id` BIGINT NOT NULL COMMENT 'Apartment ID',
                            `temperature` DECIMAL (5, 2) DEFAULT NULL COMMENT 'Temperature of the building',
                            `is_heating` CHAR(1) DEFAULT '0' COMMENT 'Status of heating: 0-disable, 1-enable',
                            `is_cooling` CHAR(1) DEFAULT '0' COMMENT 'Status of cooling: 0-disable, 1-enable',
                            `create_time` DATETIME NOT NULL COMMENT 'Creation Datetime',
                            `modified_time` DATETIME DEFAULT NULL COMMENT 'Modified Datetime',
                            `is_del` CHAR(1) DEFAULT '0' COMMENT 'Deletion status',
                            PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = 'Room Table';

-- Change `temperature` to current_temperature
ALTER TABLE building CHANGE COLUMN temperature current_temperature DECIMAL(5, 2) DEFAULT NULL COMMENT 'Current temperature of the building';
ALTER TABLE common_room CHANGE COLUMN temperature current_temperature DECIMAL(5, 2) DEFAULT NULL COMMENT 'Current temperature of the common room';
ALTER TABLE apartment CHANGE COLUMN temperature current_temperature DECIMAL(5, 2) DEFAULT NULL COMMENT 'Current temperature of the apartment';
ALTER TABLE room CHANGE COLUMN temperature current_temperature DECIMAL(5, 2) DEFAULT NULL COMMENT 'Current temperature of the room';

-- Add `target_temperature` column
ALTER TABLE building ADD COLUMN target_temperature DECIMAL(5, 2) DEFAULT NULL COMMENT 'Target temperature of the building' AFTER current_temperature;
ALTER TABLE common_room ADD COLUMN target_temperature DECIMAL(5, 2) DEFAULT NULL COMMENT 'Target temperature of the common room' AFTER current_temperature;
ALTER TABLE apartment ADD COLUMN target_temperature DECIMAL(5, 2) DEFAULT NULL COMMENT 'Target temperature of the apartment' AFTER current_temperature;
ALTER TABLE room ADD COLUMN target_temperature DECIMAL(5, 2) DEFAULT NULL COMMENT 'Target temperature of the room' AFTER current_temperature;

-- Modify `room_number` to VARCHAR(255) and add `number_of_room` column
ALTER TABLE apartment
    MODIFY COLUMN room_number VARCHAR(255) DEFAULT NULL COMMENT 'The number label of a room',
    ADD COLUMN number_of_room SMALLINT DEFAULT NULL COMMENT 'Total amount of rooms in an apartment' AFTER room_number;
