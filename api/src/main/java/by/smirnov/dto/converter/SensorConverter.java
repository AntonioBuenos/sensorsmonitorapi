package by.smirnov.dto.converter;

import by.smirnov.domain.Sensor;
import by.smirnov.domain.Type;
import by.smirnov.dto.sensors.SensorRequest;
import by.smirnov.dto.sensors.SensorResponse;
import by.smirnov.exceptionhandle.BadRequestException;
import by.smirnov.service.SensorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static by.smirnov.constants.ResponseEntityConstants.BAD_UNIT_TYPE_MESSAGE;

@RequiredArgsConstructor
@Component
public class SensorConverter {

    private final ModelMapper modelMapper;
    private final SensorService service;

    public Sensor convert(SensorRequest request) {
        request.setType(request.getType().toUpperCase());
        checkUnitTypeCorrespondance(request);
        Sensor created = modelMapper.map(request, Sensor.class);
        created.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));
        created.setIsDeleted(false);

        return created;
    }

    public Sensor convert(SensorRequest request, Long id) {
        request.setType(request.getType().toUpperCase());
        checkUnitTypeCorrespondance(request);
        Sensor old = service.findById(id);
        old.setName(request.getName());
        old.setModel(request.getModel());
        old.setRangeFrom(request.getRangeFrom());
        old.setRangeTo(request.getRangeTo());
        old.setType(Type.valueOf(request.getType()));
        old.setLocation(request.getLocation());
        old.setDescription(request.getDescription());
        return old;
    }

    public SensorResponse convert(Sensor sensor) {
        SensorResponse response = modelMapper.map(sensor, SensorResponse.class);
        Type type = sensor.getType();
        response.setTypeName(type.getTypeName());
        response.setUnit(type.getUnit());
        return response;
    }

    private void checkUnitTypeCorrespondance(SensorRequest request){
        if (!request.getUnit().equals(Type.valueOf(request.getType()).getUnit())){
            throw new BadRequestException(BAD_UNIT_TYPE_MESSAGE);
        }
    }

}
