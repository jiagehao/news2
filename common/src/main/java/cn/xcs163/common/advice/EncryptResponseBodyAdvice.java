package cn.xcs163.common.advice;

import cn.xcs163.common.annotation.DisabledEncrypt;
import cn.xcs163.common.properties.EncryptProperties;
import cn.xcs163.common.utils.AesEncryptUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class EncryptResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Autowired
    private EncryptProperties encryptProperties;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean supports(MethodParameter parameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return !parameter.getMethod().isAnnotationPresent(DisabledEncrypt.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter parameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        try {
            String content = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(body);
            if (!StringUtils.hasText(encryptProperties.getKey())) {
                throw new NullPointerException("请配置api.encrypt.key");
            }
            String result =  AesEncryptUtils.aesEncrypt(content, encryptProperties.getKey());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("加密失败！");
        }
    }
}
