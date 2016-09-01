package com.loopeer.app.android.loopeerworkingon;

import com.google.gson.annotations.SerializedName;

public class Task {

    @SerializedName("_id")
    public String id;
    @SerializedName("task")
    public String task;
    @SerializedName("created")
    public String created;
    @SerializedName("user")
    public String user;
    @SerializedName("company")
    public String company;

}