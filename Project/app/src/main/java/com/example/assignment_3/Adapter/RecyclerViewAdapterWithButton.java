package com.example.assignment_3.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment_3.Model.Record;
import com.example.assignment_3.R;

import java.util.ArrayList;

public class RecyclerViewAdapterWithButton extends RecyclerView.Adapter<RecyclerViewAdapterWithButton.MainListItemViewHolder> {
    private ArrayList<Record> recordList;
    private int num = 1;
    private ButtonClick btnclickListener;

    public RecyclerViewAdapterWithButton(ArrayList<Record> recordList,ButtonClick btnclickListener) {
        this.recordList = recordList;
        this.btnclickListener = btnclickListener;
    }

    public void reloadRecordList(ArrayList<Record> recordList) {
        this.recordList = recordList;
        this.notifyDataSetChanged();
    }

    public ArrayList<Record> getRecordList() {
        return recordList;
    }

    @NonNull
    @Override
    public MainListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recorditem, parent, false);

        MainListItemViewHolder vh = new MainListItemViewHolder(v,btnclickListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MainListItemViewHolder holder, int position) {
        holder.txtName.setText(recordList.get(position).getName());
        String timeDisplay = secToTime(recordList.get(position).getTime());
        holder.txtTime.setText(timeDisplay);
        holder.txtdate.setText(recordList.get(position).getDate());
        holder.txtnum.setText(String.valueOf(num));
        num++;
    }

    @Override
    public int getItemCount() {
        return recordList == null ? 0 : recordList.size();
    }

    class MainListItemViewHolder extends RecyclerView.ViewHolder implements Button.OnClickListener {
        private TextView txtName;
        private TextView txtnum;
        private TextView txtdate;
        private TextView txtTime;
        private Button btnShare;
        ButtonClick buttonClick;

        public MainListItemViewHolder(@NonNull View itemView, ButtonClick buttonClick) {
            super(itemView);
            txtdate = itemView.findViewById(R.id.txt_date);
            txtName = itemView.findViewById(R.id.txt_name);
            txtnum = itemView.findViewById(R.id.txt_num);
            txtTime = itemView.findViewById(R.id.txt_time);
            btnShare = itemView.findViewById(R.id.btn_high_share);

            this.buttonClick = buttonClick;

            btnShare.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            btnclickListener.buttonOnclick(getAdapterPosition());
        }
    }

    //converting the secs to hh mm ss format string
    protected String secToTime(long sec) {
        long seconds = sec % 60;
        long minutes = sec / 60;
        if (minutes >= 60) {
            long hours = minutes / 60;
            minutes %= 60;
            if (hours >= 24) {
                long days = hours / 24;
                return String.format("%d days %02d:%02d:%02d", days, hours % 24, minutes, seconds);
            }
            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        }
        return String.format("00:%02d:%02d", minutes, seconds);
    }

    public interface ButtonClick{
        void buttonOnclick(int position);
    }
}
