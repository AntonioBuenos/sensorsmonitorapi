package by.smirnov.repository;

import by.smirnov.domain.Sensor;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Primary
@RequiredArgsConstructor
public class SensorRepositoryImpl implements SensorRepository {

    private final SessionFactory sessionFactory;

    @Transactional(readOnly = true)
    public Optional<Sensor> findById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.get(Sensor.class, id));
    }

    @Transactional(readOnly = true)
    public List<Sensor> findAll() {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery(
                "select p from Sensor p where p.isDeleted = false order by p.id", Sensor.class
        ).getResultList();
    }

    @Transactional
    public Sensor create(Sensor object) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(object);
        return session.get(Sensor.class, object.getId());
    }

    @Transactional
    public Sensor update(Sensor sensor) {
        Session session = sessionFactory.getCurrentSession();
        Sensor sensorToBeUpdated = session.get(Sensor.class, sensor.getId());
        sensorToBeUpdated.setModificationDate(Timestamp.valueOf(LocalDateTime.now()));
        sensorToBeUpdated.setName(sensor.getName());
        sensorToBeUpdated.setModel(sensor.getModel());
        sensorToBeUpdated.setRangeFrom(sensor.getRangeFrom());
        sensorToBeUpdated.setRangeTo(sensor.getRangeTo());
        sensorToBeUpdated.setType(sensor.getType());
        sensorToBeUpdated.setLocation(sensor.getLocation());
        sensorToBeUpdated.setDescription(sensor.getDescription());
        return sensorToBeUpdated;
    }

    @Transactional
    public Sensor delete(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Sensor personToBeDeleted = session.get(Sensor.class, id);
        personToBeDeleted.setIsDeleted(true);
        personToBeDeleted.setTerminationDate(Timestamp.valueOf(LocalDateTime.now()));
        return session.get(Sensor.class, id);
    }

    @Transactional
    public void hardDelete(Long id) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(session.get(Sensor.class, id));
    }
}
