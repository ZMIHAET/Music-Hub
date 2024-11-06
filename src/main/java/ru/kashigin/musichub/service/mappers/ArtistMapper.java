package ru.kashigin.musichub.service.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;
import ru.kashigin.musichub.dto.ArtistDto;
import ru.kashigin.musichub.model.Artist;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ArtistMapper {
    ArtistMapper INSTANCE = Mappers.getMapper(ArtistMapper.class);

    Artist convertToArtist(ArtistDto artistDto);
}
