package hw07_0;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<Long> createUser(@RequestBody User user) {
        Long id = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        User previousUser = userService.updateUser(id, user);
        if (previousUser == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        User deleted = userService.deleteUserById(id);
        if (deleted == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
}

//curl -vvv -X POST http://localhost:8080/api/users -H 'Content-Type: application/json' -d '{"name":"Ann", "surname":"K", "age":"24"}'
//curl -vvv -X GET http://localhost:8080/api/users/5556462636854744182
//curl -vvv -X PUT http://localhost:8080/api/users/5556462636854744182 -H 'Content-Type: application/json' -d '{"name":"Lily", "surname":"K", "age":"24"}'
//curl -vvv -X DELETE http://localhost:8080/api/users/5556462636854744182
