package ru.kashigin.musichub.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kashigin.musichub.model.Person;
import ru.kashigin.musichub.model.Song;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistDto {
    @NotEmpty(message = "Playlist title should not be empty")
    @Size(min = 2, max = 100, message = "Playlist title should be between 2 and 100 characters")
    private String name;

    private String description;

    private Person owner;

    private List<Song> songs;
}
