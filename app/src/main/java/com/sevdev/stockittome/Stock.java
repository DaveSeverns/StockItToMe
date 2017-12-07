package com.sevdev.stockittome;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by davidseverns on 12/3/17.
 */

public class Stock implements Serializable{

    //keys for JSON object
    public final String SYMBOL_KEY = "Symbol", COMPANY_KEY = "Name", PRICE_KEY = "LastPrice";

    private String stockSymbol;
    private String companyName;
    private double currentPrice;


    public Stock(){

    }

    public Stock(String stockSymbol, String companyName, double currentPrice){
        this.stockSymbol = stockSymbol;
        this.companyName = companyName;
        this.currentPrice = currentPrice;
    }

    public Stock(JSONObject jsonObject) throws JSONException{


            stockSymbol = jsonObject.getString(SYMBOL_KEY);
            companyName = jsonObject.getString(COMPANY_KEY);
            currentPrice = jsonObject.getDouble(PRICE_KEY);


    }


    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }



}
