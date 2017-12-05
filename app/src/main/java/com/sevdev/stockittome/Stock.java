package com.sevdev.stockittome;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by davidseverns on 12/3/17.
 */

public class Stock{

    //keys for JSON object
    public final String SYMBOL_KEY = "Symbol", COMPANY_KEY = "Name", PRICE_KEY = "LastPrice";

    private String stockSymbol;
    private String companyName;
    private String currentPrice;
    private JSONObject jsonObject;

    public Stock(){

    }

    public Stock(String stockSymbol, String companyName, String currentPrice){
        this.stockSymbol = stockSymbol;
        this.companyName = companyName;
        this.currentPrice = currentPrice;
    }

    public Stock(String JSONStockInfo){
        try {
            jsonObject = new JSONObject(JSONStockInfo);
            stockSymbol = jsonObject.getString(SYMBOL_KEY);
            companyName = jsonObject.getString(COMPANY_KEY);
            currentPrice = jsonObject.getString(PRICE_KEY);
           // jsonObject.put();
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    public String getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice;
    }
}
