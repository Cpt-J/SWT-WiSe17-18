package org.verleihsystem.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import org.verleihsystem.model.Artikel;

import java.util.List;

@Transactional
public interface ArtikelDAO extends CrudRepository<Artikel, Long> {
}
