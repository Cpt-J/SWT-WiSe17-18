package org.verleihsystem.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import org.verleihsystem.model.Verleihgebuehr;

@Transactional
public interface VerleihgebuehrDAO extends CrudRepository<Verleihgebuehr, Long> {
}
