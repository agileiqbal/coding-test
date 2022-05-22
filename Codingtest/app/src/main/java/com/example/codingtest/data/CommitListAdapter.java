package com.example.codingtest.data;


import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView;
import android.view.ViewGroup;
import android.view.LayoutInflater;

import android.util.Log;
import android.util.DisplayMetrics;
import android.content.Context;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.concurrent.TimeUnit;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.text.Format;

import androidx.recyclerview.widget.RecyclerView;

import com.example.codingtest.MainActivity;
import com.example.codingtest.R;
import com.example.codingtest.ProfileFragment;
import com.example.codingtest.utils.Util;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class CommitListAdapter extends RecyclerView.Adapter<CommitListAdapter.ViewHolder> {
    private List<CommitList> listdata;

    private CallBack mCallBack;
    public CommitListAdapter (CallBack callback){

        mCallBack = callback;
    }
    public CommitListAdapter(List<CommitList> listdata, CallBack mOnClickListener) {

        this.listdata = listdata;
        mCallBack = mOnClickListener;

    }

    public ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem= layoutInflater.inflate(R.layout.item_list, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder( CommitListAdapter.ViewHolder viewHolder, int i) {
        String dateString = Util.getDateString(listdata.get(i).getDate());
        LinearLayout.LayoutParams llp;
        if(dateString.length() < 6){
            llp = new LinearLayout.LayoutParams(MainActivity.width - MainActivity.date_width1, ViewGroup.LayoutParams.WRAP_CONTENT);
        } else{
            llp = new LinearLayout.LayoutParams(MainActivity.width - MainActivity.date_width2, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        llp.setMargins(MainActivity.message_margin,0,0,0);
        viewHolder.textView.setLayoutParams(llp);
        viewHolder.textView.setText(listdata.get(i).getMessage());
        viewHolder.txtDate.setText(dateString);
        Picasso.get().load(listdata.get(i).getAvatar()).transform(new CropCircleTransformation()).into(viewHolder.smallAvatar);
        viewHolder.txtName.setText(listdata.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public TextView textView;
        public TextView txtDate;
        public ImageView smallAvatar;
        public TextView txtName;

        public ViewHolder(View itemView) {
            super(itemView);
            this.textView = (TextView) itemView.findViewById(R.id.txtTitle);
            this.txtDate = (TextView) itemView.findViewById(R.id.txtDate);
            this.smallAvatar = (ImageView) itemView.findViewById(R.id.small_avatar);
            this.txtName = (TextView) itemView.findViewById(R.id.txtName);
            Context context = itemView.getContext();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallBack.onItemClick();
                    MainActivity.getProfile().setId(listdata.get(getAbsoluteAdapterPosition()).getId());
                    MainActivity.getProfile().setName(listdata.get(getAbsoluteAdapterPosition()).getName());
                }
            });
        }


    }
}
