package ti.kt.agh.master.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${spring.security.mode}")
    boolean isAuthorized;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if (isAuthorized) {
            http
                    .cors().and().csrf().disable()
                    .authorizeRequests()
                        .antMatchers("/css/**", "/js/**").permitAll()
                        .antMatchers(HttpMethod.POST,"/message").permitAll()
                        .anyRequest().permitAll()
                    .and()
                        .formLogin().permitAll();
        } else
            http.authorizeRequests().anyRequest().permitAll();
    }
}