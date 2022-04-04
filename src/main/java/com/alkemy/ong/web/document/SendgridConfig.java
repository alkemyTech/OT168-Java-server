/*package com.alkemy.ong.web.document;

import com.sendgrid.SendGrid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SendgridConfig {

    @Value("${sendgridProperties.apiKey}")
    private String apiKey;

    @Bean
    public SendGrid getSendgrid(){
        return new SendGrid(apiKey);
    }
}
*/