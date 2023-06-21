package com.yscf.trade.web;


import com.github.ulwx.aka.admin.services.service.ILoginSms;
import org.apache.log4j.Logger;

public class LoginSmsImpl implements ILoginSms {
    private static Logger logger = Logger.getLogger(LoginSmsImpl.class);
    @Override
    public int send(String mobile, String c) {
       return this.send("1", mobile,mobile);
    }

    @Override
    public int send(String type, String numbers, String msgContent) {
        //return SmsUtil.sendMsg(type, numbers, msgContent);
        logger.debug("模拟发送...type="+type+",numbers="+numbers+",msg="+msgContent);
        return 1;


    }


}
