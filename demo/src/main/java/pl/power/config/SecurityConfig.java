package pl.power.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {// @formatter:off
        http.cors()
                .and()
                .authorizeRequests()
//                .antMatchers(HttpMethod.GET, "/tasks/**").permitAll()
                .antMatchers(HttpMethod.GET, "/tasks/**","/user/info")
                .hasAuthority("SCOPE_read")
                .anyRequest()
                .authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt();
    }//@formatter:on

}
