package edu.sylymonka.arch.Controller;/*
  @author   silim
  @project   arch-unit-test
  @class  ItemController
  @version  1.0.0 
  @since 08.03.2024 - 00.06
*/

import edu.sylymonka.arch.Model.Item;
import edu.sylymonka.arch.Repository.ItemRepository;
import edu.sylymonka.arch.Service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/items/")
public class ItemController {

    private final ItemService service;
    @Autowired
    public ItemController(ItemService service){
        this.service=service;
    }
    @GetMapping("")
    public List<Item> getAllItems(){
        return service.getAllItems();
    }

    @GetMapping("{id}")
    public Item getItemById(@PathVariable String id){
        return service.getItemById(id);
    }
    @PostMapping("")
    public Item addNewItem(@RequestBody Item item){
        return service.createItem(item);
    }
    @DeleteMapping("{id}")
    public void deleteItem(@PathVariable String id){
        service.deleteItem(id);
    }
}
