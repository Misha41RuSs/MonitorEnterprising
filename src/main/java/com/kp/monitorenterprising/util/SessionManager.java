package com.kp.monitorenterprising.util;

import com.kp.monitorenterprising.model.User;

public class SessionManager {
    private static User currentUser;

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static User getCurrentUser() {
        return currentUser;
    }
}
