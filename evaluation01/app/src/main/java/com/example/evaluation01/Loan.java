package com.example.evaluation01;

import java.io.Serializable;

public class Loan implements Serializable {

    private String loanType;
    private double loanAmount;
    private double downPayment;
    private int loanTerm;
    private double interestRate;

    public Loan(String loanType, double loanAmount,
                double downPayment, int loanTerm,
                double interestRate) {

        this.loanType = loanType;
        this.loanAmount = loanAmount;
        this.downPayment = downPayment;
        this.loanTerm = loanTerm;
        this.interestRate = interestRate;
    }

    public String getLoanType() {
        return loanType;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public double getDownPayment() {
        return downPayment;
    }

    public int getLoanTerm() {
        return loanTerm;
    }

    public double getInterestRate() {
        return interestRate;
    }
}
