package edu.sylymonka.arch.Model;/*
  @author   silim
  @project   arch-unit-test
  @class  Item
  @version  1.0.0 
  @since 07.03.2024 - 23.52
*/

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Item {
    @Id
    private String id;
    private String name;
    private String code;
    private String description;

    public Item(String name, String code, String description) {
        this.name = name;
        this.code = code;
        this.description = description;
    }
}
