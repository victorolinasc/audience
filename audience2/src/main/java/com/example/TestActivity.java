package com.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import br.com.concretesolutions.audience.core.Director;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Director.actor(this) // Activity actor
                .tell(new Object())
                .onStage()
                .toActor(ApiClient.class);
    }
}
