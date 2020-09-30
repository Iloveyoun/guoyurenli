package com.example.guoyurenli;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;


@SpringBootApplication 
@EnableScheduling	// 使用SpringTask定时器要加上这个注解
public class GuoyurenliTheofficialwebsiteApplication {
	
	@Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() 
	{
	    // 定义一个 HttpMessageConverter
	    FastJsonHttpMessageConverter fastConverter 
	    	= new FastJsonHttpMessageConverter();
        // FastJSON的配置参数
        FastJsonConfig fastConfig = new FastJsonConfig();
        fastConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        fastConverter.setFastJsonConfig(fastConfig);
        return new HttpMessageConverters(fastConverter);
	}

	public static void main(String[] args) {
		SpringApplication.run(GuoyurenliTheofficialwebsiteApplication.class, args);
	}

}
