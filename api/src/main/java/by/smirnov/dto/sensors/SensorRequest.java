package by.smirnov.dto.sensors;

import by.smirnov.domain.Type;
import by.smirnov.validation.EnumValid;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static by.smirnov.validation.ValidationConstants.MAX_SIZE_MESSAGE;
import static by.smirnov.validation.ValidationConstants.NOT_BLANK_MESSAGE;
import static by.smirnov.validation.ValidationConstants.NOT_NULL_MESSAGE;

@Getter
@Setter
public class SensorRequest {

    @NotBlank(message = NOT_BLANK_MESSAGE)
    @Size(max=30, message = MAX_SIZE_MESSAGE)
    private String name;

    @NotBlank(message = NOT_BLANK_MESSAGE)
    @Size(max=15, message = MAX_SIZE_MESSAGE)
    private String model;

    @Min(-100)
    @Max(100)
    @NotNull(message = NOT_NULL_MESSAGE)
    private Integer rangeFrom;

    @Min(-100)
    @Max(100)
    @NotNull(message = NOT_NULL_MESSAGE)
    private Integer rangeTo;

    @NotNull(message = NOT_NULL_MESSAGE)
    @EnumValid(enumClass = Type.class)
    private String type;

    @NotNull(message = NOT_NULL_MESSAGE)
/*    @EnumValid(enumClass = Type.class)*/
    private String unit;

    @Size(max=40, message = MAX_SIZE_MESSAGE)
    private String location;

    @Size(max=200, message = MAX_SIZE_MESSAGE)
    private String description;
}
