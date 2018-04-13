package org.verleihsystem.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import org.verleihsystem.model.Ort;

@Transactional
public interface OrtDAO extends CrudRepository<Ort,Long>{
}
