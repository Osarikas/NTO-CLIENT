package ru.myitschool.work;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class LoginActivity extends AppCompatActivity {
    EditText userName;
    Button login;
    TextView error;
    SharedPreferences sharedPreferences;

    SAPI service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sharedPreferences = getSharedPreferences("LoginSave", MODE_PRIVATE);
        if(sharedPreferences.getString("login", null) != null){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        userName = findViewById(R.id.username);
        login = findViewById(R.id.login);
        error = findViewById(R.id.error);
        login.setEnabled(false);
        login.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.disable_btn, getTheme())));
        userName.addTextChangedListener(LoginWatcher);
        service = new Retrofit.Builder()
                .baseUrl(Constants.SERVER_ADDRESS)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(SAPI.class);
    }
    TextWatcher LoginWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String userNameText = userName.getText().toString();
            error.setVisibility(View.GONE);
            if(userNameText.length() >= 3 && !Character.isDigit(userNameText.charAt(0)) && userNameText.matches("^[a-zA-Z0-9]*$")){
                login.setEnabled(true);
                login.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.enable_btn, getTheme())));
            }
            else {
                login.setEnabled(false);
                login.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.disable_btn, getTheme())));
            }
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };
    public void LoginClick(View v){
        Call<Void> call = service.checkLogin(userName.getText().toString());
        call.enqueue(new Callback<>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 200){
                    error.setVisibility(View.GONE);
                    String loginForSave = userName.getText().toString();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("login", loginForSave);
                    editor.apply();
                    Log.d("saved", loginForSave);
                    loginSuccess();
                }
                else {
                    error.setVisibility(View.VISIBLE);
                }
            }
            @Override
            @EverythingIsNonNull
            public void onFailure(Call<Void> call, Throwable t) {
                error.setVisibility(View.VISIBLE);
            }
        });
    }
    private void loginSuccess(){
        Intent intent = new Intent(this, MainActivity.class).putExtra("refresh", false);;
        startActivity(intent);
        finish();
    }

}