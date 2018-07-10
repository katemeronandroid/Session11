package com.firstexample.emarkova.session11;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements IDataExchange {
    private boolean write;
    public static boolean CREATED = false;

    private IDataInterface dataInterface;
    private Menu menu;
    private FragmentTransaction transaction;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            dataInterface = IDataInterface.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            dataInterface = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        write = false;
    }


    @Override
    public void sendData(String data) {
        try {
            dataInterface.setNewString(data);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public String getData() {
        try {
            return dataInterface.getNewString();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onStart() {
        super.onStart();
        transaction = getSupportFragmentManager().beginTransaction();
        Fragment1 fragment1 = new Fragment1();
        transaction.add(R.id.frameLayout, fragment1);
        transaction.commit();

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        this.menu = menu;
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(connection);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(this, RemoteService.class);
        intent.setAction(IDataInterface.class.getName());
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
        CREATED = true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(write) {
            menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_brush_black_24dp));
            Fragment1 fragment1 = new Fragment1();
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frameLayout,fragment1);
            transaction.commit();
            write = false;
        }
        else {
            menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_close_black_24dp));
            Fragment2 fragment2 = new Fragment2();
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frameLayout,fragment2);
            transaction.commit();
            write = true;
        }
        return true;
    }
}
