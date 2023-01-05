package fr.kira.formation.spring.cinema.seances;

import fr.kira.formation.spring.cinema.salles.Salle;
import fr.kira.formation.spring.cinema.salles.SalleRepository;
import fr.kira.formation.spring.cinema.salles.SalleService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SeanceService {

    private final SeanceRepository repository;

    private final SalleRepository salleRepository;


    public SeanceService(SeanceRepository repository,SalleRepository salleRepository) {
        this.repository = repository;
        this.salleRepository = salleRepository;
    }

    public List<Seance> findAll() {
        return repository.findAll();
    }

    public Seance save(Seance entity) {
        //récupération de la salle
        Salle salle = salleRepository.findById(entity.getSalle().getId()).orElseThrow();
        //ajout du nombre de place de la seance par rapport a la capacite de la salle.
        entity.setNombrePlace(salle.getCapacite());
        return repository.save(entity);
    }

    public Seance findById(Integer integer) {
        return repository.findById(integer).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public void deleteById(Integer integer) {
        repository.deleteById(integer);
    }

    public List<Seance> findByDate(LocalDate date){
        return repository.findByDate(date);
    };


}
