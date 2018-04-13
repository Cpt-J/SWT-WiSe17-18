package org.verleihsystem.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import org.verleihsystem.model.Pfand;

@Transactional
public interface PfandDAO extends CrudRepository<Pfand,Long> {
}
