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
import java.util.List;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codingtest.MainActivity;
import com.example.codingtest.R;
import com.example.codingtest.SecondFragment;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class CommitListAdapter extends RecyclerView.Adapter<CommitListAdapter.ViewHolder> {
   // private CommitList[] listdata;
    private List<CommitList> listdata;

    private CallBack mCallBack;
   // private final OnClickListener mOnClickListener = new MyOnClickListener();

    public CommitListAdapter (CallBack callback){

        mCallBack = callback;
    }
    public CommitListAdapter(List<CommitList> listdata, CallBack mOnClickListener) {

        this.listdata = listdata;
        mCallBack = mOnClickListener;

    }

    public ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
       Log.d("iqbal ","called");
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem= layoutInflater.inflate(R.layout.item_list, viewGroup, false);
        //listItem.setOnClickListener();
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder( CommitListAdapter.ViewHolder viewHolder, int i) {
        final CommitList myListData = listdata.get(i);
//        Log.d("iqbal", listdata[i].getName());
        //LinearLayout.LayoutParams Params1 = new LinearLayout.LayoutParams(15,50);

        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(MainActivity.width - 310, ViewGroup.LayoutParams.WRAP_CONTENT);
        llp.setMargins(MainActivity.margin,0,0,0);
        Log.d("agile", String.valueOf(MainActivity.width));
        viewHolder.textView.setLayoutParams(llp);
        int index = listdata.get(i).getDate().indexOf("T");
        String resultMsg = listdata.get(i).getDate().substring(0, index);
        viewHolder.textView.setText(listdata.get(i).getMessage());
        viewHolder.txtDate.setText(resultMsg);
        Picasso.get().load(listdata.get(i).getAvatar()).transform(new CropCircleTransformation()).into(viewHolder.smallAvatar);
        viewHolder.txtName.setText(listdata.get(i).getName());
        //viewHolder.textView.setText("iqbal");
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
