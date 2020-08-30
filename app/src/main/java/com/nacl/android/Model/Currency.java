package com.nacl.android.Model;

import com.nacl.android.Helper.CurrencyHelper;

public class Currency implements CurrencyHelper {

    private String price;
    private String price_symbol;
    private Integer currency;
    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice() {
        return price;
    }
    public String getPriceWithCurrencySymbol(){
        return price_symbol;
    }
    public void setCurrency(int currency){
        switch (currency){
            case 1:
                this.currency = Currency.USD;
                price_symbol = "$" + price;
                break;
            case 2:
                this.currency = Currency.INR;
                price_symbol = "₹" + price;
                break;
        }
    }

    public Integer getCurrency() {
        return currency;
    }
}
