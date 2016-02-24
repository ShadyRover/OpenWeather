package com.sshex.openweather.utils;

/**
 * Created by shady on 17.02.2016.
 */
public class HelpUtils {
    public static String getWeatherIcon(String iconId){
        return iconId.substring(0,2);
    }
}
