package com.gkatzioura.spring.aop.model;

import java.util.List;

/**
 * Created by gkatzioura on 5/28/16.
 */
public class Sample {
	//{"name":"yogi aditya", "value":true, address:["bad","red","fan"]}
    private String name;
    private Boolean value;
    private List<String> address;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getValue() {
		return value;
	}
	public void setValue(Boolean value) {
		this.value = value;
	}
	public List<String> getAddress() {
		return address;
	}
	public void setAddress(List<String> address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "Sample [name=" + name + ", value=" + value + ", address=" + address + "]";
	}

    
}
