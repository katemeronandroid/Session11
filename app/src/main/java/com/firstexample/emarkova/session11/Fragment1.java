package com.firstexample.emarkova.session11;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Map;

public class Fragment1 extends Fragment {
    private TextView textView;
    private IDataExchange iDataExchange;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_one,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView = (TextView) view.findViewById(R.id.textView);
        if(MainActivity.CREATED)
            textView.setText(iDataExchange.getData());
        else {
            SharedPreferences preferences = getContext().getSharedPreferences(DataStorage.STORAGE, Context.MODE_PRIVATE);
            if(preferences.contains(DataStorage.KEY))
                textView.setText(preferences.getString(DataStorage.KEY,"default"));
            else
                textView.setText("NO RESULT");
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        iDataExchange = (MainActivity) context;
    }
}
