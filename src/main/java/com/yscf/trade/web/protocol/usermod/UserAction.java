package com.yscf.trade.web.protocol.usermod;

import com.github.ulwx.aka.frame.UIFrameAppConfig;
import com.github.ulwx.aka.frame.utils.JwtHelper;
import com.github.ulwx.aka.frame.utils.TokenInfo;
import com.github.ulwx.aka.webmvc.annotation.AkaMvcActionMethod;
import com.github.ulwx.aka.webmvc.web.action.ActionSupport;
import com.github.ulwx.aka.webmvc.web.action.CbRequest;
import com.github.ulwx.aka.webmvc.web.action.CbResult;
import com.ulwx.tool.RequestUtils;
import com.ulwx.tool.SnowflakeIdWorker;
import com.ulwx.tool.StringUtils;
import com.yscf.trade.web.protocol.usermod.domain.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Tag(name = "用户接口",description = "通用用户接口" )
public class UserAction extends ActionSupport {

    @Operation(summary  = "登录", description  ="用于用户登录")
    @ApiResponse(description = "登录响应", useReturnTypeSchema=true)
    @RequestBody(description = " data in the json format", content = @Content(mediaType = "application/json"))
    @AkaMvcActionMethod(httpMethod = "post")
    public CbResult<LoginResponse> login(@RequestBody CbRequest<LoginRequest> request){
        this.getRequest().getSession().invalidate();
        RequestUtils ru=this.getRequestUtils();
        LoginRequest loginRequest=request.getData();
        String userName=loginRequest.getUsername();
        String userPass=loginRequest.getUserpass();
        String smsCode=loginRequest.getSmscode();
        if(userName.isEmpty()){
             return this.getResult(this.JsonViewError("用户姓名不能为空！"));
        }
        LoginResponse loginRes=new LoginResponse();
        String userId=SnowflakeIdWorker.instance.nextId()+"";
        loginRes.setUserId(userId);
        String token=getJwtToken(userId,"","18565574703");
        loginRes.setToken(token);

        return this.getResult(this.JsonViewSuc("登录成功",loginRes));

    }
    private String getJwtToken(String userId,String deviceId,String phone){
        String uuid =  SnowflakeIdWorker.instance.nextId()+"";

        TokenInfo jwtInfo = new TokenInfo();
        jwtInfo.setJwtID(uuid);
        jwtInfo.setDeviceID(deviceId);
        jwtInfo.setUser(userId);
        jwtInfo.setPhone(phone);
        UIFrameAppConfig uIFrameAppConfig= beanGet.getBean(UIFrameAppConfig.class);
        String secret=uIFrameAppConfig.getCurRequestHander().getJwtVerify().getSecret();
        String jwt = JwtHelper.createJWT(jwtInfo,secret,3600);

        return jwt;
    }

    @Operation(summary = "获取用户列表",description="用于获取用户列表，按条件查询")
    @RequestBody(description = " data in the json format", content = @Content(mediaType = "application/json"))
    @ApiResponse(description = "获取用户列表响应", useReturnTypeSchema=true)
    @AkaMvcActionMethod(httpMethod = "post", requestContentType ="application/json")
    public CbResult<List<MyUser>> getUserList(@RequestBody CbRequest<UserQuery> request){
        UserQuery query=request.getData();
        String userName=query.getUserName();
        String age=query.getUserAge();
        String userSex=query.getUserSex();
        ////根据参数查询
        List<MyUser> list=new ArrayList<>();
        MyUser user1=new MyUser();
        user1.setName("user1");
        user1.setPass("user1-pass");
        user1.setAge(10);
        user1.setBirth(LocalDate.of(2010,10,12));
        list.add(user1);

        MyUser user2=new MyUser();
        user2.setName("user2");
        user2.setPass("user2-pass");
        user2.setAge(10);
        user2.setBirth(LocalDate.of(2012,10,12));
        list.add(user2);

        return this.getResult(this.JsonViewSuc(list));

    }

    @Operation(summary  = "网关登录", description  ="用于用户网关登录")
    @ApiResponse(description = "响应", useReturnTypeSchema=true)
    @RequestBody(description = " data in the json format", content = @Content(mediaType = "application/json"))
    @AkaMvcActionMethod(httpMethod = "post")
    public CbResult<GatewayLoginResponse> gatewayLogin(@RequestBody CbRequest<LoginRequest> request){
        this.getRequest().getSession().invalidate();
        RequestUtils ru=this.getRequestUtils();
        LoginRequest loginRequest=request.getData();
        String userName=loginRequest.getUsername();
        String userPass=loginRequest.getUserpass();
        String smsCode=loginRequest.getSmscode();
        if(userName.isEmpty()){
            return this.getResult(this.JsonViewError("用户姓名不能为空！"));
        }
        GatewayLoginResponse loginRes=new GatewayLoginResponse();
        String userId=SnowflakeIdWorker.instance.nextId()+"";
        loginRes.setUserId(userId);
        return this.getResult(this.JsonViewSuc(loginRes));
    }

    public static class GatewayLogoutRequset{
        private String userId;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
    @Operation(summary  = "网关退出", description  ="用于用户网关退出")
    @ApiResponse(description = "响应", useReturnTypeSchema=true)
    @RequestBody(description = " data in the json format", content = @Content(mediaType = "application/json"))
    @AkaMvcActionMethod(httpMethod = "post")
    public CbResult<String> gatewayLogout(@RequestBody CbRequest<GatewayLogoutRequset> request){
        this.getRequest().getSession().invalidate();
        RequestUtils ru=this.getRequestUtils();
        GatewayLogoutRequset loginRequest=request.getData();
        String userId=loginRequest.getUserId();
        if(StringUtils.isEmpty(userId)){
            return this.getResult(this.JsonViewError("用户id不能为空！"));
        }

        return this.getResult(this.JsonViewSuc("退出成功！"));
    }
}
