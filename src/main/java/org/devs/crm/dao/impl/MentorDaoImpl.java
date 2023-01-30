package org.devs.crm.dao.impl;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.devs.crm.dao.MentorDao;
import org.devs.crm.model.Mentor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@RequiredArgsConstructor
public class MentorDaoImpl implements MentorDao {
    @Autowired
    public DataSource dataSource;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Override
    public Mentor findById(Long id) {
        String query = "SELECT * FROM tb_mentors WHERE id = :id";

        return namedParameterJdbcTemplate.query(query, new MapSqlParameterSource("id", id), (rs, rowNum) -> {
            System.out.println(rowNum);
            Mentor mentor = new Mentor();
            mentor.setId(rs.getLong("id"));
            mentor.setFirstName(rs.getString("first_name"));
            mentor.setLastName(rs.getString("last_name"));
            mentor.setPatronymic(rs.getString("patronymic"));
            mentor.setEmail(rs.getString("email"));
            mentor.setPhoneNumber(rs.getString("phone_number"));
            mentor.setSalary(rs.getBigDecimal("salary"));
            return mentor;
        }).get(0);
    }

    @Override
    public Mentor save(Mentor mentor) {
        String query = "" +
                "INSERT INTO tb_mentors(first_name, last_name, patronymic, email, phone_number, salary) " +
                "VALUES(:fname, :lname, :patronymic, :email, :phoneNumber, :salary)";

        KeyHolder holder = new GeneratedKeyHolder();
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

        MapSqlParameterSource source = new MapSqlParameterSource()
                .addValue("fname", mentor.getFirstName())
                .addValue("lname", mentor.getLastName())
                .addValue("patronymic", mentor.getPatronymic())
                .addValue("email", mentor.getEmail())
                .addValue("phoneNumber", mentor.getPhoneNumber())
                .addValue("salary", mentor.getSalary());

        namedParameterJdbcTemplate.update(query, source, holder, new String[]{"id"});

        mentor.setId(holder.getKey().longValue());
        return mentor;
    }
}