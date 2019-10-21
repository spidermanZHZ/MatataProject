package com.example.administrator.matata_android.homepage;

import android.os.AsyncTask;

public class MyPriceTask extends AsyncTask<String ,Void,String > {

    private ChangeBuyPrice changeBuyPrice;

    public MyPriceTask(ChangeBuyPrice changeBuyPrice) {
        this.changeBuyPrice = changeBuyPrice;
    }

    @Override
    protected String doInBackground(String... strings) {
        if (strings[0]!=null){
            changeBuyPrice.onChangeBugPrice(strings[0]);
        }
        return null;
    }
}
