package com.project.pwc.model;

import java.util.List;
import java.util.Objects;

public class Country {

    private String cca3;

    private List<String> borders;


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


    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Country)) {
            return false;
        }

        final Country country = (Country) o;
        return Objects.equals(getCca3(), country.getCca3());
    }

}
