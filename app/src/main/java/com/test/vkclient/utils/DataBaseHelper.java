package com.test.vkclient.utils;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.test.vkclient.model.City;
import com.test.vkclient.model.User;

/**
 * Created by Павел on 09.02.2016.
 * Класс для работы с базой данных (кеширование)
 * В кеш записываются данные о пользователе
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    static final String dbName="VKData";
    static final String userTable="user";
    static final String colID="userId";
    static final String colName="userName";
    static final String colLastName = "userLastName";
    static final String colCity="userCity";
    static final String colPhoto="userPhoto";

    public DataBaseHelper(Context context) {
        super(context, dbName, null,33);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + userTable + " (" + colID + " INTEGER PRIMARY KEY , " +
                colName + " TEXT, " + colLastName + " TEXT, " + colCity + " TEXT, " + colPhoto + " TEXT);");

        /*db.execSQL("CREATE TABLE "+employeeTable+"
        ("+colID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
        colName+" TEXT, "+colAge+" Integer, "+colDept+"
        INTEGER NOT NULL ,FOREIGN KEY ("+colDept+") REFERENCES
        "+deptTable+" ("+colDeptID+"));");*/



        //Inserts pre-defined departments
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void writeToDatabase(SQLiteDatabase db, User user) {
        ContentValues cv = new ContentValues();
         cv.put(colName, user.getFirst_name());
         cv.put(colLastName, user.getLast_name());
         cv.put(colCity, user.getCity().getTitle());
         cv.put(colPhoto, user.getPhoto_max());
         db.insert(userTable, colID, cv);
         db.close();
    }

    public User readDatabase(SQLiteDatabase db) {
        User user = new User();
        Cursor c = db.query(userTable, null, null, null, null, null, null);

        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {

            // определяем номера столбцов по имени в выборке
            int idColIndex = c.getColumnIndex(colID);
            int nameColIndex = c.getColumnIndex(colName);
            int lastNameColIndex = c.getColumnIndex(colLastName);
            int cityColIndex = c.getColumnIndex(colCity);
            int photoColIndex = c.getColumnIndex(colPhoto);

            do {
                // получаем значения по номерам столбцов и пишем все в лог
                user.setFirst_name(c.getString(nameColIndex));
                user.setLast_name(c.getString(lastNameColIndex));
                user.setCity(new City(c.getString(cityColIndex)));
                user.setPhoto_max(c.getString(photoColIndex));

            } while (c.moveToNext());
        } else
            Log.d("DBERROR", "0 rows");
        c.close();
        return user;
    }
}
