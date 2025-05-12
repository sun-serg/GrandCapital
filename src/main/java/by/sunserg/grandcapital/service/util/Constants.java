package by.sunserg.grandcapital.service.util;

public class Constants {

    private Constants() {
    }

    public static final String EMAIL_VALIDATION_PATTERN = "^(?=.{6,}$)[\\s]*[a-zA-Z0-9]+([!\"#$%&'()*+,\\-.\\/:;<=>?\\[\\]" +
            "\\^_{}][a-zA-z0-9]+)*@([\\w]+(-[\\w]+)?)(\\.[\\w]+[-][\\w]+)*" +
            "(\\.[a-z]{2,})+[\\s]*$";

    public static final String PHONE_VALIDATION_PATTERN = "^(?=.{0,}$)[0-9]*$";

    public static final String[] URL_WHITELIST = {
            "/auth/login",
            "/actuator/**",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/webjars/**"
    };

    public static final String MESSAGE_LOG_FORBIDDEN = "Попытка неавторизованного доступа пользователя c id {}";

    public static final String MESSAGE_INFO_FORBIDDEN = "Доступ запрещен";

    public static final String AUTH_ERROR_LOG_MESSAGE = "Вход с email {} не удался";

    public static final String AUTH_ERROR_INFO_MESSAGE = "Неверный email или пароль";

    public static final String LOG_NOT_UNIQUE_EMAIL_ERROR_MESSAGE = "Email {} уже используется";

    public static final String LOG_NOT_UNIQUE_PHONE_ERROR_MESSAGE = "Телефон {} уже используется";

    public static final String NOT_UNIQUE_EMAIL_ERROR_MESSAGE = "Email %s уже используется";

    public static final String NOT_UNIQUE_PHONE_ERROR_MESSAGE = "Телефон %s уже используется";

    public static final String LOG_USER_NOT_FOUND_MESSAGE = "Пользователь с email {} не найден";

    public static final String USER_FOUND_LOG_MESSAGE = "Пользователь найден по email: {}";

    public static final String USER_ID_NOT_FOUND_MESSAGE = "Пользователь c id %s не найден";

    public static final String EMAIL_ID_NOT_FOUND_LOG_MESSAGE = "EmailData с id {} не найдено для пользователя с id {}";

    public static final String EMAIL_NOT_FOUND_MESSAGE = "Email не найден для пользователя с id %s";

    public static final String PHONE_ID_NOT_FOUND_LOG_MESSAGE = "PhoneData с id {} не найдено для пользователя с id {}";

    public static final String PHONE_NOT_FOUND_MESSAGE = "Телефон не найден для пользователя с id %s";

    public static final String MONEY_TRANSFER_LOG_ERROR_MESSAGE = "Попытка перевода средств самому себе пользователем с id {}";

    public static final String MONEY_TRANSFER_ERROR_INFO_MESSAGE = "Нельзя переводить средства самому себе";

    public static final String MONEY_TRANSFER_AMOUNT_ERROR_MESSAGE = "Сумма перевода должна быть положительной";

    public static final String SENDER_ACCOUNT_NOT_FOUND_MESSAGE = "Аккаунт отправителя не найден";

    public static final String RECEIVER_ACCOUNT_NOT_FOUND_MESSAGE = "Аккаунт получателя не найден";

    public static final String MONEY_TRANSFER_NOT_ENOUGH_MONEY_MESSAGE = "Недостаточно средств для перевода";

    public static final String MONEY_TRANSFER_SUCCESS_MESSAGE = "Перевод средств выполнен успешно";
}

