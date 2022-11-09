package by.smirnov.domain;

import lombok.Getter;

@Getter
public enum Type {

    PRESSURE("bar"), VOLTAGE("voltage"), TEMPERATURE("°C"), HUMIDITY("%");

    private final String unit;

    private Type(String unit) {
        this.unit = unit;
    }

}
