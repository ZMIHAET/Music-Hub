package ru.kashigin.musichub.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kashigin.musichub.model.Artist;
import ru.kashigin.musichub.model.Genre;
import ru.kashigin.musichub.model.Song;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlbumDto {
    @NotEmpty(message = "Album title should not be empty")
    @Size(min = 2, max = 100, message = "Album title should be between 2 and 100 characters")
    private String name;

    @Min(value = 0, message = "Release year should be greater than 0")
    private int release;

    private Genre genre;

    private Artist artist;

    private List<Song> songs;
}