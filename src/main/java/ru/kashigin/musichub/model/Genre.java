package ru.kashigin.musichub.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "genre")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long genre_id;

    @NotEmpty(message = "Name of genre should not be empty")
    @Size(min = 2, max = 100, message = "Name of genre should be between 2 and 100 characters")
    private String name;

    private String description;

    @OneToMany(mappedBy = "genre")
    private List<Song> songs;

    @OneToMany(mappedBy = "genre")
    private List<Album> albums;
}
