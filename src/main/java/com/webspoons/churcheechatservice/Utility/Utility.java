package com.webspoons.churcheechatservice.Utility;

import java.util.Calendar;
import java.util.Date;

public class Utility {

    public static Date currentTime(){
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }
}
