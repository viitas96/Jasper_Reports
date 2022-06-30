package dev.powerit.report.service;

import dev.powerit.report.model.Item;

import java.util.List;

public interface ItemService {
    Item saveItem(Item item);
    List<Item> getAll();
}
