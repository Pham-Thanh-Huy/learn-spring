package com.example.learning_spring.utils;

import com.example.learning_spring.dtos.ReadListDto;
import com.example.learning_spring.models.ReadList;
import org.springframework.stereotype.Component;

@Component
public class ReadListMapper {
    public ReadListDto toDto(ReadList readList) {
        ReadListDto readListDto = new ReadListDto();
        readListDto.setId(readList.getId());
        readListDto.setName(readList.getName());
        readListDto.setCreateAt(readList.getCreateAt());
        readListDto.setUpdateAt(readList.getUpdateAt());
        readListDto.setUser(new UserMapper().toDto(readList.getUser()));
        return readListDto;
    }
}
