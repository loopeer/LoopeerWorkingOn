package com.loopeer.app.android.loopeerworkingon;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.gson.Gson;
import com.loopeer.app.android.loopeerworkingon.db.DbAdapter;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class DiaryAddActivity extends AppCompatActivity implements Callback {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static final String BASE_URL = "https://api.workingon.co/hooks/incoming?token=";

    private OkHttpClient mOkHttpClient;
    private String mUrl;

    private EditText mContentEditText;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_add);
        mContentEditText = (EditText) findViewById(R.id.edit_content);

        setupProgressDialog();

        String token = PreferencesUtils.getString(this, PreferencesUtils.KEY_TOKEN);
        if (TextUtils.isEmpty(token)) {
            Navigation.startSettingActivity(this);
            ToastUtils.showToast("请先设置身份标识");
            finish();
            return;
        }

        mUrl = BASE_URL + token;
        setupOKHttp();
    }

    private void setupOKHttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(loggingInterceptor);
        mOkHttpClient = builder.build();
    }

    private void setupProgressDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("提交中...");
        mProgressDialog.setCanceledOnTouchOutside(false);
    }

    private void commit() {
        String content = mContentEditText.getText().toString();
        if (TextUtils.isEmpty(content)) {
            ToastUtils.showToast("提交内容不能为空");
            return;
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("task", content);
            post(mUrl, jsonObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void post(String url, String json) throws IOException {
        showProgress();
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        mOkHttpClient.newCall(request).enqueue(this);
    }

    @Override
    public void onFailure(Call call, IOException e) {
        if (isFinishing())
            return;

        dissmissProgress();

        ToastUtils.showToast("提交失败" + e.getMessage());
    }

    @Override
    public void onResponse(Call call, final Response response) throws IOException {
        if (isFinishing())
            return;

        dissmissProgress();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (response.code() == 200) {
                    ToastUtils.showToast("提交成功");
                    Gson gson = new Gson();
                    try {
                        Task task = gson.fromJson(response.body().string(), AutoValue_Task.class);
                        DbAdapter.getInstance(DiaryAddActivity.this).insertTask(task);
                        setResult(RESULT_OK);
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        ToastUtils.showToast("提交失败" + response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                        ToastUtils.showToast("提交失败" + e.getMessage());
                    }
                }
            }
        });
    }

    private void showProgress() {
        mProgressDialog.show();
    }

    private void dissmissProgress() {
        mProgressDialog.dismiss();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_commit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_commit) {
            commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
