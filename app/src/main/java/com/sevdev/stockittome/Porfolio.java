package com.sevdev.stockittome;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by davidseverns on 12/4/17.
 */

public class Porfolio implements Serializable {

    private ArrayList<Stock> stocksList;

    public Porfolio() {
        super();
        stocksList = new ArrayList<>();
    }

    public void addStockToPortfolio(Stock stock){
        stocksList.add(stock);
    }

    public ArrayList<Stock> getStockPortfolioList(){
        return stocksList;
    }


    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
