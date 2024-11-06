package ru.kashigin.musichub.service.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;
import ru.kashigin.musichub.dto.PlaylistDto;
import ru.kashigin.musichub.model.Playlist;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)

public interface PlaylistMapper {
    PlaylistMapper INSTANCE = Mappers.getMapper(PlaylistMapper.class);

    Playlist convertToPlaylist(PlaylistDto playlistDto);
}
