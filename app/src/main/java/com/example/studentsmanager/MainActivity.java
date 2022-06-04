package com.example.studentsmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //khai báo bên XML main button
    Button btnClassManager,btnStudentManager,btnDangXuat;
    //khi bạn Intent nó qua thì hãy khai báo cái intent phía bên ngoài bằng null
    Intent intent = null;
    //khai báo kiểu string để check
    String strUsername = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Mapping XML button hàm main
        btnClassManager =(Button)findViewById(R.id.btnClassManager);
        btnStudentManager=(Button) findViewById(R.id.btnStudentManager);
        btnDangXuat = findViewById(R.id.btnDangXuat);

        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
        //hiển thị Activity lên đầu tiên và chuyển sang Login
        //khi bạn Intent nó qua thì hãy khai báo cái intent phía bên ngoài bằng null và xóa Intent đi

        if(checkLoginRemember()<0) {//nếu mà ko lưu thì lần sao login phải ghi lại u và p tiếp
            intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    //từ hàm main click vào button quản lí lớp thì sẽ chạy vào ac chi tiết của ql lớp
    public void classManager(View view) {
        intent = new Intent(MainActivity.this,ClassActivity.class);
        startActivity(intent);
    }

    //chuyền file từ login sang main để check ghi nhớ dữ liệu
    public int checkLoginRemember(){
        SharedPreferences sharedPreferences = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        boolean check = sharedPreferences.getBoolean("REMEMBER",false);
        if(check){//nếu đúng p&u thì sẽ lưu và return về 1
            strUsername =sharedPreferences.getString("USERNAME","");
            return  1;
        }
        return -1;
    }

    public void studentManager(View view) {
        intent = new Intent(MainActivity.this,StudentActivity.class);
        startActivity(intent);
    }


}