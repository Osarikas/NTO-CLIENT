//package ru.myitschool.work;
//
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.icu.text.SimpleDateFormat;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//import com.squareup.picasso.Picasso;
//import java.text.ParseException;
//import java.util.Date;
//import java.util.Locale;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//import retrofit2.internal.EverythingIsNonNull;
//import ru.myitschool.work.core.Constants;
//import ru.myitschool.work.data.SAPI;
//import ru.myitschool.work.data.dto.UserDTO;
//import ru.myitschool.work.ui.qr.scan.QrScanDestination;
//
//public class MainActivity {
//    SharedPreferences sharedPreferences;
//    TextView fullName, position, lastEntry, error;
//    ImageView photo;
//    Button logout, refresh, scan;
//    SAPI service;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        sharedPreferences = getSharedPreferences("LoginSave", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("scan", "");
//        editor.apply();
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        //setContentView(R.layout.activity_main);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//
//        fullName = findViewById(R.id.fullname);
//        position = findViewById(R.id.position);
//        lastEntry = findViewById(R.id.lastEntry);
//        photo = findViewById(R.id.photo);
//        logout = findViewById(R.id.logout);
//        refresh = findViewById(R.id.refresh);
//        scan = findViewById(R.id.scan);
//        error = findViewById(R.id.error);
//        Retrofit retrofit = new Retrofit
//                .Builder()
//                .baseUrl(Constants.SERVER_ADDRESS)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        service = retrofit.create(SAPI.class);
//        getAllUserData();
//
//
//
//
//    }
//
//
//    private void SetInfo(UserDTO user){
//        fullName.setText(user.getName());
//        position.setText(user.getPosition());
//        lastEntry.setText(DateConverter(user.getLastVisit()));
//        Picasso.get()
//                .load(user.getPhoto())
//                .into(photo);
//    }
//    public void RefreshClick(View v){
//        getAllUserData();
//    }
//    private void getAllUserData(){
//        Call<UserDTO> call = service.getInfo(sharedPreferences.getString("login", null));
//        call.enqueue(new Callback<>() {
//            @Override
//            @EverythingIsNonNull
//            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
//                if(response.code() == 200){
//                    UserDTO user = response.body();
//                    assert user != null;
//                    SetInfo(user);
//                    SwitchError(false);
//                }
//                else {
//                    SwitchError(true);
//                }
//            }
//            @Override
//            @EverythingIsNonNull
//            public void onFailure(Call<UserDTO> call, Throwable t) {
//                SwitchError(true);
//            }
//        });
//    }
//    public void LogoutClick(View v){
//        SharedPreferences.Editor editor= sharedPreferences.edit();
//        editor.remove("login");
//        editor.apply();
//        //TODO logout
//    }
//    public void ScanClick(View v){
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("scan", "scanClick");
//        editor.apply();
//
//        getSupportFragmentManager().setFragmentResultListener(QrScanDestination.REQUEST_KEY, this, (requestKey, bundle) -> {
//            String qrData = QrScanDestination.INSTANCE.getDataIfExist(bundle);
//            if(qrData != null){
//                navigateToResult(qrData);
//            }
//
//        });
//    }
//    private void SwitchError(Boolean switcher){
//        View[] views = {fullName, position, photo, logout, scan, lastEntry};
//        if(switcher){
//            error.setVisibility(View.VISIBLE);
//            setViewsVisible(views, View.GONE);
//        }
//        else{
//            error.setVisibility(View.GONE);
//            setViewsVisible(views, View.VISIBLE);
//        }
//
//    }
//    private void setViewsVisible(View[] views, int visible){
//        for (View view : views) {
//            view.setVisibility(visible);
//        }
//    }
//    private String DateConverter(String date){
//        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
//        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
//        try {
//            Date formattedDate = inputFormat.parse(date);
//            return outputFormat.format(formattedDate);
//        }
//        catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//
//
//    }
//    private void navigateToResult(String qrData) {
//        Intent intent = new Intent(this, QrResult.class);
//        intent.putExtra("QR_DATA", qrData);
//        startActivity(intent);
//    }
//}