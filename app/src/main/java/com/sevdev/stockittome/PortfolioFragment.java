package com.sevdev.stockittome;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class PortfolioFragment extends Fragment {

    private final String PORTFOLIO_FILE_NAME = "portfolioFile";
    View view;
    ListView listView;
    public  ArrayList<String> adapterList;
    private Porfolio porfolio;

    public PortfolioFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_portfolio, container, false);
        listView = view.findViewById(R.id.list_portfolio);
        porfolio = getDataForPortfolio();
        adapterList = getStockListFromPortfolio(porfolio);
        PortfolioAdapter portfolioAdapter = new PortfolioAdapter(getActivity(), adapterList);
        listView.setAdapter(portfolioAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(),adapterList.get(position),Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }


    public Porfolio getDataForPortfolio(){

        Porfolio tempPortfolio = new Porfolio();
        //FileOutputStream fos = new FileOutputStream(PORTFOLIO_FILE_NAME, Context.MODE_APPEND);
        try {
            FileInputStream fis = getActivity().openFileInput(PORTFOLIO_FILE_NAME);

            try{
                ObjectInputStream ois = new ObjectInputStream(fis);

                tempPortfolio = (Porfolio) ois.readObject();

                Toast.makeText(getActivity(), tempPortfolio.getStockPortfolioList().get(0).getCompanyName(), Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return  tempPortfolio;
    }

    public ArrayList<String> getStockListFromPortfolio(Porfolio p){
        ArrayList<String> tempStockList = new ArrayList<>();
        for (Stock stock: p.getStockPortfolioList())
        {
            tempStockList.add(stock.getStockSymbol());
        }

        return tempStockList;
    }
}
