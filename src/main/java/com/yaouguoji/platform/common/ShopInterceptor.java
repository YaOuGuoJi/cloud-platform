package com.yaouguoji.platform.common;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.interfaces.Claim;
import com.yaouguoji.platform.util.ShopTokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @author liuwen
 * @date 2018/12/3
 */
@Component
public class ShopInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShopInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String shopToken = ShopTokenUtil.getShopToken(request);
        if (!StringUtils.isEmpty(shopToken)) {
            try {
                Map<String, Claim> claimMap = ShopTokenUtil.verifyToken(shopToken);
                String shopId = claimMap.get("shopId").asString();
                request.setAttribute("shopId", shopId);
                return true;
            } catch (Exception e) {
                LOGGER.error("token验证失败！", e);
            }
        }
        sendJsonMessage(response);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           @Nullable ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handle, Exception ex) {
    }

    private static void sendJsonMessage(HttpServletResponse response) throws Exception {
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.print(JSON.toJSONString(CommonResult.fail(401, "没有登录！")));
        writer.close();
        response.flushBuffer();
    }
}
