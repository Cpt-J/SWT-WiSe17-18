package org.verleihsystem.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import org.verleihsystem.model.Anfrage;

@Transactional
public interface AnfrageDAO extends CrudRepository<Anfrage, Long> {
}
