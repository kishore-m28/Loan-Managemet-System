package com.hexaware.model;

public class Customer {
	private int customerId;
	private String name;
	private String email;
	private String phoneNumber;
	private String address;
	private int creditScore;
	
	public Customer() {
    }
	
	public Customer(int customerId, String name, String email, String phoneNumber, String address, int creditScore) {
		super();
		this.customerId = customerId;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.creditScore = creditScore;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerID) {
		this.customerId = customerID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getCreditScore() {
		return creditScore;
	}

	public void setCreditScore(int creditScore) {
		this.creditScore = creditScore;
	}

	public void printCustomerInfo() {
		// TODO Auto-generated method stub
		System.out.println("Customer ID: " + customerId);
        System.out.println("Name: " + name);
        System.out.println("Email Address: " + email);
        System.out.println("Phone Number: " + phoneNumber);
        System.out.println("Address: " + address);
        System.out.println("Credit Score: " + creditScore);
	}

	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", name=" + name + ", email=" + email + ", phoneNumber="
				+ phoneNumber + ", address=" + address + ", creditScore=" + creditScore + "]";
	}
	
}
