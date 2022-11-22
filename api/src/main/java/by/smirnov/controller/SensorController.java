package by.smirnov.controller;

import by.smirnov.domain.Sensor;
import by.smirnov.dto.converter.SensorConverter;
import by.smirnov.dto.sensors.SensorRequest;
import by.smirnov.dto.sensors.SensorResponse;
import by.smirnov.exceptionhandle.BadRequestException;
import by.smirnov.exceptionhandle.NotModifiedException;
import by.smirnov.service.SensorService;
import by.smirnov.validation.ValidationErrorConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static by.smirnov.constants.CommonConstants.ID;
import static by.smirnov.constants.CommonConstants.MAPPING_ID;
import static by.smirnov.constants.ResponseEntityConstants.DELETED_STATUS;
import static by.smirnov.controller.SensorControllerConstants.MAPPING_SENSORS;
import static by.smirnov.controller.SensorControllerConstants.SENSORS;

@RestController
@RequiredArgsConstructor
@RequestMapping(MAPPING_SENSORS)
public class SensorController {

    private final SensorService service;
    private final SensorConverter converter;

    @GetMapping
    public ResponseEntity<Map<String, List<SensorResponse>>> index(Pageable pageable) {
        List<SensorResponse> sensors = service.findAll(pageable)
                .stream()
                .map(converter::convert)
                .toList();
        return new ResponseEntity<>(Collections.singletonMap(SENSORS, sensors), HttpStatus.OK);
    }

    @GetMapping(MAPPING_ID)
    public ResponseEntity<SensorResponse> show(@PathVariable(ID) long id) {

        Sensor sensor = service.findById(id);
        SensorResponse response = converter.convert(sensor);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(security = {@SecurityRequirement(name = "JWT Bearer")})
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PostMapping
    public ResponseEntity<SensorResponse> create(
            @RequestBody @Valid SensorRequest request,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new BadRequestException(ValidationErrorConverter.getErrors(bindingResult).toString());
        }

        Sensor created = service.create(converter.convert(request));
        SensorResponse response = converter.convert(created);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(security = {@SecurityRequirement(name = "JWT Bearer")})
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PutMapping(MAPPING_ID)
    public ResponseEntity<SensorResponse> update(@PathVariable(name = ID) Long id,
                                                 @RequestBody @Valid SensorRequest request,
                                                 BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new BadRequestException(ValidationErrorConverter.getErrors(bindingResult).toString());
        }

        Sensor changed = service.update(converter.convert(request, id));
        SensorResponse response = converter.convert(changed);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(security = {@SecurityRequirement(name = "JWT Bearer")})
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @DeleteMapping(MAPPING_ID)
    public ResponseEntity<Map<String, Boolean>> delete(@PathVariable(ID) long id) {

        Sensor sensor = service.findById(id);
        if (Boolean.TRUE.equals(sensor.getIsDeleted())) throw new NotModifiedException();

        Sensor deleted = service.delete(id);
        return new ResponseEntity<>(
                Collections.singletonMap(DELETED_STATUS, deleted.getIsDeleted()),
                HttpStatus.OK);
    }
}
