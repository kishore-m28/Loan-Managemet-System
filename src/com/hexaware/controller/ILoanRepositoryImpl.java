package com.hexaware.controller;

import java.util.ArrayList;
import java.util.List;

//import dao.ILoanRepository;
import com.hexaware.model.Loan;
import com.hexaware.exception.*;
import com.hexaware.util.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ILoanRepositoryImpl implements ILoanRepository {
	Scanner sc=new Scanner(System.in);
	
	public void insertCustomer(int customerId, String name, String email,String phNumber,String address,int cScore) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DatabaseConnection.getDBConn();
            String sql = "INSERT INTO customers (customer_id, name, email_address,phone_number,address,credit_score) VALUES (?, ?, ?,?,?,?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, customerId);
            stmt.setString(2, name);
            stmt.setString(3, email);
            stmt.setString(4, phNumber);
            stmt.setString(5, name);
            stmt.setInt(6, cScore);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 1) {
                System.out.println("Customer inserted successfully.");
            } else {
                System.out.println("Failed to insert customer.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void applyLoan(Loan loan) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DatabaseConnection.getDBConn();
            String sql = "INSERT INTO loans (loan_id, customer_id, principal_amount, interest_rate, loan_term, loan_type, loan_status) VALUES (?, ?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, loan.getLoanId());
            stmt.setInt(2, loan.getCustomerId());
            stmt.setDouble(3, loan.getPrincipalAmount());
            stmt.setDouble(4, loan.getInterestRate());
            stmt.setInt(5, loan.getLoanTerm());
            stmt.setString(6, loan.getLoanType());
            stmt.setString(7, loan.getLoanStatus());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 1) {
                System.out.println("Loan application successful.");
            } else {
                System.out.println("Loan application failed.");
            }
        } catch (SQLException e) {
            System.out.println("Error while applying for loan: " + e.getMessage());
        } finally {
            // Close resources in a finally block
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error while closing resources: " + ex.getMessage());
            }
        }
    }
    public double calculateInterest() throws InvalidLoanException{
    	System.out.println("Enter Loan Id");
    	int temp=sc.nextInt();
    	return calculateInterestFromDb(temp);
    }
    
    public double calculateInterestFromDb(int loanId) throws InvalidLoanException{
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DatabaseConnection.getDBConn();
            // SQL query to retrieve loan details
            String sql = "SELECT principal_amount, interest_rate, loan_term FROM loans WHERE loan_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, loanId);
            rs = stmt.executeQuery();
            if (rs.next()) {
                double principalAmount = rs.getDouble("principal_amount");
                double interestRate = rs.getDouble("interest_rate");
                int loanTerm = rs.getInt("loan_term");
                return (principalAmount * interestRate * loanTerm) / 12;
            } else {
                throw new InvalidLoanException("Loan not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

	@Override
	public double calculateInterest(double principalAmount, double interestRate, int loanTerm) {
		return (principalAmount * interestRate * loanTerm) / 12;
	}

	@Override
	public void loanStatus(int loanId) {
		Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    try {
	        conn = DatabaseConnection.getDBConn();
	        // Query to retrieve loan details including customer's credit score
	        String sql = "SELECT l.loan_id, c.credit_score " +
	                     "FROM loans l " +
	                     "INNER JOIN customers c ON l.customer_id = c.customer_id " +
	                     "WHERE l.loan_id = ?";
	        stmt = conn.prepareStatement(sql);
	        stmt.setInt(1, loanId);
	        rs = stmt.executeQuery();
	        if (rs.next()) {
	            int creditScore = rs.getInt("credit_score");
	            // Determine loan status based on credit score
	            String loanStatus = (creditScore > 650) ? "Approved" : "Rejected";
	            // Update loan status in the database
	            sql = "UPDATE loans SET loan_status = ? WHERE loan_id = ?";
	            stmt = conn.prepareStatement(sql);
	            stmt.setString(1, loanStatus);
	            stmt.setInt(2, loanId);
	            int rowsAffected = stmt.executeUpdate();
	            if (rowsAffected == 1) {
	                System.out.println("Loan Approved");
	            } else {
	                System.out.println("Loan Rejected");
	            }
	        } else {
	            System.out.println("Loan not found.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	@Override
	public double calculateEMI(int loanId) throws InvalidLoanException {
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    try {
	        conn = DatabaseConnection.getDBConn();
	        // Query to retrieve loan details
	        String sql = "SELECT principal_amount, interest_rate, loan_term FROM loans WHERE loan_id = ?";
	        stmt = conn.prepareStatement(sql);
	        stmt.setInt(1, loanId);
	        rs = stmt.executeQuery();
	        if (rs.next()) {
	            double principalAmount = rs.getDouble("principal_amount");
	            double interestRate = rs.getDouble("interest_rate");
	            int loanTerm = rs.getInt("loan_term");
	            // Calculate monthly interest rate
	            double monthlyInterestRate = interestRate / 12.0 / 100.0;
	            // Calculate EMI using the provided formula
	            double emi = (principalAmount * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, loanTerm))
	                         / (Math.pow(1 + monthlyInterestRate, loanTerm) - 1);
	            return emi;
	        } else {
	            throw new InvalidLoanException("Loan not found.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return 0; // Or throw another exception
	    }
	}

	@Override
	public double calculateEMI(double principalAmount, double interestRate, int loanTerm) {
		// Calculate monthly interest rate
	    double monthlyInterestRate = interestRate / 12.0 / 100.0;
	    // Calculate EMI using the provided formula
	    double emi = (principalAmount * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, loanTerm))
	                 / (Math.pow(1 + monthlyInterestRate, loanTerm) - 1);
	    return emi;
	}

	@Override
	public void loanRepayment(int loanId, double amount) {
		Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    try {
	        conn = DatabaseConnection.getDBConn();
	        // Query to retrieve loan details
	        String sql = "SELECT principal_amount, interest_rate, loan_term, loan_status FROM loans WHERE loan_id = ?";
	        stmt = conn.prepareStatement(sql);
	        stmt.setInt(1, loanId);
	        rs = stmt.executeQuery();
	        if (rs.next()) {
	            double principalAmount = rs.getDouble("principal_amount");
	            double interestRate = rs.getDouble("interest_rate");
	            int loanTerm = rs.getInt("loan_term");
	            String loanStatus = rs.getString("loan_status");
	            // Check if the loan status is pending or approved
	            if (!loanStatus.equals("Pending") && !loanStatus.equals("Approved")) {
	                System.out.println("Loan repayment cannot be processed. Loan status is not pending or approved.");
	                return;
	            }
	            // Calculate monthly interest rate
	            double monthlyInterestRate = interestRate / 12.0 / 100.0;
	            // Calculate EMI using the provided formula
	            double emi = (principalAmount * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, loanTerm))
	                         / (Math.pow(1 + monthlyInterestRate, loanTerm) - 1);
	            // Calculate the number of EMIs that can be paid from the given amount
	            int noOfEmis = (int) (amount / emi);
	            if (noOfEmis == 0 || amount < emi) {
	                System.out.println("Amount is insufficient to pay at least one EMI. Payment rejected.");
	                return;
	            }
	            // Update the loan repayment variable
	            double remainingAmount = amount - (noOfEmis * emi);
	            System.out.println("Number of EMIs paid: " + noOfEmis);
	            System.out.println("Remaining amount after payment: " + remainingAmount);
	            // TODO: Update the loan repayment variable in the database
	            System.out.println(remainingAmount);
	        } else {
	            System.out.println("Loan not found.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	@Override
	public List<Loan> getAllLoan() {
        List<Loan> loanList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DatabaseConnection.getDBConn();
            // Query to retrieve all loan details
            String sql = "SELECT * FROM loans";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            // Iterate through the result set and populate the loan list
            while (rs.next()) {
                int loanId = rs.getInt("loan_id");
                int customerId = rs.getInt("customer_id");
                double principalAmount = rs.getDouble("principal_amount");
                double interestRate = rs.getDouble("interest_rate");
                int loanTerm = rs.getInt("loan_term");
                String loanType = rs.getString("loan_type");
                String loanStatus = rs.getString("loan_status");
                // Create a new Loan object and add it to the loan list
                Loan loan = new Loan(loanId, customerId, principalAmount, interestRate, loanTerm, loanType, loanStatus);
                loanList.add(loan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Print the details of each loan
        for (Loan loan : loanList) {
            System.out.println(loan); // Assuming Loan class has overridden toString() method
        }
        return loanList;
    }

	@Override
	public void getLoanById(int loanId) throws InvalidLoanException {
		Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    try {
	        conn = DatabaseConnection.getDBConn();
	        // Query to retrieve loan details
	        String sql = "SELECT * FROM loans WHERE loan_id = ?";
	        stmt = conn.prepareStatement(sql);
	        stmt.setInt(1, loanId);
	        rs = stmt.executeQuery();
	        if (rs.next()) {
	            int customerId = rs.getInt("customer_id");
	            double principalAmount = rs.getDouble("principal_amount");
	            double interestRate = rs.getDouble("interest_rate");
	            int loanTerm = rs.getInt("loan_term");
	            String loanType = rs.getString("loan_type");
	            String loanStatus = rs.getString("loan_status");
	            // Print loan details
	            System.out.println("Loan Details:");
	            System.out.println("Loan ID: " + loanId);
	            System.out.println("Customer ID: " + customerId);
	            System.out.println("Principal Amount: " + principalAmount);
	            System.out.println("Interest Rate: " + interestRate);
	            System.out.println("Loan Term: " + loanTerm);
	            System.out.println("Loan Type: " + loanType);
	            System.out.println("Loan Status: " + loanStatus);
	        } else {
	            throw new InvalidLoanException("Loan not found.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
}

