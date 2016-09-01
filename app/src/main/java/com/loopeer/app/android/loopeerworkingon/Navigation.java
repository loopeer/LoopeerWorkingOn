package com.loopeer.app.android.loopeerworkingon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class Navigation {

    public static final int REQUEST_CODE_ADD_DIARY = 1;

    public static void startSettingActivity(Context context) {
        context.startActivity(new Intent(context, SettingActivity.class));
    }

    public static void startDiaryAddActivity(Activity activity) {
        activity.startActivityForResult(new Intent(activity, DiaryAddActivity.class), REQUEST_CODE_ADD_DIARY);
    }

}
