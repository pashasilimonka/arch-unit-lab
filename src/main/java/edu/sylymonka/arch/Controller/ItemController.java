package edu.sylymonka.arch.Controller;/*
  @author   silim
  @project   arch-unit-test
  @class  ItemController
  @version  1.0.0 
  @since 08.03.2024 - 00.06
*/

import edu.sylymonka.arch.Model.Item;
import edu.sylymonka.arch.Service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ItemController {
    @Autowired
    private ItemService service;

    @GetMapping("api/v1/items")
    public List<Item> getAllItems(){
        return service.getAllItems();
    }

    @GetMapping("api/v1/items/{id}")
    public Item getItemById(@PathVariable String id){
        return service.getItemById(id);
    }
    @PostMapping("api/v1/items")
    public Item addNewItem(@RequestBody Item item){
        return service.createItem(item);
    }
    @DeleteMapping("api/v1/items/{id}")
    public void deleteItem(@PathVariable String id){
        service.deleteItem(id);
    }
}
