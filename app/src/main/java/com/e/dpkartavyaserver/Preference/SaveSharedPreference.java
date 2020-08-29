package com.e.dpkartavyaserver.Preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreference {
    static final String PREF_USER_NAME= "username",PREF_first_name="App",PREF_PHOTO="photo";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserName(Context ctx, String userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName);
        editor.commit();
    }
    public static void setPhoto(Context ctx,String photo)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
       // editor.putString(PREF_USER_NAME, userName);
        editor.putString(PREF_PHOTO,photo);
        editor.commit();
    }
    public static String getUserName(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }
    public static String getPhoto(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_PHOTO, "");
    }
    public static void clearPreference(Context context){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.clear();
        editor.commit();
    }
}
