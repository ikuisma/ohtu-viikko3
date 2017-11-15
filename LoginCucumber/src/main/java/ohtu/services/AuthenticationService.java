package ohtu.services;

import ohtu.domain.User;
import ohtu.data_access.UserDao;

public class AuthenticationService {

    private UserDao userDao;

    public AuthenticationService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean logIn(String username, String password) {
        for (User user : userDao.listAll()) {
            if (user.getUsername().equals(username)
                    && user.getPassword().equals(password)) {
                return true;
            }
        }

        return false;
    }

    public boolean createUser(String username, String password) {
        if (userDao.findByName(username) != null) {
            return false;
        }

        if (invalid(username, password)) {
            return false;
        }

        userDao.add(new User(username, password));

        return true;
    }

    private boolean invalid(String username, String password) {
        // validity check of username and password
        boolean usernameNotAlpha = !username.chars().allMatch(Character::isLetter);
        boolean usernameTooShort = username.length() < 3;
        boolean passwordTooShort = password.length() < 8;
        boolean passwordHasNoSpecialChars = password.chars().allMatch(Character::isLetter);
        return usernameNotAlpha | usernameTooShort | passwordTooShort | passwordHasNoSpecialChars;
    }
}
