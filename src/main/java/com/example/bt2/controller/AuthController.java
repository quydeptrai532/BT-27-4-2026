package com.example.bt2.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @GetMapping("/welcome")
    public String showWelcomePage() {
        return "welcome";
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam("ownerName") String ownerName, HttpSession session) {
        if (ownerName == null || ownerName.trim().isEmpty()) {
            return "redirect:/welcome?error";
        }
        session.setAttribute("ownerName", ownerName);
        return "redirect:/todos";
    }
}