package ru.kashigin.musichub.service.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;
import ru.kashigin.musichub.dto.AlbumDto;
import ru.kashigin.musichub.model.Album;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AlbumMapper {
    AlbumMapper INSTANCE = Mappers.getMapper(AlbumMapper.class);

    Album convertToAlbum(AlbumDto albumDto);

    AlbumDto convertToAlbumDto(Album album);
}
