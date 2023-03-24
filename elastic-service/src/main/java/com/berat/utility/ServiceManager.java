package com.berat.utility;

import com.berat.model.BaseEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Optional;


@RequiredArgsConstructor
@Getter
public class ServiceManager <T extends BaseEntity,ID> implements IService<T,ID> {

    private final ElasticsearchRepository<T,ID> repository;
    @Override
    public T save(T t) {
        t.setCreateat(System.currentTimeMillis());
        t.setUpdateat(System.currentTimeMillis());

        return repository.save(t);
    }

    @Override
    public Iterable<T> saveAll(Iterable<T> t) {
        t.forEach(x->{
            x.setCreateat(System.currentTimeMillis());
            x.setUpdateat(System.currentTimeMillis());

        });
        return repository.saveAll(t);
    }

    @Override
    public T update(T t) {
        t.setUpdateat(System.currentTimeMillis());
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
    public Iterable<T> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<T> findById(ID id) {
        return repository.findById(id);
    }

}