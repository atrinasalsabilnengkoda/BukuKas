package com.example.financekasbkn;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DatabaseHelper extends SQLiteOpenHelper {

    //Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "FinanceBKN.db";

    // User table name
    private static final String TABLE_USER = "user";
    private static final String TABLE_CASH_FLOW = "cash_flow";

    // User Table Columns names
    private static final String COLUMN_USER_ID = "id_user";
    private static final String COLUMN_USER_NAME = "username";
    private static final String COLUMN_USER_PASSWORD = "userpassword";

    // Pemasukan Table Columns names
    private static final String COLUMN_CASH_FLOW_ID = "id_cash_flow";
    private static final String COLUMN_CASH_FLOW_TANGGAL = "cash_flow_tanggal";
    private static final String COLUMN_CASH_FLOW_BULAN = "cash_flow_bulan";
    private static final String COLUMN_CASH_FLOW_TAHUN = "cash_flow_tahun";
    private static final String COLUMN_CASH_FLOW_NOMINAL = "cash_flow_nominal";
    private static final String COLUMN_CASH_FLOW_KETERANGAN = "cash_flow_keterangan";
    private static final String COLUMN_CASH_FLOW_TIPE = "cash_flow_tipe";


    // create table user
    private String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_PASSWORD + " TEXT" + ")";

    // create table pemasukan
    private String CREATE_CASH_FLOW_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CASH_FLOW + "("
            + COLUMN_CASH_FLOW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_CASH_FLOW_TANGGAL + " TEXT,"
            + COLUMN_CASH_FLOW_BULAN + " TEXT," + COLUMN_CASH_FLOW_TAHUN + " TEXT,"
            + COLUMN_CASH_FLOW_NOMINAL + " INTEGER," + COLUMN_CASH_FLOW_KETERANGAN + " TEXT,"
            + COLUMN_CASH_FLOW_TIPE + " TEXT" + ")";

    // insert user table
    private String INSERT_USER_TABLE = "INSERT INTO " + TABLE_USER + "("
            + COLUMN_USER_NAME + "," + COLUMN_USER_PASSWORD + ") "
            + "VALUES('admin','admin')";

    // drop table user
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    // drop table pemasukan
    private String DROP_CASH_FLOW_TABLE = "DROP TABLE IF EXISTS " + TABLE_CASH_FLOW;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_CASH_FLOW_TABLE);
        db.execSQL(INSERT_USER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop User Table if exist
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_CASH_FLOW_TABLE);
        // Create tables again
        onCreate(db);
    }

    public User checkUser(User user){
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_NAME,
                COLUMN_USER_PASSWORD
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_NAME + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";
        // selection arguments
        String[] selectionArgs = {user.getUsername(),user.getPassword()};
        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();

        if(cursorCount > 0) {
            if (cursor.moveToFirst()) {
                user.setUserid(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_ID))));
                user.setUsername(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_NAME)));
                user.setPassword(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_PASSWORD)));

                cursor.close();
                return user;
            } else {
                return null;
            }

        }else{
            return null;
        }
    }

    public boolean checkOldPassword(String oldPassword) {
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_NAME,
                COLUMN_USER_PASSWORD,
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_PASSWORD + " = ?";
        // selection arguments
        String[] selectionArgs = {oldPassword};
        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        return cursorCount > 0;

    }

    public User updatePassword(User user, String newPassword) {

        user.setPassword(newPassword);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String[] selectionArgs = {String.valueOf(user.getUserid())};

        values.put(COLUMN_USER_PASSWORD, newPassword);
        // updating row
        db.update(TABLE_USER, values, COLUMN_USER_ID + " = ?", selectionArgs);
        db.close();
        return user;
    }

    public void addCashFlow(CashFlow cf) {
        ContentValues values = new ContentValues();

        SQLiteDatabase db = this.getWritableDatabase();

        values.put(COLUMN_CASH_FLOW_NOMINAL, cf.getNominal());
        values.put(COLUMN_CASH_FLOW_KETERANGAN, cf.getKeterangan());
        values.put(COLUMN_CASH_FLOW_TANGGAL, cf.getTanggal());
        values.put(COLUMN_CASH_FLOW_BULAN, cf.getBulan());
        values.put(COLUMN_CASH_FLOW_TAHUN, cf.getTahun());
        values.put(COLUMN_CASH_FLOW_TIPE, cf.getTipe());
        // Inserting Row
        db.insert(TABLE_CASH_FLOW, null, values);
        db.close();
    }

    public List<CashFlow> getNominalPemasukanByMonth(String month) {

        int bulan = Integer.parseInt(month);

        String[] columns = {
                COLUMN_CASH_FLOW_NOMINAL
        };
        // sorting orders
        String sortOrder =
                COLUMN_CASH_FLOW_ID + " ASC";
        List<CashFlow> cashFlowList = new ArrayList<CashFlow>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_CASH_FLOW_BULAN + " = ?" + " AND " + COLUMN_CASH_FLOW_TIPE + " = ?";
        String[] selectionArgs = {String.valueOf(bulan), "pemasukan"};
        Cursor cursor = db.query(TABLE_CASH_FLOW, //Table to query
                columns,    //columns to return
                selection,        //columns for the WHERE clause
                selectionArgs,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order
        // Traversing through all rows and adding to list
        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    CashFlow cfPemasukan = new CashFlow();
                    cfPemasukan.setNominal(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CASH_FLOW_NOMINAL))));

                    cashFlowList.add(cfPemasukan);
                } while (cursor.moveToNext());
                cursor.close();
                db.close();
            }
        }
        return cashFlowList;
    }

    public List<CashFlow> getNominalPengeluaranByMonth(String month) {

        int bulan = Integer.parseInt(month);

        String[] columns = {
                COLUMN_CASH_FLOW_NOMINAL
        };
        // sorting orders
        String sortOrder =
                COLUMN_CASH_FLOW_ID + " ASC";
        List<CashFlow> cashFlowList = new ArrayList<CashFlow>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_CASH_FLOW_BULAN + " = ?" + " AND " + COLUMN_CASH_FLOW_TIPE + " = ?";
        String[] selectionArgs = {String.valueOf(bulan), "pengeluaran"};
        Cursor cursor = db.query(TABLE_CASH_FLOW, //Table to query
                columns,    //columns to return
                selection,        //columns for the WHERE clause
                selectionArgs,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order
        // Traversing through all rows and adding to list
        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    CashFlow cfPemasukan = new CashFlow();
                    cfPemasukan.setNominal(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CASH_FLOW_NOMINAL))));

                    cashFlowList.add(cfPemasukan);
                } while (cursor.moveToNext());
                cursor.close();
                db.close();
            }
        }
        return cashFlowList;
    }

    public List<CashFlow> getAllCashFlow(){

        String[] columns = {
                COLUMN_CASH_FLOW_ID,
                COLUMN_CASH_FLOW_NOMINAL,
                COLUMN_CASH_FLOW_KETERANGAN,
                COLUMN_CASH_FLOW_TANGGAL,
                COLUMN_CASH_FLOW_BULAN,
                COLUMN_CASH_FLOW_TAHUN,
                COLUMN_CASH_FLOW_TIPE
        };
        // sorting orders
        String sortOrder =
                COLUMN_CASH_FLOW_ID + " ASC";
        List<CashFlow> cashFlowList = new ArrayList<CashFlow>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CASH_FLOW, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order
        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CashFlow cf = new CashFlow();

                cf.setIdCashFlow(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CASH_FLOW_ID))));
                cf.setNominal(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CASH_FLOW_NOMINAL))));
                cf.setKeterangan(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CASH_FLOW_KETERANGAN)));
                cf.setTanggal(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CASH_FLOW_TANGGAL)));
                cf.setBulan(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CASH_FLOW_BULAN)));
                cf.setTahun(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CASH_FLOW_TAHUN)));
                cf.setTipe(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CASH_FLOW_TIPE)));

                // Adding detail cash flow record to list
                cashFlowList.add(cf);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return user list
        return cashFlowList;
    }

}

