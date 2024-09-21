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
@Table(name = "artist")
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long artistId;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 100, message = "Name should be between 2 and 100 characters")
    private String name;

    private String bio;

    private String photo;

    @OneToMany(mappedBy = "artist")
    private List<Song> songs;

    @OneToMany(mappedBy = "artist")
    private List<Album> albums;
}
