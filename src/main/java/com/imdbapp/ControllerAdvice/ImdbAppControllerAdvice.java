package com.imdbapp.ControllerAdvice;


import com.imdbapp.DataModels.DatabaseModel.Users;
import com.imdbapp.Exceptions.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@ControllerAdvice
public class ImdbAppControllerAdvice {


    @ExceptionHandler(ApiCallFailedException.class)
    public String handleCallFailedException(Model model, ApiCallFailedException apiCallFailedException){
        final String msg = apiCallFailedException.msg;
        model.addAttribute("msg",msg);
        return "error";
    }
    @ExceptionHandler(UserFormValidationException.class)
    public String handleUserUserFormValidation(Model model, UserFormValidationException userFormValidationException){
        final List<String> msgList = userFormValidationException.errors;
        model.addAttribute("newUser",new Users());
        model.addAttribute("msgList",msgList);
        return "register";
    }
    @ExceptionHandler(UserDoesntExistException.class)
    public String handleUserDoesntExistException(Model model, UserDoesntExistException userDoesntExistException){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.setAuthenticated(false);
        return "redirect:/logout";
    }



}
