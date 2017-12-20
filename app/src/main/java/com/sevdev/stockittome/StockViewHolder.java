package com.sevdev.stockittome;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by davidseverns on 12/20/17.
 */

public class StockViewHolder extends RecyclerView.ViewHolder {
    protected TextView stockSymbolText;
    protected TextView stockPriceText;
    protected ImageView changeImage;

    public StockViewHolder(View itemView) {
        super(itemView);
        stockSymbolText = itemView.findViewById(R.id.stock_symbol_on_card);
        stockPriceText = itemView.findViewById(R.id.stock_price_on_card);
        changeImage = itemView.findViewById(R.id.stock_change_image);
    }
}
