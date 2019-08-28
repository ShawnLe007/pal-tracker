package io.pivotal.pal.tracker;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

public class JdbcTimeEntryRepository implements TimeEntryRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTimeEntryRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(
                    "INSERT INTO time_entries (project_id, user_id, date, hours)" +
                            " values (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS
            );

            statement.setLong(1, timeEntry.getProjectId());
            statement.setLong(2, timeEntry.getUserId());
            statement.setDate(3, Date.valueOf(timeEntry.getDate()));
            statement.setInt(4, timeEntry.getHours());

            return statement;
        }, generatedKeyHolder);

        return find(generatedKeyHolder.getKey().longValue());
    }

    @Override
    public TimeEntry find(long timeEntryId) {
        return jdbcTemplate.query(con -> {
            PreparedStatement statement =
                    con.prepareStatement("SELECT id, project_id, user_id, date," +
                            "hours FROM time_entries WHERE id = ?"
                    );

            statement.setLong(1, timeEntryId);

            return statement;
        }, resultSetExtractor);
    }

    @Override
    public List<TimeEntry> list() {
        return jdbcTemplate.query(con -> {
            PreparedStatement statement =
                    con.prepareStatement("SELECT id, project_id, user_id, date," +
                            " hours FROM time_entries");

            return statement;
        }, rowMapper);
    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {
        jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement("UPDATE time_entries " +
                    "SET project_id = ?, user_id = ?, date = ?,  hours = ? " +
                    "WHERE id = ?");

            statement.setLong(1, timeEntry.getProjectId());
            statement.setLong(2, timeEntry.getUserId());
            statement.setDate(3, Date.valueOf(timeEntry.getDate()));
            statement.setInt(4, timeEntry.getHours());
            statement.setLong(5, id);

            return statement;
        });

        return find(id);
    }

    @Override
    public void delete(long timeEntryId) {
        jdbcTemplate.update("Delete from time_entries where id = ?", timeEntryId);
    }

    private final RowMapper<TimeEntry> rowMapper = (rs, rowNum) -> new TimeEntry(
            rs.getLong("id"),
            rs.getLong("project_id"),
            rs.getLong("user_id"),
            rs.getDate("date").toLocalDate(),
            rs.getInt("hours")
    );

    private final ResultSetExtractor<TimeEntry> resultSetExtractor = (rs) ->
            rs.next() ? rowMapper.mapRow(rs, rs.getRow()) : null;
}
