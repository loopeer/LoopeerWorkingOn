package com.loopeer.app.android.loopeerworkingon;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.loopeer.app.android.loopeerworkingon.db.DbAdapter;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private Cursor mCursor;

    private TaskAdapter mTaskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.view_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, 0));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.startDiaryAddActivity(MainActivity.this);
            }
        });

        setupFirstRun();

        loadTasks();
    }

    private void loadTasks() {
        mCursor = DbAdapter.getInstance(this).queryTasks();
        mTaskAdapter = new TaskAdapter(this, mCursor);
        mRecyclerView.setAdapter(mTaskAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCursor != null)
            mCursor.close();
    }

    private void setupFirstRun() {
        if (TextUtils.isEmpty(PreferencesUtils.getString(this, PreferencesUtils.KEY_TOKEN))) {
            ToastUtils.showToast("请先设置自己的身份标识");
            Navigation.startSettingActivity(this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Navigation.REQUEST_CODE_ADD_DIARY && resultCode == RESULT_OK) {
            mCursor = DbAdapter.getInstance(this).queryTasks();
            mTaskAdapter.setCursor(mCursor);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Navigation.startSettingActivity(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
