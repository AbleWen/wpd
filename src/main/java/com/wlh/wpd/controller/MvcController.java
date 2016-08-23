/**
 * 
 */
package com.wlh.wpd.controller;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wlh.wpd.model.Person;

/**
 * @author wlh
 *
 */
@Controller
@RequestMapping("/mvc")
public class MvcController {

	@RequestMapping("/hello")
    public String hello(){   
		System.out.println("hello");
        return "hello";
    }
	
	
	@RequestMapping("/person")
    public String toPerson(String name,double age){
        System.out.println(name+" - "+age);
        return "person";
    }
	
	
	@RequestMapping("/person1")
    public String toPerson(Person p){
        System.out.println(p.getName()+" - "+p.getAge());
        return "hello";
    }
	
	
	@RequestMapping("/date")
    public String date(Date date){
        System.out.println(date);
        return "hello";
    }
    //At the time of initialization,convert the type "String" to type "date"
    @InitBinder
    public void initBinder(ServletRequestDataBinder binder){
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"),
                true));
    }
    
    
    //pass the parameters to front-end
    @RequestMapping("/show")
    public String showPerson(Map<String,Object> map){
        Person p =new Person();
        map.put("p", p);
        p.setAge(20);
        p.setName("jayjay");
        return "show";
    }
    
    
    //pass the parameters to front-end using ajax
    @RequestMapping("/getPerson")
    public void getPerson(String name,PrintWriter pw){
        pw.write("hello,"+name);        
    }
    @RequestMapping("/name")
    public String sayHello(){
        return "name";
    }
    
    
    //redirect 
    @RequestMapping("/redirect")
    public String redirect(){
    	System.out.println("in redirect");
        return "redirect:hello";
    }
    
    
    @RequestMapping(value="/param")
    public String testRequestParam(@RequestParam(value="id") Integer idd,
            @RequestParam(value="name")String namee){
        System.out.println(idd+" "+namee);
        return "/hello";
    }
    
    
    
    
//    @ExceptionHandler
//    public ModelAndView exceptionHandler(Exception ex){
//        ModelAndView mv = new ModelAndView("error");
//        mv.addObject("exception", ex);
//        System.out.println("in testExceptionHandler");
//        return mv;
//    }
    
    @RequestMapping("/error")
    public String error(){
        int i = 5/0;
        return "hello";
    }
}
