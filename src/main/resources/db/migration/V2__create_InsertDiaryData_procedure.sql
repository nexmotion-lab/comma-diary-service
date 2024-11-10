
DELIMITER //

CREATE PROCEDURE `InsertDiaryData`()
BEGIN
    DECLARE i INT DEFAULT 1;
    DECLARE diaryDate DATE DEFAULT '2024-07-16';
    DECLARE selectedEmotionTag INT;

    -- Loop to insert 100 diary entries
    WHILE i <= 100 DO
        SET selectedEmotionTag = (SELECT emotion_tag_no FROM emotion_tag ORDER BY RAND() LIMIT 1);

INSERT INTO diary (account_id, date_created, diary_no, content, core_emotion_tag)
VALUES (
           4,
           CONCAT(diaryDate, ' 10:00:00'),
           i + 40,
           CONCAT('  ', i + 40),
           selectedEmotionTag
       );

INSERT INTO diary_per_emotion_tag (diary_no, emotion_tag_no)
VALUES (
               i + 40,
               selectedEmotionTag
       );

INSERT INTO diary_per_emotion_tag (diary_no, emotion_tag_no)
VALUES (
               i + 40,
               (SELECT emotion_tag_no FROM emotion_tag ORDER BY RAND() LIMIT 1)
    );

INSERT INTO diary_per_event_tag (diary_no, event_tag_no)
VALUES (
               i + 40,
               (SELECT event_tag_no FROM event_tag ORDER BY RAND() LIMIT 1)
    );

SET diaryDate = DATE_ADD(diaryDate, INTERVAL 1 DAY);
        SET i = i + 1;
END WHILE;
END //

DELIMITER ;
