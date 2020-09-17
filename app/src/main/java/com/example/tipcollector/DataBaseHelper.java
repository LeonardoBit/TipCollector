package com.example.tipcollector;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.sql.Date;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.IsoFields;
import java.util.ArrayList;

import java.util.List;



public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DAYS_TABLE = "DAYS_TABLE";
    public static final String COLUMN_DAY_DATE = "DAY_DATE";
    public static String COLUMN_WEEK = "WEEK_YEAR";
    public static final String COLUMN_TIP_CASH = "TIP_CASH";
    public static final String COLUMN_TIP_CARD = "TIP_CARD";
    public static final String COLUMN_TIP_SUM = "TIP_SUM";
    public static final String COLUMN_ID = "ID";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "days.db",null,1);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement= "CREATE TABLE " + DAYS_TABLE + "(" +
                COLUMN_ID +
                " INTEGER PRIMARY KEY , " +
                COLUMN_DAY_DATE +
                " DATE, " +
                COLUMN_WEEK +
                " INT, " +
                COLUMN_TIP_CASH +
                " INT, " +
                COLUMN_TIP_CARD +
                " INT, " +
                COLUMN_TIP_SUM +
                " INT) ";

        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DAYS_TABLE);
    }

    public boolean addOneDay (DayModel dayModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();



        cv.put(COLUMN_DAY_DATE, String.valueOf(dayModel.getDayDate()));
        cv.put(COLUMN_WEEK, dayModel.getWeekOfYear());
        cv.put(COLUMN_TIP_CASH,dayModel.getCash());
        cv.put(COLUMN_TIP_CARD,dayModel.getCard());
        cv.put(COLUMN_TIP_SUM,dayModel.getSum());


        long insert = db.insert(DAYS_TABLE,null,cv);
        if(insert == -1){
            return false;
        }
        else{
            return true;
        }

    }

    public boolean deleteOne(DayModel dayModel){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + DAYS_TABLE + " WHERE " + COLUMN_ID + " = " + dayModel.getId();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            return true;
        }else{
            return false;
        }
    }

    public List<DayModel> getAll(){
        List<DayModel> returnList = new ArrayList<>();
        //get data from the database

        String queryString = "SELECT * FROM " + DAYS_TABLE + " ORDER BY " + COLUMN_DAY_DATE + " DESC " ;


        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString,null);

        if(cursor.moveToFirst()){
            //loop through the cursor(result set) and create new day objects
            do{
                int dayId = cursor.getInt(0);
                Date dayDate = Date.valueOf(cursor.getString(1));
                int week = cursor.getInt(2);
                int tipCash = cursor.getInt(3);
                int tipCard = cursor.getInt(4);
                int tipSum = cursor.getInt(5);




                DayModel newDay = new DayModel(dayId,dayDate,week,tipCash,tipCard,tipSum);
                returnList.add(newDay);

            }while (cursor.moveToNext());

        }else{
            //failure.do not add anything to the list.
        }

        //close database and cursor
        cursor.close();
        db.close();

        return returnList;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<DayModel> getCurrentWeek(){
        List<DayModel> returnList = new ArrayList<>();

        LocalDate ld = LocalDate.now();
        int weekNumber = ld.get( IsoFields.WEEK_OF_WEEK_BASED_YEAR ) ;

        String queryString = "SELECT * FROM " + DAYS_TABLE + " WHERE "+ COLUMN_WEEK+  "=" +  weekNumber +  " ORDER BY " + COLUMN_DAY_DATE + " DESC " ;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString,null);
        if(cursor.moveToFirst()){
            //loop through the cursor(result set) and create new day objects
            do{
                int dayId = cursor.getInt(0);
                Date dayDate = Date.valueOf(cursor.getString(1));
                int week = cursor.getInt(2);
                int tipCash = cursor.getInt(3);
                int tipCard = cursor.getInt(4);
                int tipSum = cursor.getInt(5);




                DayModel newDay = new DayModel(dayId,dayDate,week,tipCash,tipCard,tipSum);
                returnList.add(newDay);

            }while (cursor.moveToNext());

        }else{
            //failure.do not add anything to the list.
        }

        //close database and cursor
        cursor.close();
        db.close();


        return returnList;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<DayModel> getCurrentMonth(){
        List<DayModel> returnList = new ArrayList<>();
        LocalDate ld = LocalDate.now();

        int yearNumber = ld.getYear();
        int monthNumber = ld.getMonth().getValue();

        String queryString = "SELECT " +
                " *, " +
                "strftime('%m', DAY_DATE) as Month, " +
                "strftime('%Y', DAY_DATE) as Year   " +
                " FROM " + DAYS_TABLE +
                " WHERE "+"(Month+0)" +
                "=" + monthNumber +
                " AND " + "(Year+0)"+
                "=" + yearNumber +
                " ORDER BY " + COLUMN_DAY_DATE +
                " DESC " ;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString,null);
        if(cursor.moveToFirst()){
            //loop through the cursor(result set) and create new day objects
            do{
                int dayId = cursor.getInt(0);
                Date dayDate = Date.valueOf(cursor.getString(1));
                int week = cursor.getInt(2);
                int tipCash = cursor.getInt(3);
                int tipCard = cursor.getInt(4);
                int tipSum = cursor.getInt(5);




                DayModel newDay = new DayModel(dayId,dayDate,week,tipCash,tipCard,tipSum);
                returnList.add(newDay);

            }while (cursor.moveToNext());

        }else{
            //failure.do not add anything to the list.
        }

        //close database and cursor
        cursor.close();
        db.close();


        return returnList;
    }


}
