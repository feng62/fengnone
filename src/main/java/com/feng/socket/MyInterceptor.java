package com.feng.socket;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.feng.socket.utils.JWTutils;
import lombok.val;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;


@Component
public class MyInterceptor implements HandlerInterceptor {
    private static final Logger logger =
            LoggerFactory.getLogger(MyInterceptor.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse
            response, Object handler) throws Exception {
        String path = request.getServletPath();//获取url
        PrintWriter out = null;

        String token = request.getHeader("AccessToken");
        JSONObject res = new JSONObject();
        if (path.split("/").length >= 3 &&"info".equals(path.split("/")[2])){
            if (token != null){
                String id = JWTutils.verify(token).getClaim("u_id").asString();
                logger.info("=====name   {}======",id);
                final Object o = redisTemplate.opsForValue().get(id);
                if (o != null){
                    return true;
                } else {
                    res.put("success", false);
                    res.put("message", "用户未登录！");
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType("application/json");
                    out = response.getWriter();
                    out.append(res.toString());
                    return false;
                }
            } else {
                res.put("success", false);
                res.put("message", "登陆过期！");
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json");
                out = response.getWriter();
                out.append(res.toString());
                return false;
            }
        }
        logger.info("====，获取的url:{}==token:{}==",path,token);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse
            response, Object handler, ModelAndView modelAndView) throws Exception {
//        response.addHeader("Access-Control-Allow-Origin", "*");
//        logger.info("执行完方法之后进执行(Controller方法调用之后)，但是此时还没进行视图渲染");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse
            response, Object handler, Exception ex) throws Exception {
//        logger.info("整个请求都处理完咯，DispatcherServlet也渲染了对应的视图咯，此时我可以做一些清理的工作了");
    }
}
