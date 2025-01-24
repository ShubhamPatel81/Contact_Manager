package com.example.Contact_manager_web.config;

import com.example.Contact_manager_web.entities.Providers;
import com.example.Contact_manager_web.entities.User;
import com.example.Contact_manager_web.helper.AppConstants;
import com.example.Contact_manager_web.repositories.UserRepo;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
public class OAuthenticationSuccessHandler  implements AuthenticationSuccessHandler {

    @Autowired
    private UserRepo userRepo;
    Logger logger = LoggerFactory.getLogger(OAuthenticationSuccessHandler.class);
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {


           logger.info("OAuthenticationSuccessHandler");

//           Identify provider
            var oAuth2AuthenticationToken =(OAuth2AuthenticationToken)authentication;
            String autorizedClientRegestrationId= oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
            logger.info(autorizedClientRegestrationId);

        DefaultOAuth2User oAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
        oAuth2User.getAttributes().forEach((key, value)->{
            logger.info( key +" : "+ value );
        });

        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setRoleList(List.of(AppConstants.ROLE_USER));
        user.setEmailVerified(true);
        user.setEnabled(true);
        user.setPassword("PASSWORD");

            if (autorizedClientRegestrationId.equalsIgnoreCase("google")){
                
//                google login
                user.setEmail(oAuth2User.getAttribute("email").toString());
                user.setProfilePic(oAuth2User.getAttribute("picture").toString());
                user.setName(oAuth2User.getAttribute("name"));
                user.setProvideId(oAuth2User.getName());
                user.setProvider(Providers.GOOGLE);
                user.setAbout("This account is created by Google");
                
            } else if (autorizedClientRegestrationId.equalsIgnoreCase("github")) {
//                github login
                String email = oAuth2User.getAttribute("email")!= null ?
                        oAuth2User.getAttribute("email").toString():oAuth2User.getAttribute("login").toString()+"@gmail.com";
                String picture  = oAuth2User.getAttribute("avatar_url").toString();
                String name = oAuth2User.getAttribute("login").toString();
                String providerUserId = oAuth2User.getName();

                user.setEmail(email);
                user.setProfilePic(picture);
                user.setName(name);
                user.setProvideId(providerUserId);
                user.setProvider(Providers.GITHUB);
                user.setAbout("This account is created by GitHub");
            }


            else if (autorizedClientRegestrationId.equalsIgnoreCase("faccebook")){
//             facebook login

                //find the user and save the user

            }else {

            }


        /*
          DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();
//          logger.info(user.getName());
//          user.getAttributes().forEach((key,value)->{
//              logger.info("{} => {}",key,value);
//          });
//          logger.info(user.getAuthorities().toString());


            String email = user.getAttribute("email").toString();
            String name = user.getAttribute("name").toString();
            String picture = user.getAttribute("picture").toString();

            //save user into database
            User user1 = new User();
            user1.setName(name);
            user1.setEmail(email);
            user1.setProfilePic(picture);
            user1.setPassword("PASSWORD");
            user1.setUserId(UUID.randomUUID().toString());
            user1.setProvider(Providers.GOOGLE);
            user1.setEnabled(true);
            user1.setEmailVerified(true);
            user1.setProvideId(user.getName());
            user1.setRoleList(List.of(
                    AppConstants.ROLE_USER
            ));

            user1.setAbout("This account is Created Using Google");




         */

        User user2 = userRepo.findByEmail(user.getEmail()).orElse(null);
        if (user2==null){
            userRepo.save(user);
//                logger.info("USER SAVED "+email);
        }


        new DefaultRedirectStrategy().sendRedirect(request,response, "/user/profile");

    }
}
