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

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Item {
    private String id;
    private String name;
    private String code;
    private String description;

}
