//package io.renren.modules.sys.oauth2;
//
//import com.google.gson.Gson;
//import io.renren.common.utils.Result;
//import org.apache.commons.lang.StringUtils;
//import org.apache.http.HttpStatus;
//import org.apache.shiro.authc.AuthenticationException;
//import org.apache.shiro.authc.AuthenticationToken;
//import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
//import org.springframework.web.reactive.function.server.ServerRequest;
//
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * oauth2过滤器
// *
// * @author ChunLiang Hu
// * @email davichi2009@gmail.com
// * @date 2017-05-20 13:00
// */
//public class OAuth2Filter extends AuthenticatingFilter {
//
//    @Override
//    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
//        //获取请求token
//        String token = getRequestToken((HttpServletRequest) request);
//
//        if (StringUtils.isBlank(token)) {
//            return null;
//        }
//
//        return new OAuth2Token(token);
//    }
//
//    @Override
//    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
//        return false;
//    }
//
//    @Override
//    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
//        //获取请求token，如果token不存在，直接返回401
//        String token = getRequestToken((HttpServletRequest) request);
//        if (StringUtils.isBlank(token)) {
//            HttpServletResponse httpResponse = (HttpServletResponse) response;
//            String json = new Gson().toJson(Result.error(HttpStatus.SC_UNAUTHORIZED, "invalid token"));
//            httpResponse.getWriter().print(json);
//
//            return false;
//        }
//
//        return executeLogin(request, response);
//    }
//
//    @Override
//    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//        httpResponse.setContentType("application/json;charset=utf-8");
//        try {
//            //处理登录失败的异常
//            Throwable throwable = e.getCause() == null ? e : e.getCause();
//            Result result = Result.error(HttpStatus.SC_UNAUTHORIZED, throwable.getMessage());
//
//            String json = new Gson().toJson(result);
//            httpResponse.getWriter().print(json);
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        }
//
//        return false;
//    }
//
//    /**
//     * 获取请求的token
//     */
//    private String getRequestToken(ServerRequest httpRequest) {
//        //从header中获取token
//        String token = httpRequest.attribute("token").orElse("").toString();
//
//        //如果header中不存在token，则从参数中获取token
//        if (StringUtils.isBlank(token)) {
//            token = httpRequest.pathVariable("token");
//        }
//
//        return token;
//    }
//
//
//}