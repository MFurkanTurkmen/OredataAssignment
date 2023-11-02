package com.oredata.assignment.utility;

import com.oredata.assignment.repository.entity.BaseEntity;
import lombok.Getter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Getter
public class ServiceManager<T extends BaseEntity,ID> implements IServiceManager<T,ID> {
    private final JpaRepository<T,ID> repository;

    public ServiceManager(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    @Override
    public T save(T t) {
        t.setCreateDate(LocalDateTime.now().toString());
        t.setUpdateDate(LocalDateTime.now().toString());
        return repository.save(t);
    }

    @Override
    public List<T> saveAll(Iterable<T> t) {
        t.forEach(entity -> entity.setCreateDate(LocalDateTime.now().toString()));
        t.forEach(entity -> entity.setUpdateDate(LocalDateTime.now().toString()));
        return repository.saveAll(t);
    }

    @Override
    public List<T> updateAll(Iterable<T> t) {
        t.forEach(entity -> entity.setUpdateDate(LocalDateTime.now().toString()));
        return repository.saveAll(t);
    }

    @Override
    public T update(T t) {
        t.setUpdateDate(LocalDateTime.now().toString());
        return repository.save(t);
    }

    @Override
    public void delete(T t) {
        repository.delete(t);

    }

    @Override
    public void deleteById(ID id) {
        repository.deleteById(id);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<T> findById(ID id) {
        return repository.findById(id);
    }
}
