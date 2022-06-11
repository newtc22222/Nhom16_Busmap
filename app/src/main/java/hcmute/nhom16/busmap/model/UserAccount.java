package hcmute.nhom16.busmap.model;

import android.content.Context;
import android.util.Log;

import hcmute.nhom16.busmap.Support;

public class UserAccount {
    private static User user;

    private UserAccount(User user) {
        this.user = user;
    }

    public static synchronized User login(Context context, String email, String password) {
        if (user == null) {
            user = Support.getUser(context, email, password);
        }
        return user;
    }

    public static void register(Context context, User _user) {
        Support.register(context, _user);
        user = Support.getUser(context, _user.getEmail(), _user.getPassword());
    }

    public static void logout() {
        user = null;
    }

    public static User getUser() {
        return user;
    }

    public static void update(Context context) {
        Support.updateUser(context, user);
    }
}
