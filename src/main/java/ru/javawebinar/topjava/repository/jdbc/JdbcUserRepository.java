package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@Transactional
public class JdbcUserRepository implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public User save(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);

        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
        } else if (namedParameterJdbcTemplate.update(
                "UPDATE users SET name=:name, email=:email, password=:password, " +
                        "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", parameterSource) == 0) {
            return null;
        }
        return user;
    }

    @Override
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE id=?", ROW_MAPPER, id);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public User getByEmail(String email) {
//        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        return DataAccessUtils.singleResult(users);
    }

    /*@Override
    public List<User> getAll() {
        return jdbcTemplate.query("SELECT * FROM users us join user_roles usr on us.id = usr.user_id ORDER BY us.name, us.email", ROW_MAPPER);
    }*/
    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("SELECT * FROM users u " +
                        "INNER JOIN user_roles r on u.id = r.user_id ORDER BY id, name, email",
                new ResultSetExtractor<List<User>>() {
                    @Override
                    public List<User> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                        List<User> list = new ArrayList<User>();
                        //Map<User, Role> map = new HashMap<>();
                        Set<Role> roless = new HashSet<>();
                        int id = 0, index = 0;
                        while (resultSet.next()) {
                            if (list.isEmpty()) {
                                User user = new User();
                                user.setId((int) resultSet.getLong("id"));
                                user.setName(resultSet.getString("name"));
                                user.setEmail(resultSet.getString("email"));
                                user.setPassword(resultSet.getString("password"));
                                user.setRegistered(resultSet.getTimestamp(5));
                                user.setEnabled(resultSet.getBoolean("enabled"));
                                user.setCaloriesPerDay(resultSet.getInt("calories_per_day"));
                                if (resultSet.getString("role").equals("ROLE_USER")) {
                                    roless.add(Role.ROLE_USER);
                                } else if (resultSet.getString("role").equals("ROLE_ADMIN")) {
                                    roless.add(Role.ROLE_ADMIN);
                                }
                                user.setRoles(roless);
                                list.add(user);
                                id = (int) resultSet.getLong("id");
                            } else if (id != (int) resultSet.getLong("id")) {
                                User user = new User();
                                user.setId((int) resultSet.getLong("id"));
                                user.setName(resultSet.getString("name"));
                                user.setEmail(resultSet.getString("email"));
                                user.setPassword(resultSet.getString("password"));
                                user.setRegistered(resultSet.getTimestamp(5));
                                user.setEnabled(resultSet.getBoolean("enabled"));
                                user.setCaloriesPerDay(resultSet.getInt("calories_per_day"));
                                if (resultSet.getString("role").equals("ROLE_USER")) {
                                    roless.add(Role.ROLE_USER);
                                } else if (resultSet.getString("role").equals("ROLE_ADMIN")) {
                                    roless.add(Role.ROLE_ADMIN);
                                }
                                user.setRoles(roless);
                                list.add(user);
                                id = (int) resultSet.getLong("id");
                                index++;
                            } else {
                                User user2 = list.get(index);

                                if (resultSet.getString("role").equals("ROLE_USER")) {
                                    roless.add(Role.ROLE_USER);
                                } else if (resultSet.getString("role").equals("ROLE_ADMIN")) {
                                    roless.add(Role.ROLE_ADMIN);
                                }
                                user2.setRoles(roless);
                                list.set(index, user2);
                            }


                            //resultSet.getString("role");
                            // map.putIfAbsent(user,)



                        }
                        return list;
                    }
                });
    }
}
