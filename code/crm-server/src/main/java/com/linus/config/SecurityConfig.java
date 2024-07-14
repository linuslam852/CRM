package com.linus.config;

import com.linus.config.handler.MyAuthenticationFailureHandler;
import com.linus.config.handler.MyAuthenticationSuccessHandler;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class SecurityConfig {

    @Resource
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;


    @Resource
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,CorsConfigurationSource corsConfigurationSource) throws Exception {
        return httpSecurity
                .formLogin((formLogin) -> {
                    formLogin.loginProcessingUrl("/api/login") //登入處理地址，不需要寫controller
                            .usernameParameter("loginAct")
                            .passwordParameter("loginPwd")
                            .successHandler(myAuthenticationSuccessHandler)
                            .failureHandler(myAuthenticationFailureHandler);
                })
                .authorizeHttpRequests((authorize) -> {
                    authorize.requestMatchers("/api/login").permitAll()
                    .anyRequest().authenticated(); //任何請求都需要登入後才能訪問
                })
                .csrf((csrf)->{
                    csrf.disable(); //禁止跨站請求偽造
                })
                .cors((cors)->{
                    cors.configurationSource(corsConfigurationSource); //支持跨域請求
                })
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",configuration);
        return source;
    }
}
