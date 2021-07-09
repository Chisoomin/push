package com.example.push_app;

import android.util.Log;

public class appinfo_getset {
    String appinfoname, appinfovalue;

    public appinfo_getset(String appinfoname, String appinfovalue) {
        this.appinfoname = appinfoname;
        this.appinfovalue = appinfovalue;
        Log.e("야야야ㅑ야야야야양야야ㅑㅇ", appinfoname+appinfovalue);
    }

    public String getAppinfoname() {
        return appinfoname;
    }

    public void setAppinfoname(String appinfoname) {
        this.appinfoname = appinfoname;
    }

    public String getAppinfovalue() {
        return appinfovalue;
    }

    public void setAppinfovalue(String appinfovalue) {
        this.appinfovalue = appinfovalue;
    }
}
