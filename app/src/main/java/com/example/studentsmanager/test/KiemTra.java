package com.example.studentsmanager.test;

import android.graphics.Color;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class KiemTra {
    //kiểm tra trống
    public  static boolean checkEmpty (EditText ed,StringBuilder sb,String smg){
        boolean check = true;
        if (String.valueOf(ed.getText()).equals("")){
            sb.append(smg).append("");
            check = false;
        }else {
            ed.setTextColor(Color.BLACK);
        }
        return  check;
    }

    //kiểm tra ngày sinh
    public static  boolean checkDate(EditText ed,StringBuilder sb){
        boolean check = true;
        if (!checkEmpty(ed,sb,"Bạn chưa nhập ngày sinh")){
            return  false;
        }
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat();
            dateFormat.applyPattern("dd-MM-yyyyy");
            Date date = dateFormat.parse(String.valueOf(ed.getText()));
        } catch (Exception ex) {
            sb.append("Ngày sinh không đúng định dạng(ngày-tháng -năm)");
            ed.setTextColor(Color.RED);
            check = false;
        }
        if (check) ed.setTextColor(Color.BLACK);
        return  check;
    }
    //Kiểm tra Email
    public static boolean checkEmail(EditText ed,StringBuilder sb){
        boolean check = true;
        if (!checkEmpty(ed,sb,"Bạn chưa nhập Email")){
            return  false;
        }
        if (!(String.valueOf(ed.getText())).
                matches("\\w+@\\w+\\.\\w+")){
            sb.append("Email không hợp lệ");
            ed.setTextColor(Color.RED);
            check = false;
        }
        if (check) ed.setTextColor(Color.BLACK);
        return check;
    }

}
