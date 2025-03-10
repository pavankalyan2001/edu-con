package com.edu.consul.util;

import com.edu.consul.exceptions.BadRequestException;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EduUtils {

    public static Date getStandardDate(String dateStr){
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return simpleDateFormat.parse(dateStr);
        }catch (Exception e){
            throw new BadRequestException("Wrong Date format");
        }
    }

    public static String getNormalDate(Date date){
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy");
            return simpleDateFormat.format(date);
        }catch (Exception e){
            throw new BadRequestException("Wrong Date format");
        }
    }
}
