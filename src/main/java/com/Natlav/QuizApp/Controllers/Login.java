package com.Natlav.QuizApp.Controllers;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class Login {

    @GetMapping("/login/google")
    public String setRole(@RequestParam String role, HttpSession session) {
        session.setAttribute("ROLE", role);
        return "redirect:/oauth2/authorization/google";
    }


}
