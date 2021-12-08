package com.example.firedetectionapp.Database;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences userSession;
    SharedPreferences.Editor editor;
    Context context;

    private static final String IS_LOGIN = "IsLoggedIn";

    public static final String KEY_FULLNAME = "fullName";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PHONENUMBER = "password";
    public static final String KEY_PASSWORD = "phoneNumber";
    public static final String KEY_DATE = "dob";
    public static final String KEY_GENDER = "gender";

    public SessionManager(Context _context) {
        context = _context;
        userSession = context.getSharedPreferences("userLoginSession", Context.MODE_PRIVATE);
        editor = userSession.edit();
    }

    public void createLoginSession(String fullName, String email, String password, String phoneNumber, String dob, String gender) {
        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_FULLNAME, fullName);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_PHONENUMBER, phoneNumber);
        editor.putString(KEY_DATE, dob);
        editor.putString(KEY_GENDER, gender);

        editor.commit();
    }

    public HashMap<String, String> getUserDetailsFromSession() {
        HashMap<String, String> userData = new HashMap<String, String>();

        userData.put(KEY_FULLNAME, userSession.getString(KEY_FULLNAME, null));
        userData.put(KEY_EMAIL, userSession.getString(KEY_EMAIL, null));
        userData.put(KEY_PASSWORD, userSession.getString(KEY_PASSWORD, null));
        userData.put(KEY_PHONENUMBER, userSession.getString(KEY_PHONENUMBER, null));
        userData.put(KEY_DATE, userSession.getString(KEY_DATE, null));
        userData.put(KEY_GENDER, userSession.getString(KEY_GENDER, null));

        return userData;

    }

    public boolean checkLogin() {
        if (userSession.getBoolean(IS_LOGIN, false)) {
            return true;
        } else
            return false;
    }

    public void logoutUserFromSession() {
        editor.clear();
        editor.commit();
    }
}
