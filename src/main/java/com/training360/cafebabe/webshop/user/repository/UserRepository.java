package com.training360.cafebabe.webshop.user.repository;

import com.training360.cafebabe.webshop.user.entities.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserRepository {

    private JdbcTemplate template;

    public UserRepository(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    private RowMapper<User> mapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int i) throws SQLException {
            String name = rs.getString("name");
            String password = rs.getString("password");
            String realName = rs.getString("real_name");
            return new User(name, password, realName);
        };
    };

    // user létrehozása
    public CreateUserResponse createUser(String name, String password, String realName) {
        if (name == null || name.trim().length() == 0) {
            return new CreateUserResponse(false, "Username cannot be empty");
        }
        if(isValidUsername(name)) {
        template.update("insert into users (name, password, real_name) VALUES (?, ?, ?)",
                name, password, realName);
        return new CreateUserResponse(true, "Successfully created user :" + name);
        }
        return new CreateUserResponse(false, "Cannot create user. Username already taken: " + name);
    }

    //megnézi, hogy az adott név szerepel-e már az adatbázisban
    public boolean isValidUsername(String name) {
        if(name.trim()==""){
            return false;
        }
        Integer count = template.queryForObject("select count(name) as nev from users where name = ?", new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet rs, int i) throws SQLException {
                return rs.getInt("nev");
            }
        }, name);
        return count == 0;
    }

    //felhasználók listázása
    public List<User> listAllUser() {
        return template.query("select name, real_name from users where role = \'ROLE_USER\'", new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int i) throws SQLException {
                String userName = rs.getString("name");
                String realName = rs.getString("real_name");
                return new User(userName, realName);
            }
        });
    }

    // módosítja a felhasználó jelszavát, és teljes nevét
    public UpdateUserResponse updateUser(String username, String password, String realName) {
        int modifiedcolumn = template.update("UPDATE users SET real_name = ?, password = ? where name = ?", realName, password, username);
        if (modifiedcolumn != 0) {
            return new UpdateUserResponse(true, "modified");
        }
        return new UpdateUserResponse(false, "user not found with name: " + username);
    }

    //módosítja a felhasználó teljes nevét
    public UpdateUserResponse updateUserRealName(String username, String realName) {
        int modifiedcolumn = template.update("UPDATE users SET real_name = ? where name = ?", realName, username);
        if(modifiedcolumn != 0) {
            return new UpdateUserResponse(true, "real name succesfuly modified to: " + realName);
        }
        return new UpdateUserResponse(false, "user not found with name: " + username);
    }


    //adott felhasználó megkeresése username alapján
    public User getUserByUserName(String username) {
        return template.queryForObject("SELECT name, password, real_name from users where name = ?", mapper, username);
    }

    //username alapján adott felhasználó törlése
    public UpdateUserResponse deleteUser(String username) {
        template.update("DELETE FROM baskets where user_name = ?", username);
        template.update("UPDATE orders SET user_name = \'deleted user\' where user_name = ?", username);
        int modifiedcolumn = template.update("DELETE FROM users where name = ?", username);
        if(modifiedcolumn != 0) {
            return new UpdateUserResponse(true, "deleted");
        }
        return new UpdateUserResponse(false, "not found user with username: " + username);
    }

    //visszaadja összesen hány regisztrált felhasználó létezik az adatbázisban (user és admin is)
    public int getNumberOfUsers(){
        return template.queryForObject("SELECT COUNT(name) FROM users WHERE role <> 'ROLE_DELETED'",Integer.class);
    }
// ; DELETE FROM users where name = ?
}
