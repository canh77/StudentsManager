package com.example.studentsmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.studentsmanager.models.ClassManager;
import com.example.studentsmanager.models.ClassManagerDAO;
import com.example.studentsmanager.test.KiemTra;

public class ClassActivity extends AppCompatActivity {
    //khai bao xml Activityclass
    EditText etIdClass , etNameClass;
    Button btnSaveClass,btnCancelClass,btnUpdateClass,btnDeleteClass;
    ListView lvClass;
    //khai báo thêm 1 classDAO để quản lí classManager
    ClassManagerDAO dao ;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);
        //Mapping XML
        etIdClass =(EditText) findViewById(R.id.etIdClass);
        etNameClass =(EditText) findViewById(R.id.etNameClass);
        btnDeleteClass=(Button)findViewById(R.id.btnDeleteClass);
        btnUpdateClass=(Button)findViewById(R.id.btnUpdateClass);
        btnSaveClass =(Button) findViewById(R.id.btnSaveClass);
        btnCancelClass=(Button) findViewById(R.id.btnCancelClass);
        lvClass = (ListView) findViewById(R.id.lvClass);

        dao = new ClassManagerDAO(ClassActivity.this);

        //tạo ra 1 ArrayAdapter để gọi dữ liệu bên DAO về
         adapter =
                new ArrayAdapter<ClassManager>(
                        this, android.R.layout.simple_list_item_1,dao.getAllClass());
        //setAdapter sẽ đổ dữu liệu ra lv
        lvClass.setAdapter(adapter);

        //đổ dữ liệu có sẵn trong lv lên form khi người dùng click vào lvClass
        lvClass.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //lấy vị trí mà người dùng click vào
                ClassManager cls = dao.getAllClass().get(position);
                etIdClass.setText(cls.getId());
                etNameClass.setText(cls.getName());
            }
        });

    }

    //reset form
    public void resetForm(View view) {
        etIdClass.setText("");
        etNameClass.setText("");
    }


    //lưu data
    public void addClass(View view) {
        try {
            StringBuilder sb = new StringBuilder();
            KiemTra.checkEmpty(etIdClass, sb,
                    "Bạn chưa nhập mã lớp\n");
            KiemTra.checkEmpty(etNameClass, sb,
                    "Bạn chưa nhập tên lớp");
            //thông báo khi có lỗi sai
            if (sb.length() > 0) {
                Toast.makeText(getApplicationContext(),
                        sb, Toast.LENGTH_SHORT).show();
                return;
            }

            //khai báo thêm 1 classDAO để quản lí classManager
            //sử dụng lớp classManager để tạo và đưa về kiểu chuỗi để ng dùng nhập dữu liệu vào
            ClassManager cls = new ClassManager(
                    etIdClass.getText().toString(),
                    etNameClass.getText().toString());
            //tạo ra biến resultText để gọi hàm dao để add dữu liệu vào
            int rs = dao.add(cls);
            if (rs > 0) {
                Toast.makeText(getApplicationContext(), "Thêm lớp thành công",
                        Toast.LENGTH_SHORT).show();

                capNhatLv();
                resetForm(view);
            } else {
                Toast.makeText(getApplicationContext(), "Có lỗi thêm thất bại", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Log.e("ERROR add:",e.toString());
        }
    }


        //hàm update
        public void updateClass (View view){
            try {
                StringBuilder sb = new StringBuilder();

                KiemTra.checkEmpty(etIdClass, sb, "Bạn chưa nhập Mã Lớp.\n");
                KiemTra.checkEmpty(etNameClass, sb, "Bạn chưa nhập Tên Lớp.");

                //Thông báo khi có lỗi sai
                if(sb.length() > 0){
                    Toast.makeText(getApplicationContext(), sb, Toast.LENGTH_SHORT).show();
                    return;
                }
            //trả về kiểu chuỗi chon người dùng nhập vào
            ClassManager cls = new ClassManager(etIdClass.getText().toString(), etNameClass.getText().toString());
            //tạo ra biến rs để gọi hàm dao update dữ liệu vào
            int rs = dao.update(cls);
            if (rs >= 0) {
                Toast.makeText(getApplicationContext(), "Sửa thành công", Toast.LENGTH_SHORT).show();
                //đổ data vừa lưu hiện lên lv

                //cập nhật lại lv
                capNhatLv();
                resetForm(view);
            } else {
                Toast.makeText(getApplicationContext(), "Không tìm thấy Mã lớp", Toast.LENGTH_SHORT).show();
            }}catch (Exception e){
                Log.e("ERROR UpdateClass: ",e.toString());
            }
        }

        //hàm delete
        public void deleteClass (View view){
            try {
                StringBuilder sb = new StringBuilder();

                KiemTra.checkEmpty(etIdClass, sb, "Bạn chưa nhập Mã Lớp.");
                //Thông báo khi có lỗi sai
                if (sb.length() > 0) {
                    Toast.makeText(getApplicationContext(), sb, Toast.LENGTH_SHORT).show();
                    return;
                }
            //tạo ra biến rs để gọi hàm dao update dữ liệu vào
            int rs = dao.delete(etIdClass.getText().toString());
            if (rs >= 0) {
                Toast.makeText(getApplicationContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();

                // Cap nhat lai ListView
                capNhatLv();

                resetForm(view);
            } else {
                Toast.makeText(getApplicationContext(), "Không tìm thấy Mã lớp", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e) {
                Log.e("ERROR delClass: ", e.toString());
            }
    }
    void capNhatLv(){
        //cập nhật lại lv
        adapter = new ArrayAdapter<ClassManager>
                (this, android.R.layout.simple_list_item_1,
                        dao.getAllClass());
        //đổ dữ liệu vừa lưu hiện lên listview
        lvClass.setAdapter(adapter);
    }
}