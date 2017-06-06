package ru.spb.itolia.converter.model;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import ru.spb.itolia.converter.model.beans.ValCurs;
import ru.spb.itolia.converter.model.beans.Valute;

/**
 * Created by itolia on 05.06.2017.
 */

public class MainModel {

    public static ValCurs getContent(String path) throws Exception {
        HttpURLConnection con = null;
        try {
            URL url = new URL(path);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setReadTimeout(10000);
            con.connect();
            Serializer serializer = new Persister();
            ValCurs example = serializer.read(ValCurs.class, con.getInputStream());
            return example;
        } finally {
            if(con != null){
                con.disconnect();
            }
        }
    }

}
