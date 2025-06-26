package com.example.nutricatalog.utils;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.HashSet;
import java.util.Set;
public class SharedPreferencesHelper {
    private static final String PREF_NAME = "nutricatalog_pref";
    private static final String KEY_FAVORITES = "favorites";

    public static void addFavorite(Context context, String foodName) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Set<String> favorites = prefs.getStringSet(KEY_FAVORITES, new HashSet<>());
        favorites = new HashSet<>(favorites); // make mutable
        favorites.add(foodName);
        prefs.edit().putStringSet(KEY_FAVORITES, favorites).apply();
    }

    public static void removeFavorite(Context context, String foodName) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Set<String> favorites = prefs.getStringSet(KEY_FAVORITES, new HashSet<>());
        favorites = new HashSet<>(favorites); // make mutable
        favorites.remove(foodName);
        prefs.edit().putStringSet(KEY_FAVORITES, favorites).apply();
    }

    public static boolean isFavorite(Context context, String foodName) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Set<String> favorites = prefs.getStringSet(KEY_FAVORITES, new HashSet<>());
        return favorites.contains(foodName);
    }

    public static Set<String> getFavorites(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getStringSet(KEY_FAVORITES, new HashSet<>());
    }
}
