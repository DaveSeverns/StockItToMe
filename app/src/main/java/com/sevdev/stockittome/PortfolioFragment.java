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

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class PortfolioFragment extends Fragment {

    View view;
    ListView listView;
    public final ArrayList<String> test = new ArrayList<>();

    public PortfolioFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        test.add("MSFT");
        test.add("AAPL");
        test.add("DOW");
        test.add("SNAP");
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
}
