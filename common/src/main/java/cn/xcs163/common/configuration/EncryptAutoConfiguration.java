package cn.xcs163.common.configuration;

import cn.xcs163.common.advice.EncryptRequestBodyAdvice;
import cn.xcs163.common.advice.EncryptResponseBodyAdvice;
import cn.xcs163.common.properties.EncryptProperties;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@SpringBootConfiguration
@Component
@EnableAutoConfiguration
@EnableConfigurationProperties(EncryptProperties.class)
public class EncryptAutoConfiguration {

    /**
     * 配置请求解密
     * @return
     */
    @Bean
    public EncryptResponseBodyAdvice encryptResponseBodyAdvice() {
        return new EncryptResponseBodyAdvice();
    }

    /**
     * 配置请求加密
     * @return
     */
    @Bean
    public EncryptRequestBodyAdvice encryptRequestBodyAdvice() {
        return new EncryptRequestBodyAdvice();
    }
}
