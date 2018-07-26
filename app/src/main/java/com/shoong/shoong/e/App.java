package com.shoong.shoong.e;

import android.app.Application;

import com.tsengvn.typekit.Typekit;

public class App extends Application{
    @Override
    public void onCreate() {
        super.onCreate();

        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "fonts/enjoystories_medium.ttf"))
                .addBold(Typekit.createFromAsset(this, "fonts/enjoystories_bold.ttf"));
    }
}
