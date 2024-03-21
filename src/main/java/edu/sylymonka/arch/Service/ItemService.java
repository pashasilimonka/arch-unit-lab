package edu.sylymonka.arch.Service;/*
  @author   silim
  @project   arch-unit-test
  @class  ItemService
  @version  1.0.0 
  @since 07.03.2024 - 23.55
*/

import edu.sylymonka.arch.Model.Item;
import edu.sylymonka.arch.Repository.ItemRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ItemService {

    private final ItemRepository repository;

    @Autowired
    public ItemService(ItemRepository repository){
        this.repository=repository;
    }
    private final List<Item> itemList = Arrays.asList(
            new Item("1","Item 1","0000001","Item 1 for arch unit"),
            new Item("2","Item 2","0000002","Item 2 for arch unit "),
            new Item("3","Item 3","0000003","Item 3 for arch unit "),
            new Item("4","Item 4","0000004","Item 4 for arch unit "),
            new Item("5","Item 5","0000005","Item 5 for arch unit ")
    );

    @PostConstruct
    void init(){
        repository.deleteAll();
        repository.saveAll(itemList);
    }

    public List<Item> getAllItems() {
        return repository.findAll();
    }

    public Item getItemById(String id){
        return repository.findById(id).orElse(new Item("","","",""));
    }

    public Item createItem(Item item){
        return repository.save(item);
    }
    public void deleteItem(String id){
        repository.deleteById(id);
    }
}
