package org.cinema.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor
public class Cinema implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String name ;
    private double longitude ;
    private double laltitude ;
    private double atitude ;
    private int nombresSalles ;

    @ManyToOne
    private Ville ville ;

    @OneToMany(mappedBy = "cinema")
    private Collection<Salle> salles ;
}
