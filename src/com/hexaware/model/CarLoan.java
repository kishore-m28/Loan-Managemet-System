package com.hexaware.model;

public class CarLoan extends Loan{
	String carModel;
	int carValue;
	
	public CarLoan() {
    }
	
	public CarLoan(int loanId, Customer customer, double principalAmount, double interestRate, int loanTerm, String loanStatus,
            String carModel, int carValue) {
		super(loanId, customer, principalAmount, interestRate, loanTerm, "CarLoan", loanStatus);
		this.carModel = carModel;
		this.carValue = carValue;
	}
	public String getCarModel() {
		return carModel;
	}
	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}
	public int getCarValue() {
		return carValue;
	}
	public void setCarValue(int carValue) {
		this.carValue = carValue;
	}
	public void printCarLoanInfo() {
        printLoanInfo();
        System.out.println("Car Model: " + carModel);
        System.out.println("Car Value: " + carValue);
    }

	@Override
	public String toString() {
		return "CarLoan [carModel=" + carModel + ", carValue=" + carValue + "]";
	}
	
}
