package by.sunserg.grandcapital.service.jwt;


import by.sunserg.grandcapital.repository.entity.User;

public final class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(user.getId(), user.getName(), user.getPassword());
    }
}
