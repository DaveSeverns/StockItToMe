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

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class PortfolioFragment extends Fragment {

    private final String PORTFOLIO_FILE_NAME = "portfolioFile";
    View view;
    ListView listView;
    public  ArrayList<String> test = getDataForPortfolio();

    public PortfolioFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_portfolio, container, false);
        listView = view.findViewById(R.id.list_portfolio);
        PortfolioAdapter portfolioAdapter = new PortfolioAdapter(getActivity(), test);
        listView.setAdapter(portfolioAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(),test.get(position),Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }


    public ArrayList<String> getDataForPortfolio(){
        InputStream fis;
        ArrayList<String> tempList = new ArrayList<>();
        final StringBuffer storedString = new StringBuffer();

        try {
            fis = new FileInputStream(PORTFOLIO_FILE_NAME);
            DataInputStream dataIO = new DataInputStream(fis);
            String strLine = null;

            while((strLine = dataIO.readLine()) != null) {
                storedString.append(strLine);
                tempList.add(storedString.toString());

            }

            dataIO.close();
            fis.close();
        }
        catch  (Exception e) {
        }
        return tempList;
    }
}
