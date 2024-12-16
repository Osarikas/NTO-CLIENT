package ru.myitschool.work;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.internal.EverythingIsNonNull;
import ru.myitschool.work.core.Constants;

public class QrResult extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    TextView result;
    Button close;
    SAPI service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_qr_result);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sharedPreferences = getSharedPreferences("LoginSave", MODE_PRIVATE);
        result = findViewById(R.id.result);
        close = findViewById(R.id.close);
        service = new Retrofit.Builder()
                .baseUrl(Constants.SERVER_ADDRESS)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(SAPI.class);

        if(getIntent().getStringExtra("QR_DATA") != null){
            RequestBodyModel bodyModel = new RequestBodyModel(getIntent().getStringExtra("QR_DATA"));
            Call<Void> call = service.openDoor(sharedPreferences.getString("login", null), bodyModel);
            call.enqueue(new Callback<>() {
                @Override
                @EverythingIsNonNull
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.code() == 200) {
                        result.setText(getString(R.string.result_success_text));
                    } else {
                        result.setText(getString(R.string.result_fail_text));
                    }
                }
                @Override
                @EverythingIsNonNull
                public void onFailure(Call<Void> call, Throwable t) {
                    result.setText(getString(R.string.result_fail_text));
                }
            });
        }
        else {
            result.setText(getString(R.string.result_null_text));
        }

        close.setOnClickListener(v -> {
            finish();

        });
    }
}