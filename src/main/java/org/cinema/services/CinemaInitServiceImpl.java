package org.cinema.services;

import org.cinema.entities.*;
import org.cinema.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@Service
@Transactional
public class CinemaInitServiceImpl implements ICinemaInitService  {

    @Autowired
    private VilleRepository villeRepository ;
    @Autowired
    private CinemaRepository cinemaRepository ;
    @Autowired
    private SalleRepository salleRepository;
    @Autowired
    private PlaceRepository placeRepository ;
    @Autowired
    private SeanceRepository seanceRepository;
    @Autowired
    private FilmRepository filmRepository ;
    @Autowired
    private ProjectionFilmRepository projectionFilmRepository ;
    @Autowired
    private TicketRepository ticketRepository ;
    @Autowired
    private CategorieRepository categorieRepository ;

    @Override
    public void initVilles() {
        Stream.of("Casa","sale","rabat","kenitra","temara").forEach(v->{
            Ville ville = new Ville();
            ville.setName(v);
            villeRepository.save(ville);
        });
    }

    @Override
    public void initCinemas() {
        villeRepository.findAll().forEach(v->{
            Stream.of("Megarama","Comedi","Opera","Hssan2","saaid haji").forEach(c->{
                Cinema cinema = new Cinema();
                cinema.setName(c);
                cinema.setVille(v);
                cinema.setNombresSalles(3+(int)(Math.random()*7));
                cinemaRepository.save(cinema);
            });
        });
    }

    @Override
    public void initSalles() {
        cinemaRepository.findAll().forEach(c->{
            for (int i=0;i<c.getNombresSalles();i++){
                Salle salle = new Salle() ;
                salle.setName("Salle "+i);
                salle.setCinema(c);
                salle.setNombrePlaces(15+(int)(Math.random()*10));
                salleRepository.save(salle);
            }
        });
    }

    @Override
    public void initSeance() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Stream.of("12:00","15:00","17:00","19:00","00:00").forEach(t->{
            Seance seance = new Seance();
            try {
                seance.setHeureDebut(dateFormat.parse(t));
                seanceRepository.save(seance);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void initPlaces() {
        salleRepository.findAll().forEach(s->{
            for (int i=0;i<s.getNombrePlaces();i++){
                Place place = new Place();
                place.setNumero(i+1);
                place.setSalle(s);
                placeRepository.save(place);
            }
        });
    }

    @Override
    public void initFilms() {
        double[] duree = new double[]{1,1.5,3,4,3.5};
        List<Categorie> categorieList = categorieRepository.findAll();
        Stream.of("Hack1","games","fifa","dar  dar","zanga zanga").forEach(f->{
            Film film = new Film();
            film.setTitre(f);
            film.setDuree(duree[new Random().nextInt(duree.length)]);
            film.setPhoto(f.replace(" ","")+".jpeg");
            film.setCategorie(categorieList.get(new Random().nextInt(categorieList.size())));
            filmRepository.save(film);
        });
    }

    @Override
    public void initProjections() {
        double[] prix = new double[]{30,50,60,90};
        villeRepository.findAll().forEach(v->{
            v.getCinema().forEach(cinema -> {
                cinema.getSalles().forEach(salle->{
                    filmRepository.findAll().forEach(film -> {
                        seanceRepository.findAll().forEach(seance -> {
                            ProjectionFilm projectionFilm = new ProjectionFilm();
                            projectionFilm.setDateProjection(new Date());
                            projectionFilm.setFilm(film);
                            projectionFilm.setPrix(prix[new Random().nextInt(prix.length)]);
                            projectionFilm.setSalle(salle);
                            projectionFilm.setSeance(seance);
                            projectionFilmRepository.save(projectionFilm);
                        });
                    });
                });
            });
        });
    }

    @Override
    public void initTickets() {
        projectionFilmRepository.findAll().forEach(p->{
            p.getSalle().getPlaces().forEach(place -> {
                Ticket ticket = new Ticket();
                ticket.setPrix(p.getPrix());
                ticket.setPlace(place);
                ticket.setProjectionFilm(p);
                ticket.setRserve(false);
                ticketRepository.save(ticket);

            });
        });
    }

    @Override
    public void initCategories() {
        Stream.of("Drama","Action","Romentic","science","Documentary").forEach(cat->{
            Categorie categorie = new Categorie();
            categorie.setName(cat);
            categorieRepository.save(categorie);
        });
    }
}
