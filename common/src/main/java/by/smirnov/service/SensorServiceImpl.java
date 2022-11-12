package by.smirnov.service;

import by.smirnov.domain.Sensor;
import by.smirnov.exception.NoSuchEntityException;
import by.smirnov.repository.SensorsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SensorServiceImpl implements SensorService{

    private final SensorsRepository repository;

    @Override
    public Sensor findById(Long id) {
        return repository
                .findById(id)
                .orElseThrow(NoSuchEntityException::new);
    }

    @Override
    public List<Sensor> findAll(Pageable pageable) {
        return repository
                .findByIsDeleted(pageable, false)
                .getContent();
    }

    @Transactional
    @Override
    public Sensor create(Sensor object) {
        return repository.save(object);
    }

    @Transactional
    @Override
    public Sensor update(Sensor toBeUpdated) {
        return repository.save(toBeUpdated);
    }

    @Transactional
    @Override
    public Sensor delete(Long id) {
        Sensor toBeDeleted = repository
                .findById(id)
                .orElseThrow(NoSuchEntityException::new);
        toBeDeleted.setIsDeleted(true);
        toBeDeleted.setTerminationDate(Timestamp.valueOf(LocalDateTime.now()));
        return repository.save(toBeDeleted);
    }

    @Transactional
    @Override
    public void hardDelete(Long id){
        repository.deleteById(id);
    }
}
