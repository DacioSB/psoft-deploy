package com.ufcg.psoft.config;

import java.util.Arrays;

import com.ufcg.psoft.security.JWTAuthFilter;
import com.ufcg.psoft.security.JWTAuthoFilter;
import com.ufcg.psoft.security.JWTUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.core.env.Environment;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    
    @Autowired
    private Environment env;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
	private JWTUtil jwtUtil;

    private static final String[] PUBLIC_MATCHERS_GET = {
        "/api/produto/info/**",
        "/swagger*/**",
        "/v2/api-docs", 
        "/configuration/**", 
        "/swagger*/**", 
        "/webjars/**"
    };
    private static final String[] PUBLIC_MATCHERS = {
        "/h2/**"
    };
    private static final String[] PUBLIC_MATCHERS_POST = {
        "/api/register/**"
    };
    //Autorização(url quem pode acessar url e pa)
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //Permitir o h2
        if (Arrays.asList(env.getActiveProfiles()).contains("dev")) {
            http.headers().frameOptions().disable();
        }

        http.cors().and().authorizeRequests()
            .antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll()
            .antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll()
            .antMatchers(PUBLIC_MATCHERS).permitAll()
            .anyRequest().authenticated().and()
            .csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilter(new JWTAuthFilter(authenticationManager(), this.jwtUtil));
        http.addFilter(new JWTAuthoFilter(authenticationManager(), this.jwtUtil, this.userDetailsService));
    }
    //Autenticação
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.userDetailsService).passwordEncoder(this.bCryptPasswordEncoder());
	}
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
