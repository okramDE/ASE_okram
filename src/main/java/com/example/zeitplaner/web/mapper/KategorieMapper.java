package com.example.zeitplaner.web.mapper;

import com.example.zeitplaner.domain.model.Kategorie;
import com.example.zeitplaner.web.dto.KategorieDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface KategorieMapper {

    Kategorie dtoZuEntity(KategorieDto dto);

    KategorieDto entityZuDto(Kategorie entity);
}
