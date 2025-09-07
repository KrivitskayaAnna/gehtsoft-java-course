package hw07.custom;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomUserDto {
    private String name;
    private String email;

    public CustomUser toUser(Long id) {
        return new CustomUser(id, name, email);
    }
}