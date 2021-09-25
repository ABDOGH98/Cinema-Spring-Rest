package org.cinema.repositories;

import org.cinema.entities.ProjectionFilm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ProjectionFilmRepository extends JpaRepository<ProjectionFilm,Long> {
}
