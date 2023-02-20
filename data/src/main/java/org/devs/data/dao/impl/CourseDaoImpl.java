package org.devs.data.dao.impl;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.devs.data.dao.CourseDao;
import org.devs.data.dao.impl.query.CourseQuery;
import org.devs.data.dao.impl.rowmapper.CourseRowMapper;
import org.devs.data.entity.Course;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CourseDaoImpl implements CourseDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final CourseRowMapper courseRowMapper;

    @Override
    public Optional<Course> findById(Long id) {


        return namedParameterJdbcTemplate.query(CourseQuery.SELECT_ONE,
                new MapSqlParameterSource("id", id), courseRowMapper).stream().findFirst();
    }

    @Override
    public Course save(Course course) {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource source = new MapSqlParameterSource()
                .addValue("id", 5)
                .addValue("name", course.getName())
                .addValue("subject", course.getSubject())
                .addValue("course_duration_in_month", course.getCourseDurationInMonth())
                .addValue("lesson_duration", course.getLessonDuration())
                .addValue("course_price", course.getCoursePrice());
        namedParameterJdbcTemplate.update(CourseQuery.SAVE_COURSE, source, keyHolder, new String[]{"id"});
        course.setId(keyHolder.getKeyAs(Long.class));
        
        return course;
    }

    @Override
    public Optional<Course> findByGroupId(Long id) {
        return namedParameterJdbcTemplate.query(CourseQuery.SELECT_ONE_BY_GROUP,
                new MapSqlParameterSource("id", id), courseRowMapper).stream().findFirst();
    }
}