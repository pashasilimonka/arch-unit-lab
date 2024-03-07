package edu.sylymonka.arch.Repository;/*
  @author   silim
  @project   arch-unit-test
  @class  ItemRepository
  @version  1.0.0 
  @since 07.03.2024 - 23.54
*/

import edu.sylymonka.arch.Model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends MongoRepository<Item,String> {
}
