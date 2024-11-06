package ru.kashigin.musichub.service.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;
import ru.kashigin.musichub.dto.PersonDto;
import ru.kashigin.musichub.model.Person;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PersonMapper {
    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    Person convertToPerson(PersonDto personDto);
}
