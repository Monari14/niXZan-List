package com.example.nixzan_list;

import android.content.Context;
import android.content.SharedPreferences;

public class ThemeUtils {

    private static final String PREFS_NAME = "theme_prefs";
    private static final String KEY_THEME = "theme";

    public static final int THEME_LIGHT = 0;
    public static final int THEME_DARK = 1;

    public static void setTheme(Context context, int theme) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_THEME, theme);
        editor.apply();
    }
    public static int getTheme(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_THEME, THEME_LIGHT);
    }
}