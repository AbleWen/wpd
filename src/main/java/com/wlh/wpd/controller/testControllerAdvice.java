package com.wlh.wpd.controller;

import org.springframework.web.servlet.ModelAndView;

//@ControllerAdvice
public class testControllerAdvice {
//	@ExceptionHandler
    public ModelAndView exceptionHandler(Exception ex){
        ModelAndView mv = new ModelAndView("error");
        mv.addObject("exception", ex);
        System.out.println("in testControllerAdvice");
        return mv;
    }

}
