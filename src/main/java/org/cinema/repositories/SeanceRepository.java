package org.cinema.repositories;

import org.cinema.entities.Seance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeanceRepository extends JpaRepository<Seance,Long> {
}
