package com.example.zeitplaner.web.mapper;

import com.example.zeitplaner.domain.model.Aufgabe;
import com.example.zeitplaner.web.dto.AufgabeDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AufgabeMapper {

    @Mapping(target = "kategorie.id", source = "kategorieId")
    @Mapping(target = "prioritaet", source = "prioritaet") // Enum als String
    Aufgabe dtoZuEntity(AufgabeDto dto);

    @Mapping(target = "kategorieId", source = "kategorie.id")
    AufgabeDto entityZuDto(Aufgabe entity);
}
