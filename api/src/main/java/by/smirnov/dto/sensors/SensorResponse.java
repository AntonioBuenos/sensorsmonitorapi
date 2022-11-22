package by.smirnov.dto.sensors;

import by.smirnov.domain.Type;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
public class SensorResponse {

    private Long id;

    private String name;

    private String model;

    private Integer rangeFrom;

    private Integer rangeTo;

    @Enumerated(EnumType.STRING)
    private Type type;

    private String location;

    private String description;

}
