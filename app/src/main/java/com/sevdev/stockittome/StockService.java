package com.sevdev.stockittome;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by davidseverns on 12/3/17.
 */

public class StockService extends Service {




    private IOHelper ioHelper;
    private ServiceThread serviceThread;

    private final IBinder mBinder = new LocalBinder();


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        ioHelper = new IOHelper(getApplicationContext());
        serviceThread = new ServiceThread();
        serviceThread.start();

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
                pullJSONFromUrl(symbol);
            }
        };
        t.start();
    }



    public Stock pullJSONFromUrl(String symbol){
        URL stockJSONURL;
        Stock tempStock = null;

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
            tempStock = new Stock(stockObject);


            Log.d("Stock data to save :", tempStock.getCompanyName() + " " + tempStock.getCurrentPrice());
        } catch (Exception e) {
            Log.e("Error", "Error grabbing stock");
            e.printStackTrace();
        }
        return tempStock;
    }

    private class ServiceThread extends Thread{
        @Override
        public void run() {
            super.run();
            while (true){
                HashMap<String,Stock> threadMap = ioHelper.readFromFile();
                if(threadMap != null)
                {
                    for (HashMap.Entry<String,Stock> entry
                            :threadMap.entrySet()) {
                        ioHelper.saveStockToFile(pullJSONFromUrl(entry.getKey()));
                        try {
                            Log.d("ServiceThread Ran Sleeping for 60 sec, CurrentState of file",threadMap.toString());
                            ServiceThread.sleep(60000);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }

            }
        }
    }
}


