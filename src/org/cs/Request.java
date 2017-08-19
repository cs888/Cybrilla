package org.cs;

import java.text.SimpleDateFormat;

public class Request {

    public static final String DATE_FORMAT = "yyyy-MM-dd";
    private String bank_ifsc_code;
    private String bank_account_number;
    private double amount;
    private String merchant_transaction_ref;
    private String transaction_date;
    private String payment_gateway_merchant_reference;
    private String hash;

    public Request(String bank_ifsc_code, String bank_account_number, double amount, String merchant_transaction_ref,
            String transaction_date, String payment_gateway_merchant_reference) {

        this.bank_ifsc_code = bank_ifsc_code;
        this.bank_account_number = bank_account_number;
        this.amount = amount;
        this.merchant_transaction_ref = merchant_transaction_ref;
        this.transaction_date = getDate(transaction_date);
        this.payment_gateway_merchant_reference = payment_gateway_merchant_reference;
        this.hash = null;
    }

    public Request() {
        // TODO Auto-generated constructor stub
    }

    private String getDate(String dateStr) {

        if (dateStr == null)
            return null;

        try {
            return new SimpleDateFormat(DATE_FORMAT).parse(dateStr).toString();
        } catch (Exception e) {
            System.out.println("Error during date_formatting");
            return null;
        }

    }
    
      String hashAPI(Request request) {
        String result=""+request.bank_ifsc_code.trim().hashCode()+
                        request.bank_account_number.trim().hashCode()+
                        request.amount+
                        request.merchant_transaction_ref.trim().hashCode()+
                        request.transaction_date.trim().hashCode()+
                        request.payment_gateway_merchant_reference.trim().hashCode();
                        
        
        return result;
    
        
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String string) {
        this.hash = string;
    }
}
