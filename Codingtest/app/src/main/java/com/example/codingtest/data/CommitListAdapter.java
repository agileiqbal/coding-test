package com.example.codingtest.data;


import android.view.View;
import android.widget.TextView;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.util.Log;
import android.content.Context;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codingtest.MainActivity;
import com.example.codingtest.R;
import com.example.codingtest.SecondFragment;

public class CommitListAdapter extends RecyclerView.Adapter<CommitListAdapter.ViewHolder> {
    private CommitList[] listdata;

    private CallBack mCallBack;
   // private final OnClickListener mOnClickListener = new MyOnClickListener();

    public CommitListAdapter (CallBack callback){

        mCallBack = callback;
    }
    public CommitListAdapter(CommitList[] listdata, CallBack mOnClickListener) {
        Log.d("iqbal size = ", String.valueOf(listdata.length));
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
        final CommitList myListData = listdata[i];
//        Log.d("iqbal", listdata[i].getName());
        viewHolder.textView.setText(listdata[i].getName());


        //viewHolder.textView.setText("iqbal");
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.textView = (TextView) itemView.findViewById(R.id.textViewList);
            Context context = itemView.getContext();

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallBack.onItemClick();

                    MainActivity.getProfile().setId(listdata[getAbsoluteAdapterPosition()].getId());
                    /*Log.d("iqbal", "calleddddd");
                   int pos = getAbsoluteAdapterPosition();
                    Log.d("iqbal positio", String.valueOf(pos));

                  //  Navigation.createNavigateOnClickListener(R.id.action_FirstFragment_to_SecondFragment);
                    Fragment second = new SecondFragment();
                    FragmentManager ft = ((MainActivity)context).getSupportFragmentManager();
                    ft.popBackStack();
                    //ft.beginTransaction().
                    ft.beginTransaction().replace(R.id.sf, second).addToBackStack(null).commit();*/
                }
            });
        }


    }
}
