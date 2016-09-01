package com.loopeer.app.android.loopeerworkingon;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private Context mContext;
    private Cursor mCursor;

    public TaskAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        if (mCursor == null || mCursor.getCount() <= position)
            return;
        mCursor.moveToPosition(position);
        holder.bind(Task.MAPPER.map(mCursor));
    }

    @Override
    public int getItemCount() {
        return mCursor == null ? 0 : mCursor.getCount();
    }

    public void setCursor(Cursor cursor) {
        if (mCursor != null)
            mCursor.close();

        mCursor = cursor;
        notifyDataSetChanged();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {

        TextView mContentTextView;
        TextView mDateTextView;

        public TaskViewHolder(View itemView) {
            super(itemView);
            mContentTextView = (TextView) itemView.findViewById(R.id.txt_content);
            mDateTextView = (TextView) itemView.findViewById(R.id.txt_date);
        }

        public void bind(Task task) {
            mContentTextView.setText(task.task());
            mDateTextView.setText(DateUtils.getDetailStandardDate(task.date()));
        }
    }

}
