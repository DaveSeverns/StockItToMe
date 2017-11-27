package com.sevdev.stockittome;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by davidseverns on 11/26/17.
 */

public class PortfolioAdapter extends BaseAdapter {
    Context ctx;
    ArrayList<String> collection;

    public PortfolioAdapter(Context ctx, ArrayList collection) {
        this.ctx = ctx;
        this.collection = collection;
    }

    @Override
    public int getCount() {
        return collection.size();
    }

    @Override
    public Object getItem(int position) {
        return collection.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = new TextView(ctx);
        textView.setText(collection.get(position));
        textView.setTextSize(20);
        return textView;
    }

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }
}
