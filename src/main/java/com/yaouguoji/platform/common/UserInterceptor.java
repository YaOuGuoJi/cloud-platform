package com.yaouguoji.platform.common;

import com.auth0.jwt.interfaces.Claim;
import com.yaouguoji.platform.util.TokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @author zhangqiang
 * @date 2018-12-10
 */

public class UserInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userToken = TokenUtil.getUserToken(request);
        if (StringUtils.isNotBlank(userToken)) {
            try {

                Map<String, Claim> claimMap = TokenUtil.verifyToken(userToken);
                String userId = claimMap.get("userId").asString();
                request.setAttribute("userId", userId);
                return true;
            } catch (UnsupportedEncodingException e) {
                LOGGER.error("token验证失败！", e);
            }
        }
        sendJsonMessage(response);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    private static void sendJsonMessage(HttpServletResponse response) throws Exception {
        ShopInterceptor.sendJson(response);
    }

}
