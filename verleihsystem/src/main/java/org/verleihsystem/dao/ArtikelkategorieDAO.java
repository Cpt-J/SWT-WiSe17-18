package org.verleihsystem.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import org.verleihsystem.model.Artikelkategorie;

@Transactional
public interface ArtikelkategorieDAO extends CrudRepository<Artikelkategorie, Long> {
}