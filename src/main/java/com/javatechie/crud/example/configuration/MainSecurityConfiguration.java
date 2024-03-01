package com.javatechie.crud.example.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javatechie.crud.example.jwt.JwtEntryPoint;
import com.javatechie.crud.example.jwt.JwtTokenFilter;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.security.Key;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MainSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtEntryPoint jwtEntryPoint;

    @Bean
    public JwtTokenFilter jwtTokenFilter() {
        return new JwtTokenFilter();
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    /**
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/addUsuario",
                "/swagger-ui/**",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }

    @Bean
    public Key getKey() {
        return MacProvider.generateKey(SignatureAlgorithm.HS512);
    }

    @Bean
    public MessageSource getMessageProperty() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
