package org.verleihsystem.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import org.verleihsystem.model.Termin;

@Transactional
public interface TerminDAO extends CrudRepository<Termin, Long> {
}
