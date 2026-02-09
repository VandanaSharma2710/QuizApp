package com.Natlav.QuizApp.security;

import com.Natlav.QuizApp.entities.User;
import com.Natlav.QuizApp.enums.RoleType;
import com.Natlav.QuizApp.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UsersRepository usersRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest)
            throws OAuth2AuthenticationException {
        System.out.println("2 CustomOAuth2UserService");
        OAuth2User oauthUser = super.loadUser(userRequest);
        System.out.println("GOOGLE ATTRIBUTES: " + oauthUser.getAttributes());

        String email = oauthUser.getAttribute("email");
        String name = oauthUser.getAttribute("name");

        System.out.println("email=" + email + " name=" + name);

        if (email == null) {
            throw new OAuth2AuthenticationException("Email not found from Google");
        }
        return oauthUser;
    }
}



