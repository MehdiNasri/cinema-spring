package fr.kira.formation.spring.cinema.salles;

import fr.kira.formation.spring.cinema.films.Film;
import fr.kira.formation.spring.cinema.seances.Seance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SalleRepository extends JpaRepository<Salle, Integer> {

}
