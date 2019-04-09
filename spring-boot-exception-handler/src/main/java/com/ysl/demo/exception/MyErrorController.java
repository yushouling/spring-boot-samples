package com.ysl.demo.exception;

import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 统一错误请求控制器
 * 参考默认异常处理类：{@link BasicErrorController}
 */
@Controller
@RequestMapping("/error")
public class MyErrorController extends AbstractErrorController {

    private static final String ERROR_PATH = "error/";

    public MyErrorController(ErrorAttributes errorAttributes,
                             List<ErrorViewResolver> errorViewResolvers) {
        super(errorAttributes, errorViewResolvers);
    }

    /**
     * 跳转到错误页面，指定HTTP媒体映射请求为text/html
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(
            value = {ERROR_PATH},
            produces = {"text/html"}
    )
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
        int code = response.getStatus();
        if (404 == code) {
            return new ModelAndView("error/404");
        } else {
            return null;
        }
    }
    
    /**
     * ajax请求时返回错误码
     */
    @RequestMapping(value = ERROR_PATH)
    public String errorMsg(HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(200);
        int code = response.getStatus();
        if (404 == code) {
            return "404未找到资源";
        } else {
            return "500服务器错误";
        }
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
