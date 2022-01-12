package com.suleymankayabasi.fourthhomework.mapper;

import com.suleymankayabasi.fourthhomework.dto.CollectionDTO;
import com.suleymankayabasi.fourthhomework.model.Collection;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CollectionMapper {

    CollectionMapper INSTANCE = Mappers.getMapper(CollectionMapper.class);

    Collection convertCollectionDTOtoCollection(CollectionDTO collectionDTO);

    CollectionDTO convertCollectionToCollectionDTO(Collection collection);

    List<Collection> convertCollectionDTOtoCollectionList(List<CollectionDTO> collectionDTOList);

    List<CollectionDTO> convertCollectionListToCollectionDTOList(List<Collection> collectionList);
}
