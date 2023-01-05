package fr.kira.formation.spring.cinema.seances;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDate;
import java.util.List;

//@RepositoryRestResource(collectionResourceRel = "seances", path = "seances")
public interface SeanceRepository extends JpaRepository<Seance, Integer> {
    List<Seance> findByDate(LocalDate date);
}
