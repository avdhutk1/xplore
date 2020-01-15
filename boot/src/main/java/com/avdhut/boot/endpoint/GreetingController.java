package com.avdhut.boot.endpoint;

import com.avdhut.boot.config.ModuleConfig;
import com.avdhut.boot.domain.Greeting;
import com.avdhut.boot.service.GreetingService;
import com.avdhut.boot.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/services/greeting")
public class GreetingController {

    @Autowired
    private GreetingService greetingService;

    //injected not by @component but by calling the bean method in the ModuleConfig class
    @Autowired
    private ModuleService modservice;

    /*
    The @GetMapping annotation is new and preferred to the old method which is also given below
    It has the same params as RequestMapping but more succint by also specifying the method
    There are also annotations for each http method as @PostMapping, @PutMapping, etc
     */
    //@RequestMapping(method = RequestMethod.GET, path="/greet")
    @GetMapping("/greet")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World")  String name){

        System.out.println(modservice.getMessage());

        return greetingService.greet(name);

    }

}
