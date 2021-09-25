package org.cinema.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Ville implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id ;
    private  String name;
    private double longitude;
    private double latitude ;
    private double altitude ;

    @OneToMany(mappedBy = "ville")
    private Collection<Cinema> cinema ;

}
