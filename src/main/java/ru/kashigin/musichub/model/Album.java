package ru.kashigin.musichub.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
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
@Table(name = "album")
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long album_id;

    @NotEmpty(message = "Album title should not be empty")
    @Size(min = 2, max = 100, message = "Album title should be between 2 and 100 characters")
    private String name;

    @NotEmpty(message = "Release year should not be empty")
    @Min(value = 0, message = "Release year should be greater than 0")
    private int release;

    @ManyToOne
    @JoinColumn(name = "genre_id", referencedColumnName = "genre_id")
    private Genre genre;

    @ManyToOne
    @JoinColumn(name = "artist_id", referencedColumnName = "artist_id")
    private Artist artist;

    @OneToMany(mappedBy = "album")
    private List<Song> songs;
}
