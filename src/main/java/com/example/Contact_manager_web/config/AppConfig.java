package com.example.Contact_manager_web.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${cloudinary.cloud.name}")
    private String cloudName_cloudinary ;

    @Value("${cloudinary.api.key}")
    private  String apiKey_cloudinary ;
    @Value("${cloudinary.api.secret}")
    private String apiSecret_cloudinary;

    //creating bean of cloudinary
    @Bean
    public Cloudinary cloudinary(){
        return new Cloudinary(
        ObjectUtils.asMap(
                "cloud_name" ,cloudName_cloudinary,
                "api_key",apiKey_cloudinary,
                "api_secret",apiSecret_cloudinary
        )
        );
    }
}
