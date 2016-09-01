package com.loopeer.app.android.loopeerworkingon;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class SettingActivity extends AppCompatActivity {

    private EditText mTokenEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mTokenEditText = (EditText) findViewById(R.id.edit_token);

        String token = PreferencesUtils.getString(this, PreferencesUtils.KEY_TOKEN);
        if (!TextUtils.isEmpty(token))
            mTokenEditText.setText(token);
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

    private void commit() {
        String token = mTokenEditText.getText().toString();
        if (TextUtils.isEmpty(token)) {
            ToastUtils.showToast("身份标识不能为空");
            return;
        }

        PreferencesUtils.putString(this, PreferencesUtils.KEY_TOKEN, token);
        finish();
    }

}
