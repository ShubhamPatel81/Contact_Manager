package com.example.Contact_manager_web.config;

import com.example.Contact_manager_web.service.Implementation.SecurityCustomUserDetailsService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@Configuration
public class SecurityConfig {

    /// user create and login
//    @Bean
//    public  UserDetailsService userDetailsService(){
//        return new InMemoryUserDetailsManager();
//    }

    @Autowired
    private OAuthenticationSuccessHandler oAuthenticationSuccessHandler;
    @Autowired
    private SecurityCustomUserDetailsService userDetailsService;
    @Bean
    public AuthenticationProvider  authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return  authenticationProvider;
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
       return new BCryptPasswordEncoder();
    }

    //configuration id authenticaton of spring securit
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
       httpSecurity
               .csrf(AbstractHttpConfigurer::disable)
               .authorizeHttpRequests(
                       authorize->{
//                           authorize.requestMatchers("/","/home","/register","/login","/services").permitAll();
                              authorize.requestMatchers("/user/**").authenticated()
                                        .anyRequest().permitAll();

                       }
               );
       //form login
        httpSecurity.formLogin(formLogin -> {

            //
            formLogin.loginPage("/login");
            formLogin.loginProcessingUrl("/authenticate");
            formLogin.successForwardUrl("/user/profile");
//            formLogin.failureForwardUrl("/login?error=true");
            // formLogin.defaultSuccessUrl("/home");
            formLogin.usernameParameter("email");
            formLogin.passwordParameter("password");
//               formlogin.failureHandler(new AuthenticationFailureHandler() {
//                   @Override
//                   public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
//
//                   }
//               });
//               formlogin.successHandler(new AuthenticationSuccessHandler() {
//                   @Override
//                   public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//
//                   }
//               });
       });
        httpSecurity.logout(logoutForm->{
                logoutForm.logoutUrl("/logout");
                logoutForm.logoutSuccessUrl("/login?logout=true");
        });

        // Oauth2 login configuration
        httpSecurity.oauth2Login(oauth->{
            oauth.loginPage("/login");
            oauth.successHandler(oAuthenticationSuccessHandler);
        });

         return httpSecurity.build();

    }

}
