package ru.spb.itolia.converter.model;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import ru.spb.itolia.converter.R;
import ru.spb.itolia.converter.model.beans.ValCurs;
import ru.spb.itolia.converter.model.beans.ValCursDbHelper;
import ru.spb.itolia.converter.view.MainActivity;

/**
 * Created by itolianezzz on 06.06.2017.
 */

public class GetValCursTask extends AsyncTask<Void, Void, Boolean> {
    private MainActivity activity;

    public GetValCursTask(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            ValCurs valCurs = ValCursService.getContent("http://www.cbr.ru/scripts/XML_daily.asp");
            ValCursDbHelper dbHelper = new ValCursDbHelper(activity);
            dbHelper.clearData();
            dbHelper.storeData(valCurs);
        } catch (Exception e) {
            Log.e("ValCursTask", "ERROR loading valutes! " + e);
            return false;
        }

        return true;
    }

    @Override
    protected void onPostExecute(Boolean resultOk) {
        super.onPostExecute(resultOk);
        if(resultOk) {
            Toast.makeText(activity, activity.getResources().getText(R.string.valcurs_loaded_popup), Toast.LENGTH_SHORT).show();
            activity.onDataLoaded();
        } else {
            Toast.makeText(activity, activity.getResources().getText(R.string.valcurs_error_popup), Toast.LENGTH_SHORT).show();
        }
    }
}
