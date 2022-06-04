package com.example.studentsmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    //khai báo bên XML
    EditText etUsername,etPassword;
    Button btnLogin,btnCancel;

    CheckBox chkRememberPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //mapping XML
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin =(Button) findViewById(R.id.btnLogin);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        chkRememberPass =(CheckBox) findViewById(R.id.chkRememberPass);

        // đọc user, pass trong SharedPreferences
        SharedPreferences pref = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        etUsername.setText(pref.getString("USERNAME",""));
        etPassword.setText(pref.getString("PASSWORD",""));
        chkRememberPass.setChecked(pref.getBoolean("REMEMBER",true));

    }

    //đăng nhập(Login)
    public void loginForm(View view) {
        //khai báo về kiểu chuỗi cho người dùng nhập vào
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        if(username.equals("fpt")&& password .equals("123")){
            Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
            //nếu check thì sẽ lưu lại trạng thái
            checkRememberPass(username,password,chkRememberPass.isChecked());
            //đăng nhập thành công sẽ finish();
            finish();
        }else {
            Toast.makeText(this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    //làm mới form về rỗng khi nhấn vào button trở lại
    public void resetForm(View view) {
        //cho u và p bằng rỗng khi người dùng kích vào button
        etUsername.setText("");
        etPassword.setText("");
    }

    //ghi nhớ pass và user vào ô checkbox
    //TỰ ĐỘNG ĐĂNG NHẬP
    public  void checkRememberPass(String username,String password,boolean status){//status là  đúng sai
        SharedPreferences sharedPreferences =getSharedPreferences("USER_FILE",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();//edit là cho phép sửa
        if(!status){//nếu user và pass sai thì clear form
            editor.clear();
        }else {
            //thêm data vào file
            editor.putString("USERNAME",username);
            editor.putString("PASSWORD",password);
            //status là lưu khi đúng định dạng
            editor.putBoolean("REMEMBER",status);
        }
        editor.commit();
    }
}