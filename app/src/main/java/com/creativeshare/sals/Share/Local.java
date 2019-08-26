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
        TypefaceUtil.overrideFont(this, "SERIF", "font/GE-SS-Two-Bold.otf"); // font from assets: "assets/fonts/Roboto-Regular.ttf
        TypefaceUtil.overrideFont(this, "DEFAULT", "font/GE-SS-Two-Bold.otf");
        TypefaceUtil.overrideFont(this, "MONOSPACE", "font/GE-SS-Two-Bold.otf");
        TypefaceUtil.overrideFont(this, "SANS_SERIF", "font/GE-SS-Two-Bold.otf");
    }
}

