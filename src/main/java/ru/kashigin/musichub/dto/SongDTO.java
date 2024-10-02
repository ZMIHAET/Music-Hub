package ru.kashigin.musichub.dto;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.kashigin.musichub.model.Album;
import ru.kashigin.musichub.model.Artist;
import ru.kashigin.musichub.model.Genre;
import ru.kashigin.musichub.model.Playlist;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SongDTO {
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 100, message = "Name of song should be between 2 and 100 characters")
    private String name;

    @Min(value = 0, message = "Release year should be greater than 0")
    private int release;

    @Min(value = 0, message = "Duration should be greater than 0")
    private int duration;

    private Genre genre;

    private Artist artist;

    private Album album;

    private List<Playlist> playlists;
}
