package ru.spb.itolia.converter.view;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;

import ru.spb.itolia.converter.R;
import ru.spb.itolia.converter.presenter.Presenter;

import static android.R.attr.data;

public class MainActivity extends AppCompatActivity implements View {

    private DataFragment dataFragment;
    private Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fm = getFragmentManager();
        dataFragment = (DataFragment) fm.findFragmentByTag(“data”);
        if (dataFragment == null) {
            // add the fragment
            dataFragment = new DataFragment();
            fm.beginTransaction().add(dataFragment, “data”).commit();
            // load the data from the web
            dataFragment.setData(presenter.getData());
        }
    }

    @Override
    public void onAttach() {

    }

    @Override
    public void onDetach() {

    }

    @Override
    public void showData() {

    }
}
