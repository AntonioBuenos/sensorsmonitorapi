package by.smirnov.repository;

import by.smirnov.domain.Sensor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface SensorsRepository extends
        CrudRepository<Sensor, Long>,
        JpaRepository<Sensor, Long> {

    Page<Sensor> findByIsDeleted(Pageable pageable, boolean isDeleted);

}