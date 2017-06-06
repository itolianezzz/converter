package ru.spb.itolia.converter.model.beans;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by itolia on 05.06.2017.
 */

@Root(name="Valute")
public class Valute {
    @Attribute(name="ID")
    private String id;

    @Element(name="NumCode")
    private int numCode;

    @Element(name="CharCode")
    private String charCode;

    @Element(name="Nominal")
    private int nominal;

    @Element(name="Name")
    private String name;

    @Element(name="Value")
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNominal() {
        return nominal;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }

    public String getCharCode() {
        return charCode;
    }

    public void setCharCode(String charCode) {
        this.charCode = charCode;
    }

    public int getNumCode() {
        return numCode;
    }

    public void setNumCode(int numCode) {
        this.numCode = numCode;
    }

    @Override
    public String toString() {
        return "ClassPojo [name = " + name + ", value = " + value + ", id = " + id + ", nominal = " + nominal + ", charCode = " + charCode + ", numCode = " + numCode + "]";
    }
}
