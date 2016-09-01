package com.loopeer.app.android.loopeerworkingon;

import android.support.annotation.StringRes;
import android.widget.Toast;

public class ToastUtils {

    public static Toast showToast(String s) {
        Toast toast = Toast.makeText(WorkingOnApp.getAppContext(), s, Toast.LENGTH_SHORT);
        toast.show();
        return toast;
    }

    public static Toast showToast(@StringRes int s) {
        Toast toast = Toast.makeText(WorkingOnApp.getAppContext(), s, Toast.LENGTH_SHORT);
        toast.show();
        return toast;
    }

}
