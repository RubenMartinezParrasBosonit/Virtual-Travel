package com.ruben.backempresa.shared.security.config;

import com.ruben.backempresa.shared.security.filter.CustomAuthenticationFilter;
import com.ruben.backempresa.shared.security.filter.CustomAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //Core interface which loads user-specific data.
    @Autowired
    private UserDetailsService userDetailsService;

    //Implementation of PasswordEncoder that uses the BCrypt strong hashing function.
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //Nuestro filtro de autentificación necesita de un Manager de Autentificación
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());

        //Por defecto, la url para hacer login es "/login/, con esta función, lo podemos cambiar
        customAuthenticationFilter.setFilterProcessesUrl("/api/login");

        /*
         * La razón para deshabilitar el CSRF es que la aplicación de spring boot está abierta al público o es engorroso cuando
         * se está en fase de desarrollo o de pruebas.
         */
        http.csrf().disable();

        /*
         * We can control exactly when our session gets created and how Spring Security will interact with it:
         *
         * always – A session will always be created if one doesn't already exist.
         * ifRequired – A session will be created only if required (default).
         * never – The framework will never create a session itself, but it will use one if it already exists.
         * stateless – No session will be created or used by Spring Security.
         */
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        /*
         * Empezamos a configurar a que endpoints puede acceder cada persona dependiendo del rol.
         */
        http.authorizeRequests().antMatchers("/api/login/**","/api/test/**", "/api/mail/send/**"
                , "/api/mail/save/**", "/api/reserva/**").permitAll();
        http.authorizeRequests().antMatchers(GET, "/api/user/**")
                .hasAnyAuthority("USER", "ADMIN");
        http.authorizeRequests().antMatchers(POST, "/api/user/**")
                .hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers(POST, "/api/role/**")
                .hasAnyAuthority("ADMIN");
        http.authorizeRequests().anyRequest().authenticated();

        //Añadimos los filtros de autorización y autentificación
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }
}
