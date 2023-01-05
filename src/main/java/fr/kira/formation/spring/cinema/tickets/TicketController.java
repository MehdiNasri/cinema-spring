package fr.kira.formation.spring.cinema.tickets;

import fr.kira.formation.spring.cinema.seances.SeanceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("tickets")
@CrossOrigin
public class TicketController {

        private final TicketService service;



        public TicketController(TicketService service) {
            this.service = service;

        }

        //Création de ticket nécessite dans l'url /tickets?seanceId=X et le body du ticket.
        @PostMapping
        public Ticket save(@RequestBody Ticket entity,@RequestParam int seanceId) throws Exception {
            return service.save(entity,seanceId);
        }

        @GetMapping("{id}")
        public Ticket findById(@PathVariable Integer integer) {
            return service.findById(integer);
        }

        @DeleteMapping("{id}")
        public void deleteById(@PathVariable Integer integer) {
            service.deleteById(integer);
        }

        @GetMapping
        public Iterable<Ticket> findAll() {
            return service.findAll();
        }

        @GetMapping("seance/{seanceId}")
        public List<Ticket> findBySeance(@PathVariable int seanceId) {
            return service.findBySeance(seanceId);
        }
}
