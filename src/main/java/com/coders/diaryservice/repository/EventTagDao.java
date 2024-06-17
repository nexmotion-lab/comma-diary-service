package com.coders.diaryservice.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class EventTagDao {

    private final JdbcTemplate jdbcTemplate;

    public void batchInsertEventTags(List<Long> eventTagIds, Long diaryNo) {
        String sql = "INSERT INTO diary_per_event_tag (diary_no, event_tag_no) VALUES (?, ?)";
        try {
            jdbcTemplate.batchUpdate(
                    sql,
                    new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                            ps.setLong(1, diaryNo);
                            ps.setLong(2, eventTagIds.get(i));
                        }

                        @Override
                        public int getBatchSize() {
                            return eventTagIds.size();
                        }
                    }
            );
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException("적절하지 않은 감정 데이터가 있습니다. 다시 시도해주세요.", ex);
        }
    }
}
