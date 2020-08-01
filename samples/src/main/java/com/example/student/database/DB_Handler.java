package com.example.student.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.student.model.Exam;


import java.util.ArrayList;

public class DB_Handler extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "QR_db";

    //Table Details

    public static final String TABLE_NAME_DETAILS = "qr_details";
    public static final String COLUMN_ID = "id_details";
    public static final String QR_DETAILS = "student_details";
    public static final String SERVICE_DETAILS = "service_details";
    public static final String STATUS = "qr_status";


    public DB_Handler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) 

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_DETAILS);

        // Create tables again
        onCreate(db);
    }


    public void insertqr(Exam exam) {
        // get writable database as we want to write data


        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(QR_DETAILS, exam.getQr_data());
        values.put(SERVICE_DETAILS, exam.getService_data());
        values.put(STATUS,exam.getStatus());
        // insert row
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NAME_DETAILS, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
  //      return id;
    }

    public ArrayList<Exam> getExamDetails() {

        String query = "SELECT * FROM " +
                TABLE_NAME_DETAILS;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if (!cursor.moveToFirst()) {
            return null;
        } else {
            ArrayList<Exam> listExam = new ArrayList<>();
            do {
                Exam exam = new Exam();
                //exam.setQr_data(cursor.getString(1));
                exam.setService_data(cursor.getString(2));
               // exam.setStartTime(cursor.getString(3));
                listExam.add(exam);

            } while (cursor.moveToNext());
            cursor.close();
            sqLiteDatabase.close();
            return listExam;
        }
    }
    public void deleteqr(Exam exam) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_DETAILS, STATUS + " = ?",
                new String[]{String.valueOf(exam.STATUS_NO)});
        db.close();
    }
    public String getStudentDetails(int position)
    {

        String query = "SELECT * FROM " +
                TABLE_NAME_DETAILS +
                " WHERE " +
                COLUMN_ID +
                " = \"" +
                position+
                "\"";
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if(!cursor.moveToFirst()) {
        return null;
        } else {
            String fieldDetails;
            do {
                fieldDetails =  cursor.getString(1);
                // exam.setStartTime(cursor.getString(3));

            } while (cursor.moveToNext());
        cursor.close();
        sqLiteDatabase.close();
        return fieldDetails;
    }
    }

    public String getStudentService(int position)
    {

        String query = "SELECT * FROM " +
                TABLE_NAME_DETAILS;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if(!cursor.moveToFirst()) {
            return null;
        } else {
            String fieldDetails;
            do {
                fieldDetails =  cursor.getString(2);
                // exam.setStartTime(cursor.getString(3));

            } while (cursor.moveToNext());
            cursor.close();
            sqLiteDatabase.close();
            return fieldDetails;
        }
    }


}
