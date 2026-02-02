package com.Natlav.QuizApp.security;

import com.Natlav.QuizApp.entities.User;
import com.Natlav.QuizApp.enums.RoleType;
import com.Natlav.QuizApp.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UsersRepository usersRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User auth2User = super.loadUser(userRequest);
        System.out.println("GOOGLE ATTRIBUTES: " + auth2User.getAttributes());

        System.out.println("OAUTH PARAMS: " + userRequest.getAdditionalParameters());
        String email = auth2User.getAttribute("email");
        String name = auth2User.getAttribute("name");

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        String selectedRole = (String) attr.getRequest().getSession().getAttribute("ROLE");
        System.out.println(selectedRole);
        RoleType roleType = "GAMEMASTER".equals(selectedRole)
                ? RoleType.ROLE_GAMEMASTER
                : RoleType.ROLE_PLAYER;


        if (email == null) {
            throw new OAuth2AuthenticationException("Email not found from Google");
        }

        User user = usersRepository.findByEmail(email).orElseGet(()->{
            User newUser = new User();
            newUser.setActive(true);
            newUser.setUsername(name);
            newUser.setEmail(email);
            newUser.setRole(roleType);
            newUser.setProvider("google");
            return  usersRepository.save(newUser);
        });


        return new DefaultOAuth2User( List.of(new SimpleGrantedAuthority(user.getRole().name())), auth2User.getAttributes(), "email");
    }


}
