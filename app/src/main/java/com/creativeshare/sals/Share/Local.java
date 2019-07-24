package com.creativeshare.sals.Share;


import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDexApplication;

import com.creativeshare.sals.Language.Language;
import com.creativeshare.sals.preferences.Preferences;


public class Local extends MultiDexApplication {
    @Override
    protected void attachBaseContext(Context base)
    {
        super.attachBaseContext(Language.updateResources(base, Preferences.getInstance().getLanguage(base)));
    }
    @Override
    public void onCreate() {
        super.onCreate();
        TypefaceUtil.overrideFont(this, "SERIF", "fonts/LateefRegOT.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf
        TypefaceUtil.overrideFont(this, "DEFAULT", "fonts/LateefRegOT.ttf");
        TypefaceUtil.overrideFont(this, "MONOSPACE", "fonts/LateefRegOT.ttf");
        TypefaceUtil.overrideFont(this, "SANS_SERIF", "fonts/LateefRegOT.ttf");
    }
}

