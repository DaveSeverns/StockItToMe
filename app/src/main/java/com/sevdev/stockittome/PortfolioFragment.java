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
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class PortfolioFragment extends Fragment {

    private final String PORTFOLIO_FILE_NAME = "portfolioFile";
    View view;
    ListView listView;
    public  ArrayList<String> adapterList;

    public PortfolioFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_portfolio, container, false);
        listView = view.findViewById(R.id.list_portfolio);
        adapterList = getDataForPortfolio();
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


    public ArrayList<String> getDataForPortfolio(){

        ArrayList<String> resultList = new ArrayList<>();
        //FileOutputStream fos = new FileOutputStream(PORTFOLIO_FILE_NAME, Context.MODE_APPEND);
        try {
            FileInputStream fis = getActivity().openFileInput(PORTFOLIO_FILE_NAME);

            if (fis != null){
                InputStreamReader reader = new InputStreamReader(fis);
                BufferedReader bufferedReader = new BufferedReader(reader);
                String line = "";
                try{
                    while ((line = bufferedReader.readLine()) != null){
                        resultList.add(line);
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return  resultList;
    }
}
