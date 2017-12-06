package com.sevdev.stockittome;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by davidseverns on 12/3/17.
 */

public class StockService extends Service {

    private final String PORTFOLIO_FILE_NAME = "portfolioFile.ser";

    private Stock stock;
    private HashMap<String, Stock> stockHashMap = new HashMap<>();

    private final IBinder mBinder = new LocalBinder();


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class LocalBinder extends Binder{
        StockService getService(){
            return StockService.this;
        }
    }

    public void getStockInfo(final String symbol){
        Thread t = new Thread() {
            @Override
            public void run() {
                URL stockJSONURL;

                try {
                    stockJSONURL = new URL
                            ("http://dev.markitondemand.com/MODApis/Api/v2/Quote/json/?symbol=" + symbol);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stockJSONURL.openStream()));
                    String tempResponse, response = "";

                    tempResponse = bufferedReader.readLine();
                    while (tempResponse != null) {
                        response = response + tempResponse;
                        tempResponse = bufferedReader.readLine();
                    }

                    JSONObject stockObject = new JSONObject(response);
                    stock = new Stock(stockObject);
                    saveStockToFile(stock);
                    Log.e("Stock data to save :", stock.getCompanyName() + " " + stock.getCurrentPrice());
                } catch (Exception e) {
                    Log.d("Error", "Error grabbing stock");
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }










    public void saveStockToFile(final Stock stock){

        Thread t = new Thread(){
            @Override
            public void run() {
                try {

                    stockHashMap.put(stock.getStockSymbol(),stock);

                    FileOutputStream fos = null;
                    fos = openFileOutput(PORTFOLIO_FILE_NAME, Context.MODE_PRIVATE);

                    String path = getFilesDir().getAbsolutePath();
                    Log.e("path", path);
                    try {

                        ObjectOutputStream oos = null;
                        oos = new ObjectOutputStream(fos);
                        oos.writeObject(stockHashMap);

                        oos.close();
                    } catch (IOException e) {
                        Log.e("Failed at", "io on the object writer");
                        e.printStackTrace();
                    }
                    fos.close();

                } catch (FileNotFoundException e) {
                    //Log.e("Failed at", "File not found");
                    Log.e("FNFE", "in save block");
                    e.printStackTrace();
                } catch (IOException e) {
                    // Log.e("Failed at", "io number 2");
                    e.printStackTrace();
                }
            }
        };t.start();

    }

}


