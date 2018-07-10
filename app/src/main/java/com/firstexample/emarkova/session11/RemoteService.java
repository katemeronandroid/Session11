package com.firstexample.emarkova.session11;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.widget.EditText;

import com.firstexample.emarkova.session11.IDataInterface;

public class RemoteService extends Service {
    private static final String KEY = "key";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new IDataInterface.Stub() {
            @Override
            public String getNewString() throws RemoteException {
                SharedPreferences sharedPreferences = getSharedPreferences(DataStorage.STORAGE,
                        Context.MODE_PRIVATE);
                return sharedPreferences.getString(KEY, "DEFAULT");
            }
            @Override
            public void setNewString(String str) throws RemoteException {
                SharedPreferences sharedPreferences = getSharedPreferences(DataStorage.STORAGE,
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(KEY, str);
                editor.commit();
            }
        };
    }
}
