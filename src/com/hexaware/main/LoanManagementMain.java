package com.hexaware.main;

import com.hexaware.controller.*;
import com.hexaware.controller.ILoanRepositoryImpl;
import com.hexaware.exception.*;
import com.hexaware.model.Loan;

import java.util.*;

public class LoanManagementMain {

	private static ILoanRepositoryImpl loanRepository = new ILoanRepositoryImpl();
	private static Loan loan = new Loan();
    public static void main(String[] args) throws InvalidLoanException {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nLoan Management System Menu:");
            System.out.println("1. Add Customer");
            System.out.println("2. Apply for a loan");
            System.out.println("3. Calculate interest for a loan");
            System.out.println("4. Check loan status");
            System.out.println("5. Calculate EMI for a loan");
            System.out.println("6. Repay loan");
            System.out.println("7. View all loans");
            System.out.println("8. View loan by ID");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
            	case 1:
            		System.out.println("Enter Customer Id:");
                    int cId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Enter Customer Name:");
                    String name=scanner.next();
                    System.out.println("Enter Customer Email:");
                    String email=scanner.next();
                    System.out.println("Enter Customer Phone Number:");
                    String phNumber=scanner.next();
                    System.out.println("Enter Customer Address:");
                    String address=scanner.next();
                    System.out.println("Enter Credit Score:");
                    int cScore = scanner.nextInt();
                    
                    
                    loanRepository.insertCustomer(cId,name,email,phNumber,address,cScore);
                    break;
            	
            	case 2:
                    System.out.println("Enter Loan Id:");
                    int loanId = scanner.nextInt();
                    System.out.println("Enter Customer Id:");
                    int customerId = scanner.nextInt();
                    System.out.println("Enter Principal Amount:");
                    double principalAmount = scanner.nextDouble();
                    System.out.println("Enter Interest Rate:");
                    double interestRate = scanner.nextDouble();
                    System.out.println("Enter Loan Term:");
                    int loanTerm = scanner.nextInt();
                    System.out.println("Enter Loan Type:");
                    String loanType = scanner.next();
                    System.out.println("Enter Loan Status:");
                    String loanStatus = scanner.next();

                    Loan loan = new Loan(loanId, customerId, principalAmount, interestRate, loanTerm, loanType, loanStatus);
                    loanRepository.applyLoan(loan);
                    break;
                case 3:
                	System.out.println(loanRepository.calculateInterest());
                    break;
                case 4:
                	System.out.println("Enter Loan Id:");
                	int loanId1=scanner.nextInt();
                	loanRepository.loanStatus(loanId1);
                    break;
                case 5:
                	System.out.println("Enter Loan Id:");
                	int loanId2=scanner.nextInt();
                	System.out.println(loanRepository.calculateEMI(loanId2));
                    break;
                case 6:
                	System.out.println("Enter Loan Id:");
                	int loanId3=scanner.nextInt();
                	System.out.println("Enter Amount:");
                	double amt=scanner.nextInt();
                	loanRepository.loanRepayment(loanId3,amt);
                    break;
                case 7:
                	loanRepository.getAllLoan();
                    break;
                case 8:
                	System.out.println("Enter Loan Id:");
                	int loanId4=scanner.nextInt();
                	loanRepository.getLoanById(loanId4);
                    break;
                case 9:
                    System.out.println("Exiting Loan Management System...");
                    System.out.println("EXITED !!!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        } while (choice != 8);

        scanner.close();
    }
	
}
