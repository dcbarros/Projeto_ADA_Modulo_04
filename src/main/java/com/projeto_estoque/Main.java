package com.projeto_estoque;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.projeto_estoque.model.ReportInfo;
import com.projeto_estoque.model.Response;
import com.projeto_estoque.model.Sale;
import com.projeto_estoque.service.SaleService;
import com.projeto_estoque.utils.DataManipulation;

public class Main {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {

        SaleService saleService = new SaleService();
    
        if(!(saleService.getById(1L).get() instanceof Sale)){
            DataManipulation.csvReader().forEach( d -> {
                saleService.createNewSale(d);
            });
        }

        DataManipulation.cleanConsole();
        try {
            List<ReportInfo> productReport = new ArrayList<>();

            List<Object> objectList = (List<Object>) saleService.getAllCategorieName()
                                                                .orElse(Collections.emptyList());
            List<String> categories = objectList.stream()
                                                .map(obj -> (String) obj)
                                                .collect(Collectors.toList());
                                                
            Map<String,Integer> maxStockPerCategories = new HashMap<>();
            categories.forEach( c -> {
                maxStockPerCategories.put(c,21000);
            });
            
            objectList = (List<Object>) saleService.getAllProductName()
                                                .orElse(Collections.emptyList());
            List<String> productName = objectList.stream()
                                                .map(obj -> (String) obj)
                                                .collect(Collectors.toList());       
    
            productName.forEach(p -> {
                productReport.add(DataManipulation.dataReport(p, saleService, maxStockPerCategories));
            });
            DataManipulation.cleanConsole();
            DataManipulation.showReports(productReport);
            DataManipulation.saveReports(productReport);

        } catch (Exception e) {
            Response response = new Response("Ocorreu um erro:");
            response.setContent(e.getMessage());
        }

    }

    
}