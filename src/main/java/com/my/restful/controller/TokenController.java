package com.my.restful.controller;


import com.my.restful.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/token")
public class TokenController {


    @Autowired
    private TokenService tokenService;



    /**
     * 用户登录    添加用户至UserMapUtil
     * @param userInfo
     * @return
     */

    @PostMapping
    public ResponseEntity<HashMap<String, Object>> login(@RequestBody Map<String,String> userInfo){

        //获取用户名密码
        String userName = userInfo.get("userName");
        String password = userInfo.get("password");

        //返回状态码提示内容
        HashMap<String, Object> result = new HashMap<>();
        String token = tokenService.login(userName, password);
        if(null == token){
            result.put("message","invalid username or password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }else{
            result.put("token",token);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }

    }


    /**
     * 登出 在erMapUtil删除用户
     * @param request
     * @return
     */

    @DeleteMapping
    public Map<String,Object> layout(HttpServletRequest request){
        String token = null;
        String requestHeader = request.getHeader("Authorization");
        if(null != requestHeader && requestHeader.startsWith("Bearer ")){
            token = requestHeader.substring(7);
            System.err.println("will delete token");
            tokenService.logout(token);
        }
        HashMap<String, Object> result = new HashMap<>();
        result.put("message","用户：" + token +  " 已登出");
        return result;
    }


}
