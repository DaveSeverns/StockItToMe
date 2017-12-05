package com.sevdev.stockittome;

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
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    EditText stockEditText;
    LinearLayout linearLayout;
    private final String PORTFOLIO_FILE_NAME = "portfolioFile";
    File portfolioFile;
    FileOutputStream stream;
    StockService mService;
    boolean mBound;
    Stock stock;
    Porfolio porfolio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        portfolioFile = new File(this.getFilesDir(),PORTFOLIO_FILE_NAME);


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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_title);
        final EditText stockInput = new EditText(this);
        builder.setView(stockInput);

        builder.setPositiveButton(R.string.save_button_text, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String text = stockInput.getText().toString();
                saveToPortfolio(text);
                mService.getStockInfo(text);

            }
        });

        builder.show();
    }

    public void makeToast(String message){
        Toast.makeText(this, message,Toast.LENGTH_SHORT).show();
    }

    public void saveToPortfolio(String stockSymbol){
        stockSymbol += "\n";
        try{
            stream = openFileOutput(PORTFOLIO_FILE_NAME, Context.MODE_PRIVATE);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(stream);
            stream.write(stockSymbol.getBytes());
            stream.close();
            makeToast("Successfully Written to File: " + PORTFOLIO_FILE_NAME);
            //notifyAll();
        }catch (Exception e){
            e.printStackTrace();
            makeToast("Error Saving to File :"+ PORTFOLIO_FILE_NAME);
        }

        Toast.makeText(MainActivity.this,stockSymbol,Toast.LENGTH_SHORT).show();
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
