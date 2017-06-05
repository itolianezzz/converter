package ru.spb.itolia.converter.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import ru.spb.itolia.converter.model.beans.ValCurs;

/**
 * Created by itolia on 05.06.2017.
 */

public class DataFragment extends Fragment {

    // data object we want to retain
    private ValCurs data;

    // this method is only called once for this fragment
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retain this fragment
        setRetainInstance(true);
    }

    public void setData(ValCurs data) {
        this.data = data;
    }

    public ValCurs getData() {
        return data;
    }
}

