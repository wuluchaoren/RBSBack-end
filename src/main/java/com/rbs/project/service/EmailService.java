package com.rbs.project.service;

import com.rbs.project.exception.MyException;
import com.rbs.project.utils.EmailUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 12:24 2018/12/16
 * @Modified by:
 */
@Service
public class EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    public String sendEmail(String[] sendTo, String message) throws Exception {
        //内容以html格式发送,防止被当成垃圾邮件
        StringBuffer messageText = new StringBuffer();
        messageText.append("<h2>OOAD课程通知</h2></br>");
        String emailText = "<a>" + message + "</a>";
        messageText.append(emailText);
        //发送邮件抄送一份给自己，防止被认为是垃圾邮件
        if (sendTo == null) {
            throw new MyException("发送邮件错误！邮件地址为空", MyException.ERROR);
        }
        boolean isSend = EmailUtils.sendEmail("ooadEmail邮件", sendTo, new String[]{"ooadmail2_3@126.com"}, messageText.toString(), null);
        return "发送邮件:" + isSend;
    }

}
