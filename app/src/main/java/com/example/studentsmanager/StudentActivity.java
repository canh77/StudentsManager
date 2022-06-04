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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentsmanager.models.ClassManager;
import com.example.studentsmanager.models.ClassManagerDAO;
import com.example.studentsmanager.models.StudentManager;
import com.example.studentsmanager.models.StudentManagerDAO;
import com.example.studentsmanager.test.KiemTra;

import java.util.ArrayList;
import java.util.List;

public class StudentActivity extends AppCompatActivity {
    Spinner spnClass;
    EditText etIdStudent,etNameStudent,etDay,etEmail;
    Button btnSaveStudent,btnCancelStudent,btnUpdateStudent,btnDeleteStudent;
    ListView lvStudent;
    StudentManagerDAO stDAO ;

    ArrayAdapter adapter;

    List<ClassManager> lsClass = new ArrayList<>();
    ClassManagerDAO clsDao = null;

    //lấy id của class đưọc chọn
    String strIDClass = null;
    TextView idClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        //Mapping XML
        etIdStudent = (EditText) findViewById(R.id.etIdStudent);
        etNameStudent = (EditText) findViewById(R.id.etNameStudent);
        etDay = (EditText) findViewById(R.id.etDay);
        etEmail = (EditText) findViewById(R.id.etEmail);
        spnClass = (Spinner) findViewById(R.id.spnClass);
        btnCancelStudent = (Button) findViewById(R.id.btnCancelStudent);
        btnDeleteStudent = (Button) findViewById(R.id.btnDeleteStudent);
        btnSaveStudent = (Button) findViewById(R.id.btnSaveStudent);
        btnUpdateStudent = (Button) findViewById(R.id.btnUpdateStudent);

        idClass = findViewById(R.id.idClass);

        lvStudent = (ListView) findViewById(R.id.lvStudent);

        //lấy danh sách lớp học
        getClassRoom();
        //lấy id của class được chọn
        getIdClass();
        //Đổ dữ liệu lên ListView
        showListView();
    }
    public  void showListView() {
        stDAO = new StudentManagerDAO(StudentActivity.this);
        //lưu data từ form xuống lv
        adapter = new ArrayAdapter<StudentManager>
                (this, android.R.layout.simple_list_item_1,
                        stDAO.getAllStudent());
        lvStudent.setAdapter(adapter);

        //đổ dữu liệu ListView lên lại forrm
        lvStudent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //lấy in4 sv từ vị trí position và cập nhật lại forrm
                StudentManager st = stDAO.getAllStudent().get(position);
                etIdStudent.setText(st.getId());
                etNameStudent.setText(st.getName());
                etDay.setText(st.getDay());
                etEmail.setText(st.getEmail());
                idClass.setText(st.getIdClass());
            }
        });
    }
    //gọi spn để chọn khi click
        public  void getIdClass(){
        spnClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strIDClass = lsClass.get(spnClass.
                        getSelectedItemPosition()).getId();
                idClass.setText(strIDClass);
                Toast.makeText(getApplicationContext(),"SV Lớp"+strIDClass, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    //gọi riêng hàm spiner
    public  void getClassRoom(){
        clsDao = new ClassManagerDAO(StudentActivity.this);
        lsClass =clsDao.getAllClass();
        //hiện ra 1 spinner đổ xuống
        //lấy all class từ database
        ArrayAdapter<ClassManager> adapter =
                new ArrayAdapter<>(this,
                        android.R.layout.
                                simple_spinner_dropdown_item,
                        clsDao.getAllClass());
        spnClass.setAdapter(adapter);
    }

    public void resetForm(View view) {
        etIdStudent.setText("");
        etNameStudent.setText("");
        etDay.setText("");
        etEmail.setText("");
        idClass.setText("");
    }
    //save data
    public void addStudent(View view) {
        try {
            StringBuilder sb = new StringBuilder();

            KiemTra.checkEmpty(etIdStudent, sb, "Bạn chưa nhập mã sinh viên.\n");
            KiemTra.checkEmpty(etNameStudent, sb, "Bạn chưa nhập tên sinh viên.\n");
            KiemTra.checkDate(etDay, sb);
            KiemTra.checkEmail(etEmail, sb);

            //Thông báo khi có lỗi sai
            if (sb.length() > 0) {
                Toast.makeText(getApplicationContext(), sb, Toast.LENGTH_SHORT).show();
                return;
            }
            StudentManager st = new StudentManager();
            st.setId(etIdStudent.getText().toString());
            st.setName(etNameStudent.getText().toString());
            st.setDay(etNameStudent.getText().toString());
            st.setEmail(etEmail.getText().toString());
            st.setIdClass(idClass.getText().toString());


            stDAO = new StudentManagerDAO(StudentActivity.this);


            int rs = stDAO.addStudent(st);
                if (rs > 0) {
                    Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    capNhatLv();
                    resetForm(view);
                } else {
                    Toast.makeText(this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception ex) {
                Log.e("Error", ex.toString());
            }
        }

            //update data
            public void updateStudent (View view){

                try {
                    StringBuilder sb = new StringBuilder();

                    KiemTra.checkEmpty(etIdStudent, sb, "Bạn chưa nhập mã sinh viên.\n");
                    KiemTra.checkEmpty(etNameStudent, sb, "Bạn chưa nhập tên sinh viên.\n");
                    KiemTra.checkDate(etDay, sb);
                    KiemTra.checkEmail(etEmail,sb);

                    //Thông báo khi có lỗi sai
                    if(sb.length() > 0){
                        Toast.makeText(getApplicationContext(), sb, Toast.LENGTH_SHORT).show();
                        return;
                    }
                StudentManager st = new StudentManager();
                st.setId(etIdStudent.getText().toString());
                st.setName(etNameStudent.getText().toString());
                st.setDay(etDay.getText().toString());
                st.setEmail(etEmail.getText().toString());
                st.setIdClass(strIDClass);

                int rs = stDAO.updateStudent(st);
                    if (rs >= 0) {
                        Toast.makeText(this, "Sửa thành công", Toast.LENGTH_SHORT).show();
                        capNhatLv();
                        resetForm(view);
                    } else {
                        Toast.makeText(this, "Không tìm thấy", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    Log.e("Error", ex.toString());
                }
            }

            //delete data
            public void deleteStudent (View view) {

                try {
                    StringBuilder sb = new StringBuilder();

                    KiemTra.checkEmpty(etIdStudent, sb, "Bạn chưa nhập mã sinh viên.\n");

                    //Thông báo khi có lỗi sai
                    if (sb.length() > 0) {
                        Toast.makeText(getApplicationContext(), sb, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    int rs = stDAO.deleteStudent(etIdStudent.getText().toString());
                        if (rs >= 0) {
                            Toast.makeText(this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            capNhatLv();
                            resetForm(view);
                        } else {
                            Toast.makeText(this, "Không tìm thấy mã", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception ex) {
                        Log.e("Error", ex.toString());
                    }
                }
            void capNhatLv(){
                adapter = new ArrayAdapter<StudentManager>
                        (this, android.R.layout.simple_list_item_1
                                , stDAO.getAllStudent());
                lvStudent.setAdapter(adapter);
            }
}