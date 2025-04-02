package ru.kashigin.musichub.service.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;
import ru.kashigin.musichub.dto.SongDto;
import ru.kashigin.musichub.model.Song;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SongMapper {
    SongMapper INSTANCE = Mappers.getMapper(SongMapper.class);

    Song convertToSong(SongDto songDto);
    SongDto convertToSongDto(Song song);
}
