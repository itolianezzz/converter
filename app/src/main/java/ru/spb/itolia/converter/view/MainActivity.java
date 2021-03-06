package ru.spb.itolia.converter.view;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import ru.spb.itolia.converter.R;
import ru.spb.itolia.converter.model.GetValCursTask;
import ru.spb.itolia.converter.model.ValCursContract;
import ru.spb.itolia.converter.model.beans.ValCursDbHelper;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener {
    private final static String BUNDLE_RESULT = "result";
    private final static String BUNDLE_AMOUNT = "amount";
    private GetValCursTask task;
    private Spinner valuteTo;
    private Spinner valuteFrom;
    private EditText amountFrom;
    private TextView amountTo;
    private SimpleCursorAdapter sAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        if(savedInstanceState != null){
            amountTo.setText(savedInstanceState.getString(BUNDLE_RESULT));
            amountFrom.setText(savedInstanceState.getString(BUNDLE_AMOUNT));
        } else {
            task = new GetValCursTask(this);
            task.execute();
        }

    }

    private void initViews() {
        valuteFrom = (Spinner) findViewById(R.id.spinner_from);
        valuteTo = (Spinner) findViewById(R.id.spinner_to);
        amountFrom = (EditText) findViewById(R.id.et_amount_from);
        amountTo = (TextView) findViewById(R.id.result);
        Button btnConvert = (Button) findViewById(R.id.btn_convert);
        btnConvert.setOnClickListener(this);
        getSupportLoaderManager().initLoader(0, null, this);
        sAdapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_2,
                null,
                new String[] {ValCursContract.ValuteEntry.COLUMN_NAME_CHARCODE, ValCursContract.ValuteEntry.COLUMN_NAME_NAME},
                new int[] {android.R.id.text1, android.R.id.text2},
                0);
        sAdapter.setDropDownViewResource(android.R.layout.simple_list_item_2);
        valuteFrom.setAdapter(sAdapter);
        valuteTo.setAdapter(sAdapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(BUNDLE_AMOUNT, amountFrom.getText().toString());
        outState.putString(BUNDLE_RESULT, amountTo.getText().toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (task != null && task.getStatus() != AsyncTask.Status.FINISHED) {
            task.cancel(true);
        }
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new ValutesCursorLoader(this, new ValCursDbHelper(this));
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        sAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        sAdapter.swapCursor(null);
    }

    @Override
    public void onClick(View view) {
        if(amountFrom.getText().toString().isEmpty()){
            Toast.makeText(this, getResources().getText(R.string.enter_amount), Toast.LENGTH_SHORT).show();
        } else {
            float fromAmount = Float.parseFloat(amountFrom.getText().toString());
            Cursor fromCursor = (Cursor) valuteFrom.getSelectedItem();
            int fromNominal = fromCursor.getInt(fromCursor.getColumnIndex("Nominal"));
            float fromValue = Float.parseFloat(fromCursor.getString(fromCursor.getColumnIndex("Value")).replace(",", "."));
            Cursor toCursor = (Cursor) valuteTo.getSelectedItem();
            int toNominal =  toCursor.getInt(toCursor.getColumnIndex("Nominal"));
            float toValue = Float.parseFloat(toCursor.getString(toCursor.getColumnIndex("Value")).replace(",", "."));
            float result = fromAmount * ((toNominal * fromValue) / (fromNominal * toValue));
            amountTo.setText(String.valueOf(result));
        }
    }

    public void onDataLoaded() {
        getSupportLoaderManager().getLoader(0).forceLoad();
    }

    private static class ValutesCursorLoader extends CursorLoader {

        ValCursDbHelper db;

        public ValutesCursorLoader(Context context, ValCursDbHelper db) {
            super(context);
            this.db = db;
        }

        @Override
        public Cursor loadInBackground() {
            Cursor cursor = db.getValutes();

            return cursor;
        }

    }
}
