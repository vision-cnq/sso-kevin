package com.kevin.controller;

import com.kevin.utils.JWTUtils;
import com.kevin.utils.ResponseCodeEnum;
import com.kevin.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.kevin.commons.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author caonanqing
 * @version 1.0
 * @description     jwt控制器
 * @createDate 2019/8/19
 */
@Controller
@RequestMapping("/user")
public class JWTController {

    @Autowired
    JWTUtils jwtUtils;

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/testAll")
    @ResponseBody
    public Result testAll(HttpServletRequest request){
        Result result;
        String token = request.getHeader("token");
        // 该验证已经是在拦截器那边验证了，至此，该代码属于多余
        String subject = jwtUtils.getClaimsToken(token).getSubject();
        if(null != subject) {
            // 重新生成token，就是为了重置token的有效期
            String newToken = jwtUtils.createJWT(subject);
            result = Result.ok("user",subject);
            result.setToken(newToken);
        } else {
            // 未登录，请先登录，
            result = Result.build(ResponseCodeEnum.LOGIN_EXPIRATION.getCode(),ResponseCodeEnum.LOGIN_EXPIRATION.getMessage());
        }
        System.out.println(result.toString());
        return result;
    }

    /**
     * 登录
     * @param username      用户名
     * @param password      密码
     * @return
     */
    @RequestMapping("/login")
    @ResponseBody
    public Object login(String username, String password) {
        Result result;
        // 验证用户信息
        if(JWTUsers.isLogin(username,password)) {
            // 生成Token
            String token = jwtUtils.createJWT(username);
            result = Result.build(ResponseCodeEnum.OK.getCode(),"登录成功");
            result.setToken(token);
        } else {
            result = Result.build(ResponseCodeEnum.INTERNAL_SERVER_ERROR.getCode(),"登录失败");
        }
        System.out.println(result.toString());
        return result;
    }

}
