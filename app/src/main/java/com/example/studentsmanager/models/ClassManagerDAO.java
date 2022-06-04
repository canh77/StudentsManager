package com.example.studentsmanager.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.studentsmanager.sqlite.DataBaseHelper;

import java.util.ArrayList;
import java.util.List;

//classManagerDAO sẽ quản lí lun lớp ClassManager

public class ClassManagerDAO {
    private SQLiteDatabase db;
    private DataBaseHelper dbHelper;

    //hàm add dữu liệu vào class và lưu lại giá trị (public static)
//   public static List<ClassManager> lsClass = new ArrayList<>();

    //tạo hàm contructor ko tham số và gọi hàm dummydata
    //khi tạo ra 1 lsclass --> dummyData -->lsclass sẽ cso dữ liệu
    public ClassManagerDAO(Context context) {
        //sẽ ko nạp chồng data
//        dummyData();
       dbHelper = new DataBaseHelper(context);
       db = dbHelper.getWritableDatabase();
    }

    //thêm dữ liệu vào cls
    public  int add(ClassManager cls){
        //listview
//      lsClass.add(cls);

        //Lưu vào database
        ContentValues values = new ContentValues();
        values.put("MaLop",cls.getId());
        values.put("TenLop",cls.getName());

        try {
            if (db.insert("ClassRooms",null, values)==-1){
                return  -1;
            }
        }catch (Exception ex ){
            Log.e("ClassManagerDAO ERROR:","//===="+ex.toString());
        }
       return 1;
    }

    //hàm getAll tất cả dữ liệu sau khi add vào listclass
    public  List<ClassManager> getAllClass(){
        List<ClassManager> list = new ArrayList<>();

//        truy vấn con trỏ
        Cursor c = db.query("ClassRooms",null,null,null,null,null,null);
        c.moveToFirst();
        while (c.isAfterLast() == false){
           ClassManager cls = new ClassManager();
            cls.setId(c.getString(0));//lấy cột 1
            cls.setName(c.getString(1));//lấy cột 2
            list.add(cls);
            //di chuyển đến vị trí tiếp theo
           c.moveToNext();
        }
       c.close();

       return  list;
    }

    //muốn update thì phải tìm kiếm theo ID thì Update
    public  static List<ClassManager> listClass = new ArrayList<>();
    //tìm vị trí class theo mã lớp
    public  int findByID (String id) {
        //dùng vòng lặp for để lấy vị trí position của id
        for (int i = 0; i < getAllClass().size(); i++) {
            //lấy ptu thứ i ở trong mảng cls
            ClassManager cls = listClass.get(i);
            //nếu mà nhạp đúng cái i thì sẽ trả về đúng gái trị đó
            if (cls.getId().equals(id)){
                return i;
            }
        }
        //ngược lại sẽ trả về -1 vì ko tìm ra
        return -1;
    }

    //gọi hàm update bên DAO
    public int update(ClassManager cls) {
        //cạp nhật trên ArrayList
        //người dùng nhâp ID
//        int position = findByID(cls.getId());
///        //nếu người dùng nhập đúng ID thì sẽ trả về >0
//        if(position >= 0){
//            //nếu bị trí pos = cái cls(vị trí của chúng ta nhập vào)
//            lsClass.set(position,cls);
//        }
//        //ngược lại sẽ trả về position cũ
//        return position;


        //cập nhật trên databasse
        ContentValues values = new ContentValues();
        values.put("MaLop", cls.getId());
        values.put("TenLop", cls.getName());
        int rs = db.update("ClassRooms",values,"MaLop=?",new String[]{cls.getId()});
        try {
            if (rs == 0){
                return  -1;
            }
        }catch (Exception ex){
            Log.e("ClassManagerDAO ERROR:","//==="+ex.toString());
        }
        return 1;
    }

    //gọi hàm Delete bên Dao để gọi qua AcClass
    public int delete(String id){//chỉ lấy Id nên dùng String id
        /*Xoa trên ArrayList
        //vị trí id mà người dùng nhập vào
        int position = findByID(id);
        if(position >= 0){
            //nếu mà tìm được id là >=0 thì sẽ xóa cái vị trí pos id đó
            lsClass.remove(position);
        }
        //ngược lại sẽ trả về cái pos cũ
        return position;
        */

        //Xóa trên databasse
        int rs =db.delete("ClassRooms","MaLop=?",new String[]{id});
        if (rs == 0)
            return  -1;
        return  1;
    }



    //Hàm để hiển thị set dữ liệu mẫu vào listview
//    public  void dummyData(){
//        lsClass.add(new ClassManager("CP16309","LẬP TRÌNH ANDROID CƠ BẢN"));
//        lsClass.add(new ClassManager("PT16310","THIẾT KẾ ĐỒ HỌA"));
//        lsClass.add(new ClassManager("MOB103","LẬP TRÌNH JAVA"));
//    }

}
