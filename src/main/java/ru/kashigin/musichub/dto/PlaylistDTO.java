package ru.kashigin.musichub.dto;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.kashigin.musichub.model.Person;
import ru.kashigin.musichub.model.Song;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistDTO {
    @NotEmpty(message = "Playlist title should not be empty")
    @Size(min = 2, max = 100, message = "Playlist title should be between 2 and 100 characters")
    private String name;

    private String description;

    private Person owner;

    private List<Song> songs;
}
