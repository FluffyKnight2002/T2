package com.studentRegistrationSpringBoot.cze.controllers;


import com.studentRegistrationSpringBoot.cze.Dto.UserDto;
import com.studentRegistrationSpringBoot.cze.appConfig.MyUserDetail;
import com.studentRegistrationSpringBoot.cze.dao.UserDao;
import com.studentRegistrationSpringBoot.cze.models.Role;
import com.studentRegistrationSpringBoot.cze.models.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class LoginController {

    @Autowired
    private LogoutHandler logoutHandler;


    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;



    @GetMapping("/login")
    public String showLoginForm() {
        return "Login";
    }

    @GetMapping("/register")
    public String register(){
        return "Register";
    }

    @PostMapping("/register")
    public String registerPost(UserDto userDto){
        User user=new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setRole(Role.USER);
//        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userDao.save(user);
        return "redirect:/login";
    }

    @GetMapping("/")
    public String home(@AuthenticationPrincipal MyUserDetail userDetails){
        int userId=userDetails.getUserId();
        return "User";
    }

//    @GetMapping("/logout")
//    public String logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
//        // Perform any custom actions here
//
//        // Invalidate the session
//        logoutHandler.logout(request, response, authentication);
//
//        // Redirect to the login page or any other desired page
//        return "redirect:/login?logout";
//    }
}

