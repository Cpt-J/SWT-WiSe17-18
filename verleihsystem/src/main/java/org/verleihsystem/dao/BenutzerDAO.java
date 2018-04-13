package org.verleihsystem.dao;

import org.springframework.data.repository.CrudRepository;
import org.verleihsystem.model.Benutzer;


public interface BenutzerDAO extends CrudRepository<Benutzer, Long> {
    Benutzer getBenutzerByBenutzername(String Benutzername);
}
