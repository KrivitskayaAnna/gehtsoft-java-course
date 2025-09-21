package hw09;

import hw08.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class PureJdbcUserRepository {
    private final Connection connection;
    private final String schemaName;
    private final String tableName;

    @Autowired
    public PureJdbcUserRepository(
            @Value("${postgres.connection.endpoint}") String endpoint,
            @Value("${postgres.connection.user}") String user,
            @Value("${postgres.connection.password}") String password,
            @Value("${postgres.database.schema}") String schemaName,
            @Value("${postgres.database.table}") String tableName) throws SQLException {
        this.connection = DriverManager.getConnection(endpoint, user, password);
        this.schemaName = schemaName;
        this.tableName = tableName;
    }

    public User getById(Long id) throws SQLException {
        String sql = String.format(
                "SELECT * FROM %s.%s WHERE id = ?",
                schemaName,
                tableName
        );
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getInt("age")
                );
            }
        }
        return null;
    }

    public User update(Long id, User user) throws SQLException {
        User existingUser = getById(id);
        if (existingUser != null) {
            String sql = String.format(
                    "UPDATE %s.%s SET name = ?, surname = ?, age = ? WHERE id = ?",
                    schemaName,
                    tableName
            );
            try (PreparedStatement st = connection.prepareStatement(sql)) {
                st.setString(1, user.getName());
                st.setString(2, user.getSurname());
                st.setInt(3, user.getAge());
                st.setLong(4, id);
                st.executeUpdate();
            }
            return user;
        }
        return null;
    }

    public Long create(User user) throws SQLException {
        String sql = String.format(
                "INSERT INTO %s.%s (name, surname, age) VALUES (?, ?, ?) RETURNING id",
                schemaName,
                tableName
        );
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, user.getName());
            st.setString(2, user.getSurname());
            st.setInt(3, user.getAge());
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getLong("id");
            }
        }
        return null;
    }

    public User deleteById(Long id) throws SQLException {
        String sql = String.format(
                "DELETE FROM %s.%s WHERE id = ? RETURNING *",
                schemaName,
                tableName
        );
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getInt("age")
                );
            }
        }
        return null;
    }
}