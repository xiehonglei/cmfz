package com.baizhi;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.util.Config;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;

import java.util.Properties;

@SpringBootApplication
@MapperScan("com.baizhi.dao")
public class CmfzApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmfzApplication.class, args);
    }

    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        fastConverter.setFastJsonConfig(fastJsonConfig);
        HttpMessageConverter<?> converter = fastConverter;
        return new HttpMessageConverters(converter);
    }

    @Bean
    public Producer KaptchaProducer() {
        Properties kaptchaProperties = new Properties();
        kaptchaProperties.put("kaptcha.border", "no");
        kaptchaProperties.put("kaptcha.textproducer.char.length", "4");
        kaptchaProperties.put("kaptcha.image.height", "50");
        kaptchaProperties.put("kaptcha.image.width", "150");
        kaptchaProperties.put("kaptcha.obscurificator.impl", "com.google.code.kaptcha.impl.ShadowGimpy");
        kaptchaProperties.put("kaptcha.textproducer.font.color", "black");
        kaptchaProperties.put("kaptcha.textproducer.font.names", "楷体,宋体");
        kaptchaProperties.put("kaptcha.textproducer.font.size", "50");
        kaptchaProperties.put("kaptcha.noise.impl", "com.google.code.kaptcha.impl.NoNoise");
        //kaptchaProperties.put("kaptcha.noise.impl","com.google.code.kaptcha.impl.DefaultNoise");
        kaptchaProperties.put("kaptcha.textproducer.char.string", "acdefhkmnprtwxy2345678");
        Config config = new Config(kaptchaProperties);
        return config.getProducerImpl();
    }
}
