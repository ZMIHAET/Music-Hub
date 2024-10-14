package ru.kashigin.musichub.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kashigin.musichub.model.Album;
import ru.kashigin.musichub.model.Song;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArtistDto {
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 100, message = "Name should be between 2 and 100 characters")
    private String name;

    private String bio;

    private String photo;

    private List<Song> songs;

    private List<Album> albums;
}
