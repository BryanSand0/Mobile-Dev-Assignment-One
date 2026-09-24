# Implementation Plan - Loan Calculator Logic

This plan outlines the steps to implement the core functionality of the Loan Calculator, including the data flow between activities using `ActivityResultLauncher`, input validation, and result calculation.

## User Review Required

> [!IMPORTANT]
> - The `DownPaymentActivity` SeekBar will be set to a range of 0-99 as per the instructions.
> - `LoanTermActivity` will return an `int` (10, 15, 20, 25, or 30).
> - `LoanInterestActivity` will enforce a valid range of 0.1 to 30.0.

## Proposed Changes

### [Resources]

#### [MODIFY] [strings.xml](file:///C:/Users/Luke/AndroidStudioProjects/evaluation01/app/src/main/res/values/strings.xml)
- Add strings for activity titles, button labels (Cancel, Submit, Close), loan type options (Home Loan, Auto Loan, Personal Loan), and validation messages.

### [Main Activity]

#### [MODIFY] [MainActivity.java](file:///C:/Users/Luke/AndroidStudioProjects/evaluation01/app/src/main/java/com/example/evaluation01/MainActivity.java)
- Define variables to store selected loan data.
- Implement 5 `ActivityResultLauncher<Intent>` instances for:
    - Loan Type (String)
    - Loan Amount (double)
    - Down Payment % (double)
    - Loan Term (int)
    - Interest Rate (double)
- Update button listeners to launch activities using these launchers.
- Implement `Submit` logic: validate all fields are present, create `Loan` object, and start `LoanDetailsActivity`.
- Update `Reset` logic to clear both UI and stored variables.

### [Selection Activities]

#### [MODIFY] [LoanTypeActivity.java](file:///C:/Users/Luke/AndroidStudioProjects/evaluation01/app/src/main/java/com/example/evaluation01/LoanTypeActivity.java) and [activity_loan_type.xml](file:///C:/Users/Luke/AndroidStudioProjects/evaluation01/app/src/main/res/layout/activity_loan_type.xml)
- Update layout to use string resources.
- Return selected loan type via `setResult`.

#### [MODIFY] [LoanAmountActivity.java](file:///C:/Users/Luke/AndroidStudioProjects/evaluation01/app/src/main/java/com/example/evaluation01/LoanAmountActivity.java) and [activity_loan_amount.xml](file:///C:/Users/Luke/AndroidStudioProjects/evaluation01/app/src/main/res/layout/activity_loan_amount.xml)
- Add validation for numeric input > 0.
- Return amount via `setResult`.

#### [MODIFY] [DownPaymentActivity.java](file:///C:/Users/Luke/AndroidStudioProjects/evaluation01/app/src/main/java/com/example/evaluation01/DownPaymentActivity.java) and [activity_down_payment.xml](file:///C:/Users/Luke/AndroidStudioProjects/evaluation01/app/src/main/res/layout/activity_down_payment.xml)
- Set SeekBar max to 99.
- Update value display in real-time.
- Return percentage via `setResult`.

#### [MODIFY] [LoanTermActivity.java](file:///C:/Users/Luke/AndroidStudioProjects/evaluation01/app/src/main/java/com/example/evaluation01/LoanTermActivity.java) and [activity_loan_term.xml](file:///C:/Users/Luke/AndroidStudioProjects/evaluation01/app/src/main/res/layout/activity_loan_term.xml)
- Wire the five term buttons to return the corresponding integer value.

#### [MODIFY] [LoanInterestActivity.java](file:///C:/Users/Luke/AndroidStudioProjects/evaluation01/app/src/main/java/com/example/evaluation01/LoanInterestActivity.java) and [activity_loan_interest.xml](file:///C:/Users/Luke/AndroidStudioProjects/evaluation01/app/src/main/res/layout/activity_loan_interest.xml)
- Add validation for range 0.1 - 30.0.
- Return interest rate via `setResult`.

### [Results]

#### [MODIFY] [LoanDetailsActivity.java](file:///C:/Users/Luke/AndroidStudioProjects/evaluation01/app/src/main/java/com/example/evaluation01/LoanDetailsActivity.java)
- Receive the `Loan` object.
- Calculate Principal, Monthly Payment, Total Payment, and Total Interest.
- Display all details in the summary view.

## Verification Plan

### Manual Verification
- Launch each selection activity and verify that the selected value is correctly returned and displayed on `MainActivity`.
- Test the `Reset` button to ensure all values are cleared.
- Test `Submit` with incomplete data (should show Toast).
- Test `Submit` with complete data and verify the calculations in `LoanDetailsActivity`.
- Verify range validations in Amount, Down Payment, and Interest activities.
