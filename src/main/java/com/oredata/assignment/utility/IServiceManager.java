package com.oredata.assignment.utility;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public interface IServiceManager<T,ID> {
    T save(T t);
    List<T> saveAll(Iterable<T> t);
    T update(T t);
    List<T> updateAll(Iterable<T> t);
    void delete(T t);
    void deleteById(ID id);
    List<T> findAll();
    Optional<T> findById(ID id);
}
