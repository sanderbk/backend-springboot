package io.swagger.configuration;

import io.swagger.jwt.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CustomWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtTokenFilter tokenFilter;

    private static final String[] AUTH_WHITELIST = {
            "/login",
            "/users",
            "/h2-console/**/**",
            "/api-docs/**",
            "/swagger-ui/**",
            "/",
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**"
    };


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(AUTH_WHITELIST); // Makes sure that the HTML pages of the whitelisted URLs are shown
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        // Force the use of HTTPS via Heroku. Uncomment this code if you are deploying the application there!
//        http.requiresChannel()
//                .requestMatchers(r -> r.getHeader("X-Forwarded-Proto") != null)
//                .requiresSecure();

        http.cors().and().csrf().disable();
        http.csrf().disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // Creates a session per http request and deletes it afterwards

        http.authorizeRequests().antMatchers(AUTH_WHITELIST)
                .permitAll() // Grant access to the Login, Register & H2-Console pages without a token
                .anyRequest().authenticated(); // Makes sure that a token is needed for any other URLs

        http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class); // Use the JWT Filter class
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


}
