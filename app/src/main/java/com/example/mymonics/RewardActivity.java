package com.example.mymonics;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymonics.adapter.RewardAdapter;
import com.example.mymonics.api.APIClient;
import com.example.mymonics.api.APIInteface;
import com.example.mymonics.model.Reward;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RewardActivity extends AppCompatActivity {

    private RecyclerView rvReward;
    private List<Reward> listReward;
    RewardAdapter rewardAdapter;
    private ImageButton imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);

        listReward = new ArrayList<>();
        rvReward = findViewById(R.id.rv_reward);
        rvReward.setHasFixedSize(true);
        imgBack = findViewById(R.id.img_back);

        showRecyclerCardView();
        getReward();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RewardActivity.this, CleaningServiceActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getReward() {
        APIInteface apiInteface = APIClient.getApiClient().create(APIInteface.class);

        Call<List<Reward>> call = apiInteface.getReward();
        call.enqueue(new Callback<List<Reward>>() {
            @Override
            public void onResponse(Call<List<Reward>> call, Response<List<Reward>> response) {
                Log.d("masuk", String.valueOf(response.body()));
                listReward.addAll(response.body());
                rvReward.setAdapter(rewardAdapter);
            }

            @Override
            public void onFailure(Call<List<Reward>> call, Throwable t) {

            }
        });
    }

    private void showRecyclerCardView() {
        rvReward.setLayoutManager(new GridLayoutManager(this,2));
        rewardAdapter = new RewardAdapter(listReward,this);
        rvReward.setAdapter(rewardAdapter);
    }
}
