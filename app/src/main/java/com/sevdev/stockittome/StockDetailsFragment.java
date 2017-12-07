package com.sevdev.stockittome;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;



public class StockDetailsFragment extends Fragment {


    TextView companyText;
    TextView priceText;
    IOHelper ioHelper;
    ImageView stockImageView;

    public StockDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedStateInstance){
        super.onCreate(savedStateInstance);
        ioHelper = new IOHelper(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(container != null){
            container.removeAllViews();
        }

        Bundle bundle = this.getArguments();
        int position = 0;
        String symbol = "";

        if(bundle!=null){
            position = bundle.getInt("position");
            symbol = bundle.getString("symbol");
        }
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stock_details, container, false);
        companyText = view.findViewById(R.id.company_name_text);
        priceText = view.findViewById(R.id.last_price_text);
        stockImageView = view.findViewById(R.id.stock_chart_image);

        Stock stock = ioHelper.getStockBySymbolKey(symbol);
        if(bundle != null){
            companyText.setText(stock.getCompanyName());
            priceText.setText("$"+stock.getCurrentPrice());
        }

        if(stock != null){
            showStockChart(symbol);
        }

        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    public void showStockChart(String symbol) {

        Picasso.with(getContext())
                .load("https://finance.google.com/finance/getchart?p=5d&q=" + symbol)
                .centerInside()
                .resize(1200, 1200)
                .into(stockImageView);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

}
