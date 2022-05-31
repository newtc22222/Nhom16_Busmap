package hcmute.nhom16.busmap.model;

import hcmute.nhom16.busmap.Support;

public class UserAccount {
    private static User user;

    private UserAccount(User user) {
        this.user = user;
    }

    public static synchronized User login(String username, String password) {
        if (user == null) {
            boolean result = Support.checkAccount(username, password);
            if (result) {
                user = Support.getUser(username, password);
            }
        }
        return user;
    }

    public static void register(User _user) {
        Support.register(_user);
        user = Support.getUser(_user.getEmail(), _user.getPassword());
    }

    public static void logout() {
        user = null;
    }

    public static User getUser() {
        return user;
    }

    public static void update() {
        Support.updateUser(user);
    }
}
