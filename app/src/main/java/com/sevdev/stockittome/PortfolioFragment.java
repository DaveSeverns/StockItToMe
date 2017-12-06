package com.sevdev.stockittome;

import android.content.Context;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A placeholder fragment containing a simple view.
 */
public class PortfolioFragment extends android.app.Fragment {

    private final String PORTFOLIO_FILE_NAME = "portfolioFile.ser";
    View view;
    ListView listView;
    public  ArrayList<String> adapterList = new ArrayList<>();
    private HashMap portfolio = new HashMap<>();
    Context context;
    PortfolioAdapter portfolioAdapter;
    PortfolioFragmentInterface parent;

    public PortfolioFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        parent = (PortfolioFragmentInterface) context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_portfolio, container, false);
        listView = view.findViewById(R.id.list_portfolio);



        portfolioAdapter = new PortfolioAdapter(getActivity(), adapterList);
        listView.setAdapter(portfolioAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(),adapterList.get(position),Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }


    public void parsePortfolioMap(HashMap<String, Stock> porfolioMap) {
        String tempSymbol;

        for (HashMap.Entry<String,Stock> entry :
             porfolioMap.entrySet()) {
            tempSymbol = entry.getKey();
            Toast.makeText(context, tempSymbol, Toast.LENGTH_SHORT).show();
            adapterList.add(tempSymbol);
            portfolioAdapter.notifyDataSetChanged();

        }

    }

    public void addStockToList(String symbol){

    }

    public interface PortfolioFragmentInterface{
        public void addStocksToFragment();
    }
}
