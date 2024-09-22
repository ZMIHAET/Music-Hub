package ru.kashigin.musichub.test;

import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.Test;
import ru.kashigin.musichub.model.Album;
import ru.kashigin.musichub.model.Song;

import java.util.List;
@SpringBootTest
public class AlbumTest {
    @Test
    public void testAlbumAndSongRelationship() {
        Album album = new Album();
        Song song = new Song();
        album.setSongs(List.of(song));
        song.setAlbum(album);
        System.out.println(album);
    }
}
