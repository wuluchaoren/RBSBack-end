package com.rbs.project.utils;

import com.rbs.project.exception.MyException;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 10:06 2018/12/19
 */
public class JsonUtils {

    /**
     * Description: String格式转Timestamp
     *
     * @Author: 17Wang
     * @Time: 10:07 2018/12/19
     */
    public static Timestamp StringToTimestamp(String s) throws MyException {
        try {
            return Timestamp.valueOf(s);
        }catch (Exception e){
            throw new MyException("String格式转Timestamp错误！String的类型必须形如： yyyy-mm-dd hh:mm:ss",MyException.ERROR);
        }
    }

    /**
     * Description: Timestamp格式转String
     *
     * @Author: 17Wang
     * @Time: 10:11 2018/12/19
     */
    public static String TimestampToString(Timestamp ts) {
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return sdf.format(ts);
    }
}
