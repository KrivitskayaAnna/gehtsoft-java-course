package hw07.custom;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomUser {
    private Long id;
    private String name;
    private String email;

    public CustomUserDto toUserDto() {
        return new CustomUserDto(name, email);
    }
}