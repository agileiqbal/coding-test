package com.example.codingtest;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.graphics.BitmapKt;
import androidx.core.graphics.drawable.BitmapDrawableKt;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.codingtest.data.CommitList;
import com.example.codingtest.data.CommitListAdapter;
import com.example.codingtest.databinding.FragmentSecondBinding;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

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

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private ImageView imgAvatar;
    private TextView txtName;
    //private TextView txtName;
    private TextView txtId;
    private TextView txtBio;
    private TextView txtPr;
    private TextView txtPg;
    private TextView txtPrR;

    private String avatar;
    private String name;
    private String id;
    private String bio = "";
    private String pr = "";
    private String pg = "";
    private String prR = "0";

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
        id = MainActivity.getProfile().getId();
        name = MainActivity.getProfile().getName();
        new LoadProfile(getActivity().getApplicationContext()).execute();
        imgAvatar = (ImageView) view.findViewById(R.id.main_avatar);
        txtName = (TextView) view.findViewById(R.id.textViewName);
        txtId = (TextView) view.findViewById(R.id.textViewId);
        txtBio = (TextView) view.findViewById(R.id.textViewBio);
        txtPr = (TextView) view.findViewById(R.id.textViewPr);
        txtPg = (TextView) view.findViewById(R.id.textViewPg);
        txtPrR = (TextView) view.findViewById(R.id.textViewPrR);
        LinearLayout ll = (LinearLayout) view.findViewById(R.id.commitLayout);
        ll.setAlpha(0.5f);
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
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
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);


            URL newurl = null;
            try {
                newurl = new URL(avatar);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            final int radius = 5;
            final int margin = 5;

            Picasso.get().load(avatar).transform(new CropCircleTransformation()).into(imgAvatar);
            txtName.setText(name);
            txtId.setText("@" + id);
            txtBio.setText("Bio: " + bio);
            txtPr.setText("Public Repos: " + pr);
            txtPg.setText("Public Gists: " + pg);
            txtPrR.setText("Private Repos: " + prR);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpURLConnection urlConnection = null;
            String urlTemp = "https://api.github.com/users/" + id;
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

            if(sb == null){
                return null;
            }

            jsonString = sb.toString();

            System.out.println("JSON: " + jsonString);
            urlConnection.disconnect();
            try {
                JSONObject jobj = new JSONObject(jsonString);
                avatar = jobj.getString("avatar_url");

                bio = jobj.getString("bio");
                pr = jobj.getString("public_repos");
                pg = jobj.getString("public_gists");
                if (jobj.has("private_repos")) {
                    prR = jobj.getString("private_repos");
                }

                if (bio.equals("null")) {
                    bio = "";
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }
    }
}