package com.example.zeitplaner.web.mapper;

import com.example.zeitplaner.domain.model.Termin;
import com.example.zeitplaner.web.dto.TerminDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface TerminMapper {

    @Mapping(target = "kategorie.id", source = "kategorieId")
    Termin dtoZuEntity(TerminDto dto);

    @Mapping(target = "kategorieId", source = "kategorie.id")
    TerminDto entityZuDto(Termin entity);
}
