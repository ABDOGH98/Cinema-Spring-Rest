package org.cinema.controllers;

import lombok.Data;
import org.cinema.entities.Film;
import org.cinema.entities.Ticket;
import org.cinema.repositories.FilmRepository;
import org.cinema.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CinemaRestController {
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping("/listFilms")
    public List<Film> films(){
        return filmRepository.findAll();
    }
    @GetMapping(path = "/imageFilm/{id}",produces = MediaType.IMAGE_JPEG_VALUE )
    public byte[] image(@PathVariable (name = "id")Long id) throws IOException {
        Film film = filmRepository.findById(id).get();
        String photoname = film.getPhoto();
        File file = new File(System.getProperty("user.home")+"/img/cinema/"+photoname);
        Path path = Paths.get(file.toURI());
        return Files.readAllBytes(path);
    }
    @PostMapping("/payerTickets")
    @Transactional
    public List<Ticket> payerTickets(@RequestBody TicketForm ticketForm){
        List<Ticket> ticketList = new ArrayList<>();
        ticketForm.getTickets().forEach(idTicket
                -> {
            Ticket ticket = ticketRepository.findById(idTicket).get();
            ticket.setNomClient(ticketForm.getNomClient());
            ticket.setCodePayement(ticketForm.getCodePayement());
            ticket.setRserve(true);
            ticketRepository.save(ticket);
            ticketList.add(ticket);
        });
        return ticketList ;
    }

}

@Data
class TicketForm{
    private String nomClient ;
    private int codePayement;
    private List<Long> tickets = new ArrayList<>();
}