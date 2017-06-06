package ru.spb.itolia.converter.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;

import ru.spb.itolia.converter.model.beans.ValCurs;
import ru.spb.itolia.converter.model.beans.ValCursDbHelper;
import ru.spb.itolia.converter.model.beans.Valute;
import ru.spb.itolia.converter.model.ValCursContract.ValuteEntry;
import ru.spb.itolia.converter.view.MainActivity;

/**
 * Created by itolianezzz on 06.06.2017.
 */

public class GetValCursTask extends AsyncTask<Void, Void, ValCurs> {
    private MainActivity activity;
    private ValCurs valCurs;

    public GetValCursTask(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    protected ValCurs doInBackground(Void... params) {
        try {
            valCurs = MainModel.getContent("http://www.cbr.ru/scripts/XML_daily.asp");
            ValCursDbHelper dbHelper = new ValCursDbHelper(activity);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            for(Valute valute: valCurs.getValutes()){
                ContentValues values = new ContentValues();
                values.put(ValuteEntry.COLUMN_NAME_ENTRY_ID, valute.getId());
                values.put(ValuteEntry.COLUMN_NAME_NUMCODE, valute.getNumCode());
                values.put(ValuteEntry.COLUMN_NAME_CHARCODE, valute.getCharCode());
                values.put(ValuteEntry.COLUMN_NAME_NOMINAL, valute.getNominal());
                values.put(ValuteEntry.COLUMN_NAME_NAME, valute.getName());
                values.put(ValuteEntry.COLUMN_NAME_VALUE, valute.getValue());

                db.insert(ValuteEntry.TABLE_NAME, null, values);
                activity
                        .getSharedPreferences(MainActivity.APP_PREFERENCES, Context.MODE_PRIVATE)
                        .edit()
                        .putBoolean(MainActivity.PREF_DATA_LOADED, true)
                        .apply();
            }

        } catch (Exception e) {
            Log.e("ValCursTask", "ERROR loading valutes! " + e);
        }

        return valCurs;
    }

    @Override
    protected void onPostExecute(ValCurs result) {
        super.onPostExecute(result);
        Toast.makeText(activity, "Valcurs loaded", Toast.LENGTH_SHORT).show();
        activity.onDataLoaded();

    }
}
