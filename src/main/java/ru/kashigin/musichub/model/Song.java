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
@Table(name = "song")
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long song_id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 100, message = "Name of song should be between 2 and 100 characters")
    private String name;

    @NotEmpty(message = "Release year should not be empty")
    @Min(value = 0, message = "Release year should be greater than 0")
    private int release;

    @NotEmpty(message = "Duration should not be empty")
    @Min(value = 0, message = "Duration should be greater than 0")
    private int duration;

    @ManyToOne
    @JoinColumn(name = "genre_id", referencedColumnName = "genre_id")
    private Genre genre;

    @ManyToOne
    @JoinColumn(name = "artist_id", referencedColumnName = "artist_id")
    private Artist artist;

    @ManyToOne
    @JoinColumn(name = "album_id", referencedColumnName = "album_id")
    private Album album;

    @ManyToMany
    @JoinTable(
            name = "playlist_song",
            joinColumns = @JoinColumn(name = "song_id"),
            inverseJoinColumns = @JoinColumn(name = "playlist_id")
    )
    private List<Playlist> playlists;
}
