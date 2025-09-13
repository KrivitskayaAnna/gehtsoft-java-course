package hw07.custom;

import hw07.custom.annotations.CustomPathVariable;
import hw07.custom.annotations.CustomRequestBody;
import hw07.custom.annotations.CustomRequestMapping;
import hw07.custom.annotations.CustomRestController;
import hw07.custom.annotations.methods.*;

import java.util.List;
import java.util.Map;

@CustomRestController
@CustomRequestMapping(value="/api/v1/users")
public class CustomUserController {
    private final CustomUserService userService;

    public CustomUserController(CustomUserService userService) {
        this.userService = userService;
    }

    @CustomGetMapping
    public List<CustomUserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @CustomGetMapping("/{id}")
    public CustomUserDto getUserById(@CustomPathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    @CustomPostMapping
    public CustomUserDto createUser(@CustomRequestBody CustomUserDto user) {
        return userService.createUser(user);
    }

    @CustomPutMapping("/{id}")
    public CustomUserDto updateUser(@CustomPathVariable("id") Long id,
                                    @CustomRequestBody CustomUserDto user) {
        return userService.updateUser(id, user);
    }

    @CustomPatchMapping("/{id}")
    public CustomUserDto patchUser(@CustomPathVariable("id") Long id,
                                   @CustomRequestBody Map<String, Object> updates) {
        return userService.patchUser(id, updates);
    }

    @CustomDeleteMapping("/{id}")
    public void deleteUser(@CustomPathVariable("id") Long id) {
        userService.deleteUser(id);
    }
}