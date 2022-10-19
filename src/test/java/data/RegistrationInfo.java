package data;

import lombok.Data;

/**
 * Дата-класс с описанием данных пользователя, нужных при регистрации
 */

@Data
public class RegistrationInfo {
    private final String login;
    private final String password;
    private final String status;
}
