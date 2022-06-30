package dev.powerit.report.controller;

import dev.powerit.report.model.Item;
import dev.powerit.report.service.impl.ItemServiceImpl;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@RestController
@RequestMapping("api/v1/items")
public class ItemController {

    private final ItemServiceImpl itemService;

    public ItemController(ItemServiceImpl itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/all")
    public List<Item> index(){
        return itemService.getAll();
    }

    @PostMapping
    public ResponseEntity<Item> saveItem(@RequestBody Item item){
        return new ResponseEntity<>(itemService.saveItem(item), HttpStatus.CREATED);
    }

    @GetMapping
    public String generateReport(HttpServletResponse response) throws JRException, IOException {
        response.setContentType("application/x-download");
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"item.pdf\""));
        OutputStream outputStream = response.getOutputStream();
        itemService.exportReport(outputStream);
        return "Report was generated";
    }


}
