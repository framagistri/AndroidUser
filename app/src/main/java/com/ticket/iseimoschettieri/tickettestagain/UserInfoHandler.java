package com.ticket.iseimoschettieri.tickettestagain;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Francesco on 01/06/2017.
 */

public class UserInfoHandler {

    public UserInfoHandler(){
    }

    public static void saveInfo(Context myContext, String username, String password){
        SharedPreferences sharedPref = myContext.getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("username",username);
        editor.putString("password",password);
        editor.apply();
    }

    public static String getUsername(Context myContext){
        SharedPreferences sharedPref = myContext.getSharedPreferences("userInfo",Context.MODE_PRIVATE);

        return sharedPref.getString("username","");
    }

    public static String getPassword(Context myContext){
        SharedPreferences sharedPref = myContext.getSharedPreferences("userInfo",Context.MODE_PRIVATE);

        return sharedPref.getString("password","");
    }

    public static void setUsername(Context myContext,String username){
        SharedPreferences sharedPref = myContext.getSharedPreferences("userInfo",Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("username",username);
        editor.apply();
    }

    public static void setPassword(Context myContext,String password){
        SharedPreferences sharedPref = myContext.getSharedPreferences("userInfo",Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("password",password);
        editor.apply();
    }

    public static void logout(Context myContext){
        SharedPreferences sharedPref = myContext.getSharedPreferences("userInfo",Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("username","");
        editor.putString("password","");
        editor.apply();
    }

}
