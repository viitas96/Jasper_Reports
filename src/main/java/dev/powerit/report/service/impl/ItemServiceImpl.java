package dev.powerit.report.service.impl;

import dev.powerit.report.model.Item;
import dev.powerit.report.repository.ItemRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemServiceImpl implements dev.powerit.report.service.ItemService {

    private final ItemRepository itemRepository;

    public void exportReport(OutputStream outputStream) throws FileNotFoundException, JRException {
        List<Item> items = getAll();
        File file = ResourceUtils.getFile("classpath:items.jrxml");
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(items);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("createdBy", "Clima Victor");
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            JasperExportManager.exportReportToPdfStream(jasperPrint,outputStream);
        } catch (Exception e){
            System.out.println(e);
        }

    }

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public List<Item> getAll() {
        return itemRepository.findAll();
    }

}
