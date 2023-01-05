package fr.kira.formation.spring.cinema.films;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.kira.formation.spring.cinema.acteurs.Acteur;
import fr.kira.formation.spring.cinema.acteurs.ActeurService;
import fr.kira.formation.spring.cinema.films.dto.FilmCompletDto;
import fr.kira.formation.spring.cinema.films.dto.FilmReduitDto;
import fr.kira.formation.spring.cinema.seances.Seance;
import fr.kira.formation.spring.cinema.seances.SeanceService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe Service pour les films.
 */
@Service
public class FilmService {

    private final FilmJpaRepository jpaRepository;
    private final ActeurService acteurService;

    private final SeanceService seanceService;
    private final ObjectMapper mapper;


    public FilmService(FilmJpaRepository jpaRepository, ActeurService acteurService, ObjectMapper mapper,SeanceService seanceService) {
        this.jpaRepository = jpaRepository;
        this.acteurService = acteurService;
        this.mapper = mapper;
        this.seanceService = seanceService;
    }

    /**
     * Sauvegarde un film dans la base de données.
     * Si le film posséde aucun id, alors sauvegarde le film et lui donne un id.
     * Sinon remplace le film portant l'id dans la base de données.
     * @param film a sauvegarder
     * @return film sauvegarder.
     */
    public FilmCompletDto save(Film film){
        Film entite = jpaRepository.save(film);
        return mapper.convertValue(entite, FilmCompletDto.class);
    }

    /**
     * Retourne la liste de l'ensemble des films.
     * @return liste de l'ensemble des films.
     */
    public List<FilmReduitDto> findAll(){
        List<Film> entities = this.jpaRepository.findAll();
        /*
        En JS
        let entities = ...
        return entities.map(entity => mapper.convertValue(entity, FilmReduitDto.class))
         */
        return entities.stream().map(film -> mapper.convertValue(film, FilmReduitDto.class)).toList();
    }

    /**
     * Retourne le film portant l'id.
     * @param id du film a rechercher
     * @return le film rechercher
     * @throws ResponseStatusException si aucun ne porte cet id dans la base de données,
     *      alors retourne cette exception avec le status 404 NOT_FOUND
     */
    public FilmCompletDto findById(Integer id) {
        var entity =  jpaRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
        return mapper.convertValue(entity, FilmCompletDto.class);
    }

    /**
     * Supprime le film portant l'id dans la base de données. Ne fait rien si aucun film ne porte cette id.
     * @param id du film a supprimer.
     */
    public void deleteById(Integer id) {
        jpaRepository.deleteById(id);
    }

    /**
     * Retourne la liste des films ou leurs titre contient le mot clef.
     * @param titre a rechercher
     * @return liste des films
     */
    public List<FilmReduitDto> findByTitreContaining(String titre){
        List<Film> entities = jpaRepository.findByTitreContaining(titre);
        return entities.stream().map(film -> mapper.convertValue(film, FilmReduitDto.class)).toList();
    }

    /**
     * Ajoute un acteur a un film en fonction de l'id du film et l'id de l'acteur
     * @param id du film
     * @param idActeur id de l'acteur
     */
    public void addActeurById(Integer id, Integer idActeur) {
        Acteur acteur = new Acteur();
        acteur.setId(idActeur);
        addActeur(id, acteur);
    }

    /**
     * Ajoute un acteur au film en fonction de l'id du film et de l'acteur.
     * @param id du film
     * @param acteur a ajouter
     */
    public void addActeur(Integer id, Acteur acteur) {
        Film film = jpaRepository.findById(id).orElseThrow();
        Acteur acteurAAjouter = this.acteurService.findOrInsert(acteur);
        film.getActeurs().add(acteurAAjouter);
        jpaRepository.save(film);
    }

    /**
     * Récuperation des films par la date de sortie.
     * @param date
     * @return
     */
    public List<FilmReduitDto> findByDate(LocalDate date){
        List<Seance> seances = seanceService.findByDate(date);
        List<Film> films = new ArrayList<>();

        for(Seance seance : seances){
            films.add(seance.getFilm());
        }

        System.out.println(films);
        return films.stream().map(film -> mapper.convertValue(film, FilmReduitDto.class)).toList();
    }
}
