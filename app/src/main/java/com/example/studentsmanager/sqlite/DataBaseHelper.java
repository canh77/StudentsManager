package com.example.studentsmanager.sqlite;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "dbStudentManager";
    public static final int VERSION_DB = 1;
    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION_DB);
    }

    //phải gỡ app và chạy lại thì mới tạo ra database mới được vì mối lần chạy chỉ tạo ra 1 database thôi
    @Override
    public void onCreate(SQLiteDatabase db){
        //tạo ra bảng sql_lite database\
        // Tạo bảng classroom
        String sSQL_ClassRoom =
                "CREATE TABLE ClassRooms (" +
                        "MaLop text PRIMARY KEY," +
                        "TenLop text)";
        db.execSQL(sSQL_ClassRoom);

        // tạo bảng student
        String sSQL_Students =
                "CREATE TABLE students (" +
                        "MaSV text PRIMARY KEY," +
                        "TenSV text,NgaySinh text," +
                        "Email text," +
                        "IdClass text)";
        db.execSQL(sSQL_Students);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("Drop table if exists sSQL_ClassRooms");
        db.execSQL("drop table if exists students");
        onCreate(db);
    }
}
