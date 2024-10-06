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
@Table(name = "album")
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "album_id")
    private Long albumId;

    @NotEmpty(message = "Album title should not be empty")
    @Size(min = 2, max = 100, message = "Album title should be between 2 and 100 characters")
    private String name;

    @Min(value = 0, message = "Release year should be greater than 0")
    private int release;

    @ManyToOne
    @JoinColumn(name = "artist_id", referencedColumnName = "artist_id")
    @ToString.Exclude
    private Artist artist;

    @OneToMany(mappedBy = "album")
    private List<Song> songs;
}
