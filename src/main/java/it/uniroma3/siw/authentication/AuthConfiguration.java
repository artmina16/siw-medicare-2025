package it.uniroma3.siw.authentication;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import it.uniroma3.siw.security.CustomAccessDeniedHandler;

import static it.uniroma3.siw.model.Credentials.DOCTOR_ROLE;
import static it.uniroma3.siw.model.Credentials.ADMIN_ROLE;
import static org.springframework.security.config.Customizer.withDefaults;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
//public  class WebSecurityConfig {
	public class AuthConfiguration {

    @Autowired
    private DataSource dataSource;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .authoritiesByUsernameQuery("SELECT username, role from credentials WHERE username=?")
                .usersByUsernameQuery("SELECT username, password, 1 as enabled FROM credentials WHERE username=?");
    }
    
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    protected SecurityFilterChain configure(final HttpSecurity httpSecurity, CustomAccessDeniedHandler customAccessDeniedHandler) throws Exception{

        httpSecurity
                .csrf(withDefaults()).cors(cors -> cors.disable())
                .authorizeHttpRequests(requests -> requests
//                .requestMatchers("/**").permitAll()
                        // chiunque (autenticato o no) può accedere alle pagine index, login, register, ai css e alle immagini
                        .requestMatchers(HttpMethod.GET, "/", "/index", "/login", "/register", "/css/**", "/images/**").permitAll()
                        // chiunque (autenticato o no) può mandare richieste POST al punto di accesso per login e register 
                        .requestMatchers(HttpMethod.POST, "/register", "/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/doctor/**").hasAnyAuthority(DOCTOR_ROLE)
                        .requestMatchers(HttpMethod.POST, "/doctor/**").hasAnyAuthority(DOCTOR_ROLE)
                        .requestMatchers(HttpMethod.GET, "/admin/**").hasAnyAuthority(ADMIN_ROLE)
                        .requestMatchers(HttpMethod.POST, "/admin/**").hasAnyAuthority(ADMIN_ROLE)
                        // tutti gli utenti autenticati possono accere alle pagine rimanenti 
                        .anyRequest().authenticated())
                		// LOGIN:
                		.formLogin(login -> login
                        .loginPage("/login")
                        .permitAll()
                        .defaultSuccessUrl("/success", true)
                        .failureUrl("/login?error=true")).logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .clearAuthentication(true).permitAll())
                		.exceptionHandling(handling -> handling
                        .accessDeniedHandler(customAccessDeniedHandler));
        return httpSecurity.build();
    }
}