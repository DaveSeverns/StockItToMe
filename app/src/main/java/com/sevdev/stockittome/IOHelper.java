package com.sevdev.stockittome;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by davidseverns on 12/6/17.
 */

public class IOHelper {
    private final String PORTFOLIO_FILE_NAME = "portfolioFile.ser";

    Context context;
    public IOHelper(Context c){
        context = c;
        //File file = new File(context.getFilesDir()+PORTFOLIO_FILE_NAME);
        Log.e("File Path", "dave is retarded");

    }

    public HashMap readFromFile() {
        HashMap<String, Stock> stockHashMap = new HashMap<>();

        try {
            FileInputStream fileInputStream = context.openFileInput(PORTFOLIO_FILE_NAME);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            stockHashMap = (HashMap) objectInputStream.readObject();
            Log.e("From file in activity", stockHashMap.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return stockHashMap;
    }

    public ArrayList<String> getListOfSymbols(){
        ArrayList<String> stockList = new ArrayList<>();
        HashMap<String,Stock> mapToParse = readFromFile();

        for (HashMap.Entry<String,Stock> entry :
             mapToParse.entrySet()) {
            stockList.add(entry.getKey());
        }
        return stockList;
    }


    public boolean saveStockToFile(Stock stock){
        boolean saved = false;
        //TODO if this fails add the load back in.
        HashMap<String,Stock> tempMap = readFromFile();
        tempMap.put(stock.getStockSymbol(),stock);
        FileOutputStream fos;
        try {
            fos = context.openFileOutput(PORTFOLIO_FILE_NAME, Context.MODE_PRIVATE);
            String path = context.getFilesDir().getAbsolutePath();
            Log.e("path", path);
            ObjectOutputStream oos = null;
            oos = new ObjectOutputStream(fos);
            oos.writeObject(tempMap);
            oos.close();
            fos.close();
            saved = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return saved;
    }

    public Stock getStockBySymbolKey(String symbol){
        Stock tempStock = new Stock();
        HashMap<String,Stock> stringStockHashMap = readFromFile();
        if(stringStockHashMap != null){
            tempStock = stringStockHashMap.get(symbol);
        }
        return  tempStock;
    }
}
