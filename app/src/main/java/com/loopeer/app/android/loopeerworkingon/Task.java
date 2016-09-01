package com.loopeer.app.android.loopeerworkingon;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.squareup.sqldelight.RowMapper;

@AutoValue
public abstract class Task implements TaskModel {

    public static final Factory<Task> FACTORY = new Factory<>(new Creator<Task>() {
        @Override
        public Task create(long id, @NonNull String _id, @Nullable String task, @Nullable String created, @Nullable String user, @Nullable String company, @Nullable Long date) {
            return new AutoValue_Task(id, _id, task, created, user, company, date);
        }
    });

    public static final RowMapper<Task> MAPPER = FACTORY.select_allMapper();

}
