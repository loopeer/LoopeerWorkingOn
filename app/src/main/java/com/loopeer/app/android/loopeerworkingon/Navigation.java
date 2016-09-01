package com.loopeer.app.android.loopeerworkingon;

import android.content.Context;
import android.content.Intent;

public class Navigation {

    public static void startSettingActivity(Context context) {
        context.startActivity(new Intent(context, SettingActivity.class));
    }

    public static void startDiaryAddActivity(Context context) {
        context.startActivity(new Intent(context, DiaryAddActivity.class));
    }

}
