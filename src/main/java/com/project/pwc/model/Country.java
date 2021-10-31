package com.project.pwc.model;

import java.util.List;

public class Country {

    String cca3;

    List<String> borders;

    List<Country> borderGraphList;


    public void addEdge(Country to) {
        borderGraphList.add(to);
    }


    public String getCca3() {
        return cca3;
    }

    public void setCca3(String cca3) {
        this.cca3 = cca3;
    }

    public List<String> getBorders() {
        return borders;
    }

    public void setBorders(List<String> borders) {
        this.borders = borders;
    }

}
