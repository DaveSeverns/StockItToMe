package com.sevdev.stockittome;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
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


    View view;
    ListView listView;
    ArrayList<String> adapterList;
    PortfolioAdapter portfolioAdapter;
    PortfolioFragmentInterface portfolioFragmentInterface;
    IOHelper ioHelper;

    public PortfolioFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ioHelper = new IOHelper(getActivity());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof PortfolioFragmentInterface){
            portfolioFragmentInterface = (PortfolioFragmentInterface) context;
        }else{
            throw new RuntimeException(context.toString() + "needs to implement PortfolioFragmentInterface");
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(container != null){
            container.removeAllViews();
        }

        view =  inflater.inflate(R.layout.fragment_portfolio, container, false);
        adapterList = ioHelper.getListOfSymbols();
        listView = view.findViewById(R.id.list_portfolio);

        TextView emptyListText = view.findViewById(R.id.emptyStockText);
        listView.setEmptyView(emptyListText);

        portfolioAdapter = new PortfolioAdapter(getActivity(), adapterList);
        listView.setAdapter(portfolioAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(),adapterList.get(position),Toast.LENGTH_LONG).show();

                portfolioFragmentInterface.stockItemSelected(position,adapterList.get(position));
            }
        });

        return view;
    }


    public void parsePortfolioMap(HashMap<String, Stock> porfolioMap) {
        String tempSymbol;

        for (HashMap.Entry<String,Stock> entry :
             porfolioMap.entrySet()) {
            tempSymbol = entry.getKey();

            adapterList.add(tempSymbol);
            portfolioAdapter.notifyDataSetChanged();

        }

    }

    public void addStockToList(String symbol){
            adapterList.add(symbol.toUpperCase());
            portfolioAdapter.notifyDataSetChanged();

    }

    public interface PortfolioFragmentInterface{
        void stockItemSelected(int position, String symbol);
    }
}
