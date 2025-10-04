package hw08;

import hw09.PureJdbcUserRepository;
import hw09.SpringDataJpaUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    @Autowired
    private SpringDataJpaUserRepository userRepository; //SpringDataJpaUserRepository

    public Optional<User> getUserById(Long id) throws SQLException {
        return userRepository.findById(id);
    }

    public User updateUser(Long id, User user) throws SQLException {
        userRepository.update(id, user.getName(), user.getSurname(), user.getAge());
        user.setId(id);
        return user;
    }

    public Long createUser(User user) throws SQLException {
        return userRepository.save(user).getId();
    }

    public void deleteUserById(Long id) throws SQLException {
        userRepository.deleteById(id);
    }
}
