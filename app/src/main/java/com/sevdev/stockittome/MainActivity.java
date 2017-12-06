package com.sevdev.stockittome;

import android.app.FragmentManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements PortfolioFragment.PortfolioFragmentInterface{
    EditText stockEditText;
    LinearLayout linearLayout;
    private final String PORTFOLIO_FILE_NAME = "portfolioFile.ser";
    File portfolioFile;
    StockService mService;
    boolean mBound;
    Stock stock;
    FrameLayout portfolioFrame;
    FragmentManager fragmentManager;
    PortfolioFragment portfolioFragment;
    HashMap<String,Stock> porfolioMap = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fragmentManager = getFragmentManager();
        portfolioFragment = new PortfolioFragment();
        addStocksToFragment();
        fragmentManager.beginTransaction().replace(R.id.portfolio_frame, portfolioFragment).commit();



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

                mService.getStockInfo(text);



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

    @Override
    public void addStocksToFragment(){


        try {
            FileInputStream fileInputStream = openFileInput(PORTFOLIO_FILE_NAME);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            porfolioMap = (HashMap) objectInputStream.readObject();
            Log.e("From file in activity", porfolioMap.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if(porfolioMap.entrySet() != null){

            portfolioFragment.parsePortfolioMap(porfolioMap);
        }
        else Toast.makeText(this, "Map Null", Toast.LENGTH_SHORT).show();

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

   
}
