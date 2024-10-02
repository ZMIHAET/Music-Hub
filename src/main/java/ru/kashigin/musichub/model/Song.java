package ru.kashigin.musichub.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@Table(name = "song")
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "song_id")
    private Long songId;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 100, message = "Name of song should be between 2 and 100 characters")
    private String name;

    @Min(value = 0, message = "Release year should be greater than 0")
    private int release;

    @Min(value = 0, message = "Duration should be greater than 0")
    private int duration;

    @ManyToOne
    @JoinColumn(name = "genre_id", referencedColumnName = "genre_id")
    @ToString.Exclude
    private Genre genre;

    @ManyToOne
    @JoinColumn(name = "artist_id", referencedColumnName = "artist_id")
    @ToString.Exclude
    private Artist artist;

    @ManyToOne
    @JoinColumn(name = "album_id", referencedColumnName = "album_id")
    @ToString.Exclude
    private Album album;

    @ManyToMany
    @JoinTable(
            name = "playlist_song",
            joinColumns = @JoinColumn(name = "song_id"),
            inverseJoinColumns = @JoinColumn(name = "playlist_id")
    )
    private List<Playlist> playlists;
}
