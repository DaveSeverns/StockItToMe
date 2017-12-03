package com.sevdev.stockittome;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by davidseverns on 12/3/17.
 */

public class StockService extends Service {

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

    public void getStockInfo(String stockSymbol){

    }

}


