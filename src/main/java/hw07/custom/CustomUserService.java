package hw07.custom;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class CustomUserService {
    private Map<Long, CustomUser> users = new ConcurrentHashMap<>(); // In-memory storage
    private AtomicLong idGenerator = new AtomicLong(1);

    public List<CustomUserDto> getAllUsers() {
        return users.values().stream().map(CustomUser::toUserDto).toList();
    }

    public CustomUserDto getUserById(Long id) {
        return users.get(id).toUserDto();
    }

    public CustomUserDto createUser(CustomUserDto dto) {
        Long currentId = idGenerator.incrementAndGet();
        users.put(currentId, dto.toUser(currentId));
        return dto;
    }

    public CustomUserDto updateUser(Long id, CustomUserDto dto) {
        users.put(id, dto.toUser(id));
        return dto;
    }

    public CustomUserDto patchUser(Long id, Map<String, Object> updates) {
        CustomUser currentUser = users.get(id);
        if (updates.containsKey("name")) {
            currentUser.setName(updates.get("name").toString());
        }
        if (updates.containsKey("email")) {
            currentUser.setEmail(updates.get("email").toString());
        }
        users.put(id, currentUser);
        return currentUser.toUserDto();
    }

    public void deleteUser(Long id) {
        users.remove(id);
    }
}