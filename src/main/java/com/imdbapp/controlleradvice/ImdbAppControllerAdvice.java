package com.imdbapp.controlleradvice;


import com.imdbapp.exceptions.ApiCallFailedException;
import com.imdbapp.exceptions.NoCheckBoxSelectionException;
import com.imdbapp.exceptions.UserDoesntExistException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ImdbAppControllerAdvice {

    @ExceptionHandler(NoCheckBoxSelectionException.class)
    public String handleNoCheckBoxSelectionException(Model model, NoCheckBoxSelectionException noCheckBoxSelectionException) {
        final String msg = noCheckBoxSelectionException.msg;
        model.addAttribute("msg", msg);
        return "search";
    }

    @ExceptionHandler(ApiCallFailedException.class)
    public String handleCallFailedException(Model model, ApiCallFailedException apiCallFailedException) {
        final String msg = apiCallFailedException.msg;
        model.addAttribute("msg", msg);
        return "error";
    }

    @ExceptionHandler(UserDoesntExistException.class)
    public String handleUserDoesntExistException() {
        return "redirect:/logout";
    }


}
