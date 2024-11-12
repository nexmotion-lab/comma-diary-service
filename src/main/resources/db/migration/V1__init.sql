CREATE TABLE IF NOT EXISTS `account_event_tags` (
    `account_id` BIGINT NOT NULL,
    `event_tags` MEDIUMTEXT COLLATE utf8mb4_general_ci,
    PRIMARY KEY (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `emotion_tag` (
                               `xvalue` int NOT NULL,
                               `yvalue` int NOT NULL,
                               `emotion_tag_no` bigint NOT NULL AUTO_INCREMENT,
                               `name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
                               PRIMARY KEY (`emotion_tag_no`)
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `event_tag` (
                             `event_tag_no` bigint NOT NULL AUTO_INCREMENT,
                             `name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
                             PRIMARY KEY (`event_tag_no`),
                             UNIQUE KEY `unique_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=185 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE IF NOT EXISTS `diary` (
    `account_id` BIGINT DEFAULT NULL,
    `date_created` DATETIME(6) DEFAULT NULL,
    `diary_no` BIGINT NOT NULL AUTO_INCREMENT,
    `content` VARCHAR(1000) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `core_emotion_tag` BIGINT DEFAULT NULL,
    PRIMARY KEY (`diary_no`),
    KEY `FKjcoc2rvprxivs7fy97w0g7060` (`core_emotion_tag`),
    CONSTRAINT `FKjcoc2rvprxivs7fy97w0g7060` FOREIGN KEY (`core_emotion_tag`) REFERENCES `emotion_tag` (`emotion_tag_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `diary_per_emotion_tag` (
    `diary_no` bigint NOT NULL,
    `emotion_tag_no` bigint NOT NULL,
    `created` date DEFAULT NULL,
    PRIMARY KEY (`diary_no`,`emotion_tag_no`),
    KEY `FKh9qtqntaud2atkqvq4tworcfk` (`emotion_tag_no`),
    CONSTRAINT `FKgawwmql8yct6am4pk12be1nhb` FOREIGN KEY (`diary_no`) REFERENCES `diary` (`diary_no`) ON DELETE CASCADE,
    CONSTRAINT `FKh9qtqntaud2atkqvq4tworcfk` FOREIGN KEY (`emotion_tag_no`) REFERENCES `emotion_tag` (`emotion_tag_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `diary_per_event_tag` (
    `diary_no` bigint NOT NULL,
    `event_tag_no` bigint NOT NULL,
    `created` date DEFAULT NULL,
    PRIMARY KEY (`diary_no`,`event_tag_no`),
    KEY `FKh37uok5h4qwgef4k13ed4gcy1` (`event_tag_no`),
    CONSTRAINT `FKc4jmrl2nrlo4y3sa5sxb903vy` FOREIGN KEY (`diary_no`) REFERENCES `diary` (`diary_no`) ON DELETE CASCADE,
    CONSTRAINT `FKh37uok5h4qwgef4k13ed4gcy1` FOREIGN KEY (`event_tag_no`) REFERENCES `event_tag` (`event_tag_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE IF NOT EXISTS `event_tag_weights` (
    `event_tag_no` bigint NOT NULL,
    `word` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
    `weight` float DEFAULT NULL, PRIMARY KEY (`event_tag_no`,`word`),
    FULLTEXT KEY `word` (`word`) /*!50100 WITH PARSER `ngram` */ ,
    CONSTRAINT `event_tag_weights_ibfk_1` FOREIGN KEY (`event_tag_no`) REFERENCES `event_tag` (`event_tag_no`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
