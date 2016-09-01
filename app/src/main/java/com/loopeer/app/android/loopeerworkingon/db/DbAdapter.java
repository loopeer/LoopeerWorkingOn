package com.loopeer.app.android.loopeerworkingon.db;

import android.content.Context;
import android.database.Cursor;

import com.loopeer.app.android.loopeerworkingon.Task;

public class DbAdapter {

    private static DbAdapter sDbAdapter;

    private DbOpenHelper mDbOpenHelper;

    private DbAdapter() {
    }

    public static DbAdapter getInstance(Context context) {
        if (sDbAdapter == null)
            sDbAdapter = new DbAdapter();
        if (sDbAdapter.mDbOpenHelper == null)
            sDbAdapter.mDbOpenHelper = DbOpenHelper.getInstance(context);
        return sDbAdapter;
    }

    public void insertTask(Task task) {
        mDbOpenHelper.getWritableDatabase()
                .insert(Task.TABLE_NAME, null, Task.FACTORY.marshal()
                        ._id(task._id())
                        .company(task.company())
                        .user(task.user())
                        .created(task.created())
                        .task(task.task())
                        .date(System.currentTimeMillis())
                        .asContentValues());
    }

    public Cursor queryTasks() {
        return mDbOpenHelper.getReadableDatabase()
                .rawQuery(Task.SELECT_ALL, null);
    }

}
