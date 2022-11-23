package by.smirnov.repository;

import java.util.List;
import java.util.Optional;

public interface CRUDRepository <K, T>{
    Optional<T> findById(K id);
    List<T> findAll();
    T create(T object);
    T update(T object);
    T delete(K id);
    void hardDelete(K id);
}
