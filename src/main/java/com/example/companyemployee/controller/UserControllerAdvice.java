package com.example.companyemployee.controller;

import com.example.companyemployee.entity.User;
import com.example.companyemployee.security.SpringUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class UserControllerAdvice {

    @ModelAttribute("currentUser")
    public User currentUser(@AuthenticationPrincipal SpringUser springUser) {
        if (springUser != null) {
            return springUser.getUser();
        }
        return null;
    }
}
