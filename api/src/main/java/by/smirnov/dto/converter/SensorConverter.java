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

@RequiredArgsConstructor
@Component
public class SensorConverter {

    private final ModelMapper modelMapper;
    private final SensorService service;

    public Sensor convert(SensorRequest request) {
        Sensor created = modelMapper.map(request, Sensor.class);
        created.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));
        created.setIsDeleted(false);
        if (!request.getUnit().equals(Type.valueOf(request.getType()).getUnit())){
            throw new BadRequestException("Unit does not conform chosen type.");
        }
        else created.setType(Type.valueOf(request.getType()));

        return created;
    }

    public Sensor convert(SensorRequest request, Long id) {
        Sensor old = service.findById(id);
        old.setModificationDate(Timestamp.valueOf(LocalDateTime.now()));
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
        return modelMapper.map(sensor, SensorResponse.class);
    }

}
