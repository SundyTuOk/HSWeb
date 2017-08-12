package com.sf.service.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {
	
	String name;
	int age;
	String email;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public User() {
		super();
	}
	public User(String name, int age, String email) {
		super();
		this.name = name;
		this.age = age;
		this.email = email;
	}
	@Override
	public String toString() {
		return "User [name=" + name + ", age=" + age + ", email=" + email + "]";
	}
	
}
