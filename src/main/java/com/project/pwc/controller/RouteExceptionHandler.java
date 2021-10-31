package com.project.pwc.controller;

import com.project.pwc.exception.RouteNotFoundException;
import com.project.pwc.model.exception.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RouteExceptionHandler {

    @ExceptionHandler(RouteNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessage routeNotFoundError(RouteNotFoundException ex) {
        ErrorMessage result = new ErrorMessage(HttpStatus.BAD_REQUEST.toString(), ex.getMessage());
        return result;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorMessage routeNotFoundError500(RouteNotFoundException ex) {
        ErrorMessage result = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.toString(), ex.getMessage());
        return result;
    }

}
