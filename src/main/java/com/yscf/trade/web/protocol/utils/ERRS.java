package com.yscf.trade.web.protocol.utils;

import com.github.ulwx.aka.frame.protocol.utils.IError;

public class ERRS extends IError {
	
	public static int verify_sign_error=1;
	public static int param_error=2;
	public static int inter_property_error=5;
	public static int ip_white_null_error=7;
	public static int ip_white_not_exist_error=8;
	public static int third_error=9999;
	public static int bg_error=9998;
	public static int sign_error=9997;
	public static int unknow_error=8000;
	
	public static int recharge_fail=8001;
	
	static {
		errors.put(verify_sign_error, "验证签名错误");
		errors.put(param_error, "参数错误");
		errors.put(inter_property_error, "接口配置信息未设置");
		errors.put(ip_white_null_error, "ip白名单为空");
		errors.put(ip_white_not_exist_error, "请求ip不在白名单内");
	
		errors.put(unknow_error, "服务器处理失败！请联系客服处理！");
		errors.put(third_error, "银行存管处理失败！");
		errors.put(bg_error, "");
		errors.put(sign_error, "签名失败!!");
		
		errors.put(recharge_fail, "充值失败!!");
	}
}
