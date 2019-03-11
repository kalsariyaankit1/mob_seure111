package com.example.admin.mob_seure111;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class DatabaseHelper {

    // Table Name
    public static final String TABLE_NAME = "MODES";
    public static final String TABLE_NAME_CONTACT = "PRIMARY_CONTACT";
    public static final String TABLE_NAME_TRACK = "TRACK";

    // Table columns
    public static final String _ID = "_id";
    public static final String MODE_NAME = "mode_name";
    public static final String PIN = "pin";

    public static final String _CID = "_id";
    public static final String CONTACT_NAME = "contact_name";
    public static final String CONTACT1 = "contact1";
    public static final String CONTACT2 = "contact2";
    public static final String CONTACT3 = "contact3";
    public static final String CONTACT4 = "contact4";

    //track table
    public static final String _TID = "_id";
    public static final String CONTACT = "contact";
    public static final String CODE = "code";
    public static final String CREATED_TIME = "created_time";

    // Database Information
    static final String DB_NAME = "Mob_secDB";

    // database version
    static final int DB_VERSION = 11;

    // Creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + MODE_NAME + " TEXT NOT NULL, " + PIN + " TEXT);";

    private static final String CREATE_TABLE_TRACK = "create table " + TABLE_NAME_TRACK + "(" + _TID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CONTACT + " TEXT NOT NULL, " + CODE + " TEXT NOT NULL, " + CREATED_TIME + " TEXT NOT NULL);";

    private static final String CREATE_TABLE_CONTACT = "create table " + TABLE_NAME_CONTACT + "(" + _CID
            + " INTEGER PRIMARY KEY, " + CONTACT_NAME + " TEXT NOT NULL," + CONTACT1 + " TEXT NOT NULL, " +
            CONTACT2 + " TEXT NOT NULL," + CONTACT3 + " TEXT NOT NULL," + CONTACT4 + " TEXT NOT NULL);";



    SQLiteDatabase sqLiteDatabase;
    DBAdapter dbAdapter;
    Context context;

    DatabaseHelper(Context context) {
        this.context = context;
        dbAdapter = new DBAdapter(context, DB_NAME, null, DB_VERSION);
    }

    public DatabaseHelper open() {
        sqLiteDatabase = dbAdapter.getWritableDatabase();
        return this;
    }

    public void close() {
        sqLiteDatabase.close();
    }

    public long Insert(String codename, String code) {
        ContentValues c = new ContentValues();
        c.put(PIN, code);
        c.put(MODE_NAME, codename);
        return sqLiteDatabase.replace(TABLE_NAME, null, c);
    }
    public long InsertTrack(String contact,String code,String time) {
        ContentValues c = new ContentValues();
        c.put(CONTACT,contact);
        c.put(CODE,code);
        c.put(CREATED_TIME,time);
        return sqLiteDatabase.replace(TABLE_NAME_TRACK,null,c);
    }
    public long Insert_contact(String contact_name, String c1, String c2, String c3, String c4) {
        ContentValues c = new ContentValues();
        c.put(_CID,1);
        c.put(CONTACT_NAME, contact_name);
        c.put(CONTACT1, c1);
        c.put(CONTACT2, c2);
        c.put(CONTACT3, c3);
        c.put(CONTACT4, c4);
        return sqLiteDatabase.replace(TABLE_NAME_CONTACT, null, c);

    }
   /* public long Update_contact(String id,String c1,String c2,String c3,String c4){
        ContentValues c=new ContentValues();
        c.put(_CID,id);
        c.put(CONTACT1,c1);
        c.put(CONTACT2,c2);
        c.put(CONTACT3,c3);
        c.put(CONTACT4,c4);
        return sqLiteDatabase.update(TABLE_NAME_CONTACT,c,"_CID='0'" ,new String[] {id});

    }*/

    public Cursor select() {
        return sqLiteDatabase.query(TABLE_NAME, null, null,
                null, null, null, null);
    }

    public Cursor select_contact() {
        return sqLiteDatabase.query(TABLE_NAME_CONTACT, null, null,
                null, null, null, null);
    }

    public Cursor select_track() {
        return  sqLiteDatabase.query(TABLE_NAME_TRACK,null,null,null,null,null,null);
    }


    class DBAdapter extends SQLiteOpenHelper{

        public DBAdapter(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL(CREATE_TABLE);
            db.execSQL(CREATE_TABLE_CONTACT);
            db.execSQL(CREATE_TABLE_TRACK);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_TRACK);
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_CONTACT);
            onCreate(db);
        }
    }
}
