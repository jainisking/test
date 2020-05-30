package com.gkatzioura.spring.aop.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gkatzioura.spring.aop.model.Sample;

/**
 * Created by gkatzioura on 5/28/16.
 */
@Service
public class SampleService {

    public Sample createSample(String sampleName) {

        Sample sample = new Sample();
        sample.setName(sampleName);
        
        List<String> address=new ArrayList<>();
        address.add("santosh");
        address.add("deepesh");
        sample.setAddress(address);
        return sample;
    }
}
