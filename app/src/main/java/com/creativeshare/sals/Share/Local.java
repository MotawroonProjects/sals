package com.creativeshare.sals.Share;


import android.app.Application;
import android.content.Context;

import com.creativeshare.sals.Language.Language;


public class Local extends Application {
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(Language.updateResources(newBase, Language.getLanguage(newBase)));
    }

}

