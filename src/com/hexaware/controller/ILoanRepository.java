package com.hexaware.controller;

import com.hexaware.model.*;
import com.hexaware.exception.*;
import java.util.List;

public interface ILoanRepository {
	void applyLoan(Loan loan);

    double calculateInterest() throws InvalidLoanException;

    double calculateInterest(double principalAmount, double interestRate, int loanTerm);

    void loanStatus(int loanId);

    double calculateEMI(int loanId) throws InvalidLoanException;

    double calculateEMI(double principalAmount, double interestRate, int loanTerm);

    void loanRepayment(int loanId, double amount);

    public List<Loan> getAllLoan();

    void getLoanById(int loanId) throws InvalidLoanException;
}
