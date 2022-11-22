package by.smirnov.domain;

import lombok.Getter;

@Getter
public enum Type {

    PRESSURE("Pressure", "bar"),
    VOLTAGE("Voltage", "voltage"),
    TEMPERATURE("Temperature", "°C"),
    HUMIDITY("Humidity", "%");

    private final String typeName;
    private final String unit;

    private Type(String typeName, String unit) {
        this.typeName = typeName;
        this.unit = unit;
    }

}
