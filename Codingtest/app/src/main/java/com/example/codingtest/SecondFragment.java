package com.example.codingtest;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.codingtest.data.CommitList;
import com.example.codingtest.databinding.FragmentSecondBinding;

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

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("iqbal second", MainActivity.getProfile().getId());
        new LoadProfile(getActivity().getApplicationContext()).execute();
        Button firstBtn = (Button) view.findViewById(R.id.button_first);
        firstBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });

        Button secondBtn = (Button) view.findViewById(R.id.button_second);
        secondBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              // do nothing
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private class LoadProfile extends AsyncTask<Void, Void, Void> {
        public LoadProfile(Context applicationContext) {
        }

        @Override
        protected Void doInBackground(Void... voids) {



            HttpURLConnection urlConnection = null;
            String urlTemp = "https://api.github.com/users/"+MainActivity.getProfile().getId();
            URL url = null;
            try {
                url = new URL(urlTemp);
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
                JSONObject jobj = new JSONObject(jsonString);
              String uni = jobj.getString("avatar_url");

Log.d("iqbal aaaa", uni);


            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }
    }
}