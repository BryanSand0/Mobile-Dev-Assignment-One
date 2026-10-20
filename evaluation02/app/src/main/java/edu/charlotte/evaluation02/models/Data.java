package edu.charlotte.evaluation02.models;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

import edu.charlotte.evaluation02.R;

public class Data {

    public static class LoanType implements Serializable {
        String name, description;

        public LoanType() {
        }

        public LoanType(String name, String description) {
            this.name = name;
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    public static ArrayList<LoanType> getLoanTypes() {
        ArrayList<LoanType> loanTypes = new ArrayList<>();
        loanTypes.add(new LoanType("Home Loan", "For purchasing or refinancing residential property. Typically offers lower rates and longer terms."));
        loanTypes.add(new LoanType("Auto Loan", "For financing a new or used vehicle purchase. Shorter terms with moderate rates."));
        loanTypes.add(new LoanType("Personal Loan", "Unsecured loan for general use. Higher rates, flexible purpose."));
        loanTypes.add(new LoanType("Other Loan", "For any loan type not covered above. Terms and rates vary based on the lender and purpose."));
        return loanTypes;
    }

    public static class LoanTerm implements Serializable {
        String name, description;
        int image_res_id;
        int years;

        public LoanTerm() {
        }

        public LoanTerm(String name, String description, int image_res_id, int years) {
            this.name = name;
            this.description = description;
            this.image_res_id = image_res_id;
            this.years = years;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getImage_res_id() {
            return image_res_id;
        }

        public void setImage_res_id(int image_res_id) {
            this.image_res_id = image_res_id;
        }

        public int getYears() {
            return years;
        }

        public void setYears(int years) {
            this.years = years;
        }
    }

    public static ArrayList<LoanTerm> getLoanTerms() {
        ArrayList<LoanTerm> loanTerms = new ArrayList<>();
        loanTerms.add(new LoanTerm("10 Years", "Short commitment, high monthly payments but significantly less interest paid overall. Ideal for those who can afford larger installments.", R.drawable.money_bags_1, 10));
        loanTerms.add(new LoanTerm("15 Years", "A balanced short-to-mid commitment. Moderate payments with substantial interest savings compared to longer terms.", R.drawable.money_bags_2, 15));
        loanTerms.add(new LoanTerm("20 Years", "A moderate commitment offering a middle ground between payment size and total interest paid.", R.drawable.money_bags_3, 20));
        loanTerms.add(new LoanTerm("25 Years", "A longer commitment with lower monthly payments. More interest paid over time but greater monthly flexibility.", R.drawable.money_bags_4, 25));
        loanTerms.add(new LoanTerm("30 Years", "Maximum term, lowest monthly payments but the highest total interest paid. Best for maximizing monthly cash flow.", R.drawable.money_bags_5, 30));
        return loanTerms;
    }
}