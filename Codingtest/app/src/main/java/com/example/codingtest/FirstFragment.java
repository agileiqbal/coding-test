package com.example.codingtest;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;

import com.example.codingtest.data.CallBack;
import com.example.codingtest.data.CommitList;
import com.example.codingtest.data.CommitListAdapter;
import com.example.codingtest.databinding.FragmentFirstBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class FirstFragment extends Fragment  {
    private final CallBack mOnClickListener = new CallBack() {
        @Override
        public void onItemClick() {
            NavHostFragment.findNavController(FirstFragment.this)
                    .navigate(R.id.action_FirstFragment_to_SecondFragment);
        }
    };
    private FragmentFirstBinding binding;
    CommitList[] myListData;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        myListData = new CommitList[10];

       /* myListData = new CommitList[] {
                new CommitList("Email", "iqbal"),
                new CommitList("Info", "iqbal"),
                new CommitList("Delete","iqbal"),
                new CommitList("Dialer", "iqbal"),
                new CommitList("Alert", "iqbal"),
                new CommitList("Email", "iqbal"),
                new CommitList("Info", "iqbal"),
                new CommitList("Delete","iqbal"),
                new CommitList("Dialer", "iqbal"),
                new CommitList("Alert", "iqbal"),

        };

        CommitListAdapter adapter = new CommitListAdapter(myListData);
        Log.d("iqbal", "ca;;ed3");
        binding.recyclerview.setHasFixedSize(true);

        binding.recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerview.setAdapter(adapter);*/

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new LoadCommitList(getActivity().getApplicationContext()).execute();
        Button firstBtn = (Button) view.findViewById(R.id.button_first);
        firstBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      //do nothing
    }
});
        Button secondBtn = (Button) view.findViewById(R.id.button_second);
        secondBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
      /*  binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });*/
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



    private class LoadCommitList extends AsyncTask<Void, Void, Void> {


        public LoadCommitList(Context applicationContext) {
        }

        @Override
            protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog


        }

            @Override
            protected Void doInBackground(Void... arg0) {


            HttpURLConnection urlConnection = null;

            URL url = null;
            try {
                url = new URL("https://api.github.com/repos/flutter/flutter/commits");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            try {
                urlConnection = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                urlConnection.setRequestMethod("GET");
            } catch (ProtocolException e) {
                e.printStackTrace();
            }
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);

            urlConnection.setDoOutput(true);

            try {
                urlConnection.connect();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String jsonString;
            StringBuilder sb = null;
            try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {

                char[] buffer = new char[1024];

                jsonString = new String();

                sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            jsonString = sb.toString();

            System.out.println("JSON: " + jsonString);
            urlConnection.disconnect();
            try {
                JSONArray itemArray=new JSONArray(jsonString);
                int count = 0;
                for (int i = 0; i < itemArray.length(); i++) {
                    //int value=itemArray.getInt(i);
                    JSONObject jobj = itemArray.getJSONObject(i);
                    String commitString = jobj.getString("commit");
                    JSONObject json = new JSONObject(commitString);
                    JSONObject commit = json.getJSONObject("author");
                    String name = commit.getString("name");

                   // if(!name.contains("x") && !name.contains("g")){
                        Log.d("iqbal count = ", String.valueOf(count));
                        String date = commit.getString("date");
                        String msg = json.getString("message");
                        int index = msg.indexOf("(#");
                        String resultMsg = msg.substring(0, index);
                        String authorString = jobj.getString("author");
                        JSONObject json2 = new JSONObject(authorString);
                        String id = json2.getString("login");
                        String avatar = json2.getString("avatar_url");
                        CommitList cml = new CommitList(name, resultMsg, avatar,date, id);
                        myListData[count] = cml;
                        Log.d("iqbal", cml.getName() + " "+ cml.getAvatar() + cml.getDate() + cml.getMessage() + cml.getId());
                        count++;
                        if(count == 10){
                            break;
                        }
                   // }

                    //JSONArray jData = json.getJSONArray("message");


                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;

        }

            @Override
            protected void onPostExecute(Void result) {
            super.onPostExecute(result);


               CommitListAdapter adapter = new CommitListAdapter(myListData, mOnClickListener);
                Log.d("iqbal", "ca;;ed3");
                binding.recyclerview.setHasFixedSize(true);

                binding.recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
                binding.recyclerview.setAdapter(adapter);
        }

        }
    }
