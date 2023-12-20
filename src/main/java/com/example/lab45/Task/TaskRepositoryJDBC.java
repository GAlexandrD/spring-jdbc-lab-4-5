package com.example.lab45.Task;

import com.example.taskmanager.Task.dto.UpdateTaskDto;
import com.example.taskmanager.Task.errors.DataBaseException;
import com.example.taskmanager.Task.errors.InvalidSortParamException;
import com.example.taskmanager.Task.errors.TaskNotFoundException;
import com.example.taskmanager.Task.utils.DateParser;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.List;


@Repository
public class TaskRepositoryJDBC implements TaskRepository {
    private final JdbcTemplate jdbcTemplate;
    private final DateParser dateParser;
    static private Task mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Task(rs.getLong(1), rs.getString(4), rs.getDate(2), rs.getInt(5), rs.getBoolean(3));
    }

    static private final String FIND_ONE_BY_ID = "SELECT * FROM task WHERE id=?";
    static private final String FIND_ONE_BY_NAME = "SELECT * FROM task WHERE name=?";
    static private final String FIND_ALL = "SELECT * FROM task";
    static private final String FIND_ALL_SORT_BY_DEADLINE = "SELECT * FROM task ORDER BY deadline";
    static private final String FIND_ALL_SORT_BY_PRIORITY = "SELECT * FROM task ORDER BY priority";
    static private final String UPDATE_TASK = "UPDATE task SET name = ?, deadline=?, priority = ?, done = ? WHERE id = ?";
    static private final String DELETE_TASK = "DELETE FROM task WHERE id=?";
    static private final String CREATE_TASK = "INSERT INTO task(name, deadline, priority) VALUES(?, ?, ?)";
    static private final String COUNT_TASKS = "SELECT COUNT(*) FROM task";
    static private final String FIND_PAGE = "SELECT * FROM task ORDER BY id LIMIT ? OFFSET ?";



    public TaskRepositoryJDBC(JdbcTemplate jdbcTemplate, DateParser dateParser) {
        this.jdbcTemplate = jdbcTemplate;
        this.dateParser = dateParser;
    }

    @Override
    public Task getTaskById(Long id) {
        Task task;
        try {
            task = jdbcTemplate.queryForObject(FIND_ONE_BY_ID, TaskRepositoryJDBC::mapRow, id);
            return task;
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new TaskNotFoundException(id);
        }
    }

    @Override
    public Task getByName(String name) {
        Task task;
        try {
            task = jdbcTemplate.queryForObject(FIND_ONE_BY_NAME, TaskRepositoryJDBC::mapRow, name);
            return task;
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new TaskNotFoundException(name);
        }
    }


    @Override
    public List<Task> getAllTasks() {
        return jdbcTemplate.query(FIND_ALL, TaskRepositoryJDBC::mapRow);
    }

    @Override
    public List<Task> getAllTasks(String sort) {
        if(sort.equals("deadline")) return jdbcTemplate.query(FIND_ALL_SORT_BY_DEADLINE, TaskRepositoryJDBC::mapRow);
        if(sort.equals("priority")) return jdbcTemplate.query(FIND_ALL_SORT_BY_PRIORITY, TaskRepositoryJDBC::mapRow);
        throw new InvalidSortParamException(sort);
    }


    @Override
    public PageImpl<Task> getAllTasks(Pageable pageable) {
        List<Task> tasks = jdbcTemplate.query(
                    FIND_PAGE,
                    TaskRepositoryJDBC::mapRow,
                    pageable.getPageSize(),
                    pageable.getOffset());
        int count = jdbcTemplate.queryForObject(COUNT_TASKS, Integer.class);
        return new PageImpl<Task>(tasks, pageable, count);
    }

    @Override
    public Task createTask(String taskName, String deadline, Integer priority) {
        Date deadlineDate = dateParser.parseDateStr(deadline);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreatorFactory psf = new PreparedStatementCreatorFactory(CREATE_TASK
                , Types.VARCHAR, Types.DATE, Types.BIGINT);
        psf.setGeneratedKeysColumnNames("id");
        PreparedStatementCreator psc = psf.newPreparedStatementCreator(new Object[]{taskName, deadlineDate, priority});
        try {
            jdbcTemplate.update(psc, keyHolder);
        } catch (Exception ex) {
            throw new DataBaseException(ex.getMessage());
        }
        Long key = (Long) keyHolder.getKey();
        return jdbcTemplate.queryForObject(FIND_ONE_BY_ID, TaskRepositoryJDBC::mapRow, key);
    }


    @Override
    public int updateTask(Long id, UpdateTaskDto update) {
        Date deadline = dateParser.parseDateStr(update.getDeadline());
        try {
            return jdbcTemplate.update(UPDATE_TASK, update.getTaskName(), deadline, update.getPriority(), update.getDone(), id);
        } catch (Exception ex) {
            throw new DataBaseException(ex.getMessage());
        }
    }

    @Override
    public int deleteTask(Long id) {
        try {
            return jdbcTemplate.update(DELETE_TASK, id);
        } catch (Exception ex) {
            throw new DataBaseException(ex.getMessage());
        }
    }
}
