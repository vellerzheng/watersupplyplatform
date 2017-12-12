package com.waterworks.service.tools.currentTime;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by vellerzheng on 2017/10/25.
 */
public class CurrentDateTime implements Converter<String,Date> {
    @Override
    public Date convert(String s) {
        // 将日期串转成日期类型（格式是yyyy-MM-dd)
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try{
            return simpleDateFormat.parse(s);
        }catch (ParseException e){
            e.printStackTrace();
        }
        return null;
    }

    public static Date currentTime(){
        // 将日期串转成日期类型（格式是yyyy-MM-dd HH:mm:ss)
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            return simpleDateFormat.parse(simpleDateFormat.format(new Date()));
        }catch (ParseException e){
            e.printStackTrace();
        }
        return null;
    }

}
