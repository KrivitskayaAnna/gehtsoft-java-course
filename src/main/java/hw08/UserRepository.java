//package hw08;
//
//import org.springframework.stereotype.Repository;
//
//import java.util.HashMap;
//import java.util.Random;
//
//@Repository
//public class UserRepository {
//    private HashMap<Long, User> users;
//
//    private UserRepository() {
//        this.users = new HashMap<>();
//    }
//
//    public User getById(Long id) {
//        return users.get(id);
//    }
//
//    public User update(Long id, User user) {
//        User existingUser = users.get(id);
//        if (existingUser != null) {
//            return users.put(id, user);
//        }
//        return null;
//    }
//
//    public Long create(User user) {
//        Random random = new Random();
//        Long newId = random.nextLong(0, Long.MAX_VALUE);
//        users.put(newId, user);
//        return newId;
//    }
//
//    public User deleteById(Long id) {
//        return users.remove(id);
//    }
//}
