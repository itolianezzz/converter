package ru.spb.itolia.converter.model.beans;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ru.spb.itolia.converter.model.ValCursContract.ValuteEntry;
import ru.spb.itolia.converter.view.MainActivity;

/**
 * Created by itolianezzz on 06.06.2017.
 */

public class ValCursDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ValCurs.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ValuteEntry.TABLE_NAME + " (" +
                    ValuteEntry._ID + " INTEGER PRIMARY KEY," +
                    ValuteEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                    ValuteEntry.COLUMN_NAME_NUMCODE + INT_TYPE + COMMA_SEP +
                    ValuteEntry.COLUMN_NAME_CHARCODE + TEXT_TYPE + COMMA_SEP +
                    ValuteEntry.COLUMN_NAME_NOMINAL + INT_TYPE + COMMA_SEP +
                    ValuteEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    ValuteEntry.COLUMN_NAME_VALUE + TEXT_TYPE +
            " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ValuteEntry.TABLE_NAME;

    public ValCursDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public Cursor getValutes() {
        return getWritableDatabase().query(ValuteEntry.TABLE_NAME, new String[]{ValuteEntry._ID, ValuteEntry.COLUMN_NAME_NAME, ValuteEntry.COLUMN_NAME_CHARCODE, ValuteEntry.COLUMN_NAME_NOMINAL, ValuteEntry.COLUMN_NAME_VALUE},  null, null, null, null, null);
    }

    public void clearData(){
        SQLiteDatabase db = getReadableDatabase();
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
        db.close();
    }

    public void storeData(ValCurs valCurs){
        SQLiteDatabase db = getWritableDatabase();
        for(Valute valute: valCurs.getValutes()){
            ContentValues values = new ContentValues();
            values.put(ValuteEntry.COLUMN_NAME_ENTRY_ID, valute.getId());
            values.put(ValuteEntry.COLUMN_NAME_NUMCODE, valute.getNumCode());
            values.put(ValuteEntry.COLUMN_NAME_CHARCODE, valute.getCharCode());
            values.put(ValuteEntry.COLUMN_NAME_NOMINAL, valute.getNominal());
            values.put(ValuteEntry.COLUMN_NAME_NAME, valute.getName());
            values.put(ValuteEntry.COLUMN_NAME_VALUE, valute.getValue());

            db.insert(ValuteEntry.TABLE_NAME, null, values);
        }
        db.close();
    }
}

