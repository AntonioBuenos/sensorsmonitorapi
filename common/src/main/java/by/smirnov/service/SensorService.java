package by.smirnov.service;

import by.smirnov.domain.Sensor;

import java.util.List;

public interface SensorService {

    Sensor findById(Long id);

    List<Sensor> findAll();

    Sensor create(Sensor object);

    Sensor update(Sensor toBeUpdated);

    Sensor delete(Long id);

    void hardDelete(Long id);
}
