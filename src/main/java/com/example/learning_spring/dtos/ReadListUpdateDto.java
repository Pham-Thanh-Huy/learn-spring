package com.example.learning_spring.dtos;


import com.example.learning_spring.models.ReadList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ReadListUpdateDto {

    String name;

    public void updateEntity(ReadList readList) {
        if (this.name != null) {
            readList.setName(this.name);
        }
    
    }
}
