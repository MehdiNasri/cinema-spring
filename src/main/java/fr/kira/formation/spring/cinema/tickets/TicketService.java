package fr.kira.formation.spring.cinema.tickets;

import fr.kira.formation.spring.cinema.seances.Seance;
import fr.kira.formation.spring.cinema.seances.SeanceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    private final TicketRepository repository;
    private final SeanceService seanceService;
    public TicketService(TicketRepository repository,SeanceService seanceService) {
        this.repository = repository;
        this.seanceService = seanceService;
    }

    public Ticket save(Ticket entity,int seanceId) throws Exception {

        //On va chercher la seance correspondante à l'ID
        Seance seance = this.seanceService.findById(seanceId);
        //Initilisation du total du nombre de place de la seance
        int totalPLacesRestantes = seance.getNombrePlace();
        //Recupération de tout les tickets liés a la seance
        List<Ticket> tickets = repository.findBySeance(seance);
        //Pour chaque tickets on soustrais le nombre de places reservé avec le nombre de places total
        for(Ticket ticket : tickets){
            totalPLacesRestantes -= ticket.getNombrePlace();
        }
        //Check s'il y'a assez de place par rapport au nombre de place choisi
        if(totalPLacesRestantes < entity.getNombrePlace()){
               throw new Exception("Il n'y a pas assez de place pour cette seance");
        }
        //Ajout de la seance dans le ticket qui va etre créer
        entity.setSeance(seance);
        return repository.save(entity);
    }

    public Ticket findById(Integer integer) {
        return repository.findById(integer).orElseThrow();
    }

    public void deleteById(Integer integer) {
        repository.deleteById(integer);
    }

    public Iterable<Ticket> findAll() {
        return repository.findAll();
    }

    public List<Ticket> findBySeance(int seanceId ){
        //On va chercher la seance correspondante à l'ID
        Seance seance = this.seanceService.findById(seanceId);

        //Recupération de tout les tickets liés a la seance
        return repository.findBySeance(seance);
    }

}
