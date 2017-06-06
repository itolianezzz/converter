package ru.spb.itolia.converter.model.beans;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by itolia on 05.06.2017.
 */

@Root(name="ValCurs")
public class ValCurs {
    @Attribute(name="Date")
    private String date;

    @Attribute(name="name")
    private String name;

    @ElementList(inline=true)
    private List<Valute> valute;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Valute> getValutes() {
        return valute;
    }

    public void setValute(List<Valute> valute) {
        this.valute = valute;
    }

    @Override
    public String toString() {
        return "ClassPojo [date = " + date + ", name = " + name + ", valute = " + valute + "]";
    }
}
