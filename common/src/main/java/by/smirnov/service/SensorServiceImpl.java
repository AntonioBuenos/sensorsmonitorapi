package by.smirnov.service;

import by.smirnov.domain.Sensor;
import by.smirnov.exception.NoSuchEntityException;
import by.smirnov.repository.SensorRepository;
import by.smirnov.repository.SensorRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SensorServiceImpl implements SensorService{

    private final SensorRepository repository;

    @Override
    public Sensor findById(Long id) {
        return repository
                .findById(id)
                .orElseThrow(NoSuchEntityException::new);
    }

    @Override
    public List<Sensor> findAll() {
        return repository.findAll();
    }

    @Transactional
    @Override
    public Sensor create(Sensor object) {
        return repository.create(object);
    }

    @Transactional
    @Override
    public Sensor update(Sensor toBeUpdated) {
        return repository.update(toBeUpdated);
    }

    @Transactional
    @Override
    public Sensor delete(Long id) {
        return repository.delete(id);
    }

    @Transactional
    @Override
    public void hardDelete(Long id){
        repository.hardDelete(id);
    }
}
