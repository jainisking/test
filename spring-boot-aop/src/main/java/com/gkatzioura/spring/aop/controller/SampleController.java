package com.gkatzioura.spring.aop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gkatzioura.spring.aop.model.Sample;
import com.gkatzioura.spring.aop.service.SampleService;

/**
 * Created by gkatzioura on 5/28/16.
 */
@RestController
@RequestMapping("/sample")
public class SampleController {

    @Autowired
    private SampleService sampleService;
    
//    @GetMapping(path = "sampleAPI")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/sampleAPI")
    public Sample sample(@RequestBody(required = false) Sample userObject) {
    	System.out.println("inside of rest ====="+userObject);
    	return sampleService.createSample("disha");
    }
    
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/sampleAPI1")
    public Sample sample1(@RequestBody(required = false) Sample userObject, @RequestParam(required = false, name="name")String sampleName) {
    	System.out.println("inside of rest ====="+userObject);
    	return sampleService.createSample("disha");
    }
    
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/sampleAPI2")
    public Sample sample2(@RequestBody(required = false) List<Sample> userObject) {
    	System.out.println("inside of rest ====="+userObject);
    	return sampleService.createSample("disha");
    }
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/sampleAPI3")
    public Sample sample3(@RequestBody(required = false) List<Sample> userObject, @RequestParam(required = false, name="name")String sampleName) {
    	System.out.println("inside of rest ====="+userObject);
    	return sampleService.createSample("disha");
    }
    
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/sampleAPI4")
    public Sample sample4() {
    	System.out.println("inside of rest =====");
    	return sampleService.createSample("disha");
    }
    
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/sampleAPI5")
    public Sample sample5(@RequestParam(required = false, name="address")Boolean address, @RequestParam(required = false, name="name")String sampleName) {
    	System.out.println("inside of rest ====="+address+" "+sampleName);
    	return sampleService.createSample("disha");
    }
    /*@GetMapping(path = "sampleAPI")
    public Sample sample(@RequestParam(required = false, name="name")String sampleName) {
    	System.out.println("inside of rest");
    	return sampleService.createSample(sampleName);
    }*/
}
