package com.example.studentsmanager.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.studentsmanager.sqlite.DataBaseHelper;

import java.util.ArrayList;
import java.util.List;

//trong StudentManagerDAO thì sẽ viết hàm thêm nhập sửa xóa database
public class StudentManagerDAO {
    private SQLiteDatabase db;
    private SQLiteOpenHelper helper;

    //khai báo hàm construc tỏ ko có tham số và thêm helper và db
    public StudentManagerDAO(Context context){
        helper = new DataBaseHelper(context);
        db = helper.getWritableDatabase();
    }

//insert data
    public int addStudent(StudentManager st){
        //tạo hàm contentValue và thêm các tham số
        ContentValues values = new ContentValues();
        values.put("MaSV",st.getId());
        values.put("TenSV",st.getName());
        values.put("NgaySinh",st.getDay());
        values.put("Email",st.getEmail());
        values.put("IdClass",st.getIdClass());

        try {
          if (db.insert("students",null,values) == -1)
                return  -1;
        }catch (Exception ex){
            Log.e("StudentDAO Error:",ex.toString());
        }
        return  1;
    }

    //getAll
    public List<StudentManager> getAllStudent(){
        List<StudentManager> ls = new ArrayList<>();
        //con trỏ
        Cursor c = db.query("students",null,null,null,null,null,null);
        c.moveToFirst();

        while (c.isAfterLast() ==false){
            StudentManager st = new StudentManager();
            st.setId(c.getString(0));//lấy cột 1
            st.setName(c.getString(1));
            st.setDay(c.getString(2));
            st.setEmail(c.getString(3));
            st.setIdClass(c.getString(4));
            //thêm vào list
            ls.add(st);
            //di chuyển đến vị trí tiếp theo
            c.moveToNext();
        }
        c.close();
        return  ls;
    }

    //update data
    public  int updateStudent(StudentManager st ){
        ContentValues values = new ContentValues();
        values.put("MaSV",st.getId());
        values.put("TenSV",st.getName());
        values.put("NgaySinh",st.getDay());
        values.put("Email",st.getEmail());
        values.put("IdClass",st.getIdClass());
        //update theo id
        int rs  = db.update("students",values,"MaSV=?",new String[]{st.getId()});
        try {
            if(rs == 0)
                return -1;
        }catch (Exception ex){
            Log.e("StudentDAO Error:",ex.toString());
        }
        return 1;
    }

    //Delete data
    public  int deleteStudent(String id){
        int rs = db.delete("students","MaSV=?",new String[]{id});
        if (rs ==0)//nếu result =0 thì deleta thành công
            return -1;
        return 1;
    }
}
