package hw08;

import hw09.PureJdbcUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class UserService {
    @Autowired
    private PureJdbcUserRepository userRepository;

    public User getUserById(Long id) throws SQLException {
        return userRepository.getById(id);
    }

    public User updateUser(Long id, User user) throws SQLException {
        return userRepository.update(id, user);
    }

    public Long createUser(User user) throws SQLException {
        return userRepository.create(user);
    }

    public User deleteUserById(Long id) throws SQLException {
        return userRepository.deleteById(id);
    }
}
