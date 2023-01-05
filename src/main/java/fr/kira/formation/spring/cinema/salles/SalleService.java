package fr.kira.formation.spring.cinema.salles;

import fr.kira.formation.spring.cinema.seances.Seance;
import fr.kira.formation.spring.cinema.seances.SeanceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalleService {

    private final SalleRepository repository;
    private final SeanceService seanceService;


    public SalleService(SalleRepository repository, SeanceService seanceService) {
        this.repository = repository;
        this.seanceService = seanceService;
    }

    public Salle save(Salle entity) {
        return repository.save(entity);
    }

    public Salle findById(Integer integer) {
        return repository.findById(integer).orElseThrow();
    }

    public void deleteById(Integer integer) {
        repository.deleteById(integer);
    }

    public Iterable<Salle> findAll() {
        return repository.findAll();
    }

    /**
     * Recupération de toutes les salles dispo qui ne sont pas dans une seance.
     * @return
     */
    public List<Salle> findAllDispo(){
        //récupération des seances
        List<Seance> seances = seanceService.findAll();
        //récupération des salles
        List<Salle> salles = repository.findAll();
        //Filtre des salles par rapport a ceux qui sont deja dans une seance.
        for(Seance seance : seances){
            salles.remove(seance.getSalle());
        }
       return salles;

    }
}
