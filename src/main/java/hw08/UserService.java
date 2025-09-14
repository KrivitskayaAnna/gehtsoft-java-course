package hw08;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getUserById(Long id) {
        return userRepository.getById(id);
    }

    public User updateUser(Long id, User user) {
        return userRepository.update(id, user);
    }

    public Long createUser(User user) {
        return userRepository.create(user);
    }

    public User deleteUserById(Long id) {
        return userRepository.deleteById(id);
    }
}
