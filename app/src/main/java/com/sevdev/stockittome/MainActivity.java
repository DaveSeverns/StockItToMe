package com.sevdev.stockittome;

import android.app.FragmentManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements PortfolioFragment.PortfolioFragmentInterface{

    private final String PORTFOLIO_FILE_NAME = "portfolioFile.ser";

    StockService mService;
    boolean mBound;

    FragmentManager fragmentManager;
    PortfolioFragment portfolioFragment;
    StockDetailsFragment stockDetailsFragment;
    boolean twoPainz;
    IOHelper ioHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ioHelper =  new IOHelper(getApplicationContext());
        twoPainz = (findViewById(R.id.details_frame) != null);
        fragmentManager = getFragmentManager();

        portfolioFragment = new PortfolioFragment();
        stockDetailsFragment = new StockDetailsFragment();
        //addStocksToFragment();
        fragmentManager.beginTransaction().add(R.id.portfolio_frame, portfolioFragment).commit();
        if(twoPainz){
            fragmentManager.beginTransaction().add(R.id.details_frame, stockDetailsFragment).commit();
        }



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               getSymbol();

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Bind to stock Service
        Intent intent = new Intent(this, StockService.class);

        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(mConnection);
        mBound = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getSymbol(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_title);
        final EditText stockInput = new EditText(this);
        builder.setView(stockInput);

        builder.setPositiveButton(R.string.save_button_text, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String text = stockInput.getText().toString();

                new UpdateListTask().execute(text);



            }
        });

        builder.setNegativeButton(R.string.cancel_dialog_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            StockService.LocalBinder binder = (StockService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };


    @Override
    public void stockItemSelected(int position, String symbol) {

        Bundle bundle = new Bundle();
        bundle.putInt("position",position);
        bundle.putString("symbol",symbol);

        if(!twoPainz){
            getFragmentManager().beginTransaction().replace(R.id.portfolio_frame,stockDetailsFragment).addToBackStack(null).commit();
            stockDetailsFragment.setArguments(bundle);
            getFragmentManager().executePendingTransactions();
        }
        stockDetailsFragment.setTwoPaneView(symbol);

    }

   private class UpdateListTask extends AsyncTask<String,Void,String>{

       public UpdateListTask() {
           super();
       }

       @Override
       protected void onPreExecute() {
           super.onPreExecute();
       }

       @Override
       protected void onPostExecute(String stock) {
           super.onPostExecute(stock);
           if(stock.equalsIgnoreCase("Success")){
               portfolioFragment.parsePortfolioMap(ioHelper.readFromFile());
           }

       }

       @Override
       protected void onProgressUpdate(Void... values) {
           super.onProgressUpdate(values);
       }

       @Override
       protected void onCancelled(String aVoid) {
           super.onCancelled(aVoid);
       }

       @Override
       protected void onCancelled() {
           super.onCancelled();
       }

       @Override
        protected String doInBackground(String... strings) {
           String symbol = strings[0];

           Stock stockFromService = mService.pullJSONFromUrl(symbol);

           ioHelper.saveStockToFile(stockFromService);
           return "Success";
       }
    }

}
