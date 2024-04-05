package com.projeto_estoque.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.projeto_estoque.model.ReportInfo;
import com.projeto_estoque.model.Sale;
import com.projeto_estoque.service.SaleService;

public class DataManipulation {

    public static List<Sale> csvReader() throws IOException {
        Reader reader = Files.newBufferedReader(Paths.get("src\\main\\java\\com\\projeto_estoque\\data\\sellers.csv"));
        CsvToBean<Sale> csvToBean = new CsvToBeanBuilder<Sale>(reader)
                                    .withType(Sale.class)
                                    .withIgnoreLeadingWhiteSpace(true)
                                    .build();
        return csvToBean.parse();        
    }

    public static ReportInfo dataReport(String productName, SaleService saleService, Map<String,Integer> maxStockPerCategories) {
        
        Map<String,Integer> consumerPerCategories = new HashMap<>();
        Map<String,Integer> lowStockPerCategories = new HashMap<>();
        Map<String,BigDecimal> costToRenovePerCategories = new HashMap<>();

        List<String> categories = Arrays.asList("SuperPremium", "Premium" , "Comum");

        categories.forEach((c) -> {
            @SuppressWarnings("unchecked")
            List<Object> objectList = (List<Object>) saleService.getAllSaleByCategorieAndProductName(c, productName)
                                                                .orElse(Collections.emptyList());
            List<Sale> sales = objectList.stream()
                .map(obj -> (Sale) obj)
                .collect(Collectors.toList());
            
            
            BigDecimal meanCost = BigDecimal.ZERO;
            if(sales.size() != 0){
                meanCost = sales.stream().map(Sale::getCosts).reduce(BigDecimal.ZERO, BigDecimal::add).divide(BigDecimal.valueOf(sales.size()), RoundingMode.HALF_UP);
            }
            
            consumerPerCategories.put(c, sales.stream().mapToInt(Sale::getQuantity).sum());  
            if(consumerPerCategories.get(c) > 15000){
                lowStockPerCategories.put(c, maxStockPerCategories.get(c) - consumerPerCategories.get(c));
                costToRenovePerCategories.put(c, BigDecimal.valueOf(consumerPerCategories.get(c)).multiply(meanCost));   
            }
        });
        @SuppressWarnings("unchecked")
        List<Object> register = (List<Object>) saleService.getByProductName(productName)
                                                                .orElse(Collections.emptyList());

        return new ReportInfo(productName, maxStockPerCategories, consumerPerCategories, lowStockPerCategories, costToRenovePerCategories, register.size());

    }

    public static void showReports(List<ReportInfo> reports) {
        
        reports.stream().forEach(r -> {
            System.out.printf("O produto: %s, possui %d registros\n", r.getProductName(), r.getRegisters());
            System.out.println("=========================================");
            System.out.println("O Estoque máximo da loja");
            r.getMaxStockPerCategories().forEach((k,v)-> {
                System.out.printf("Categoria: %s - Estoque Máximo: %d kg\n", k,v);
            });
            System.out.println("=========================================");
            System.out.println("Consumo por categorias");
            r.getConsumerPerCategories().forEach((k,v)-> {
                System.out.printf("Categoria: %s - Consumo: %d kg\n", k,v);
            });
            System.out.println("=========================================");
            System.out.println("Categorias com estoque crítico");
            r.getLowStockPerCategories().forEach((k,v)-> {
                System.out.printf("Categoria: %s - Estoque: %d kg\n", k,v);
            });
            System.out.println("=========================================");
            System.out.println("Custo de renovação do estoque");
            r.getCostToRenovePerCategories().forEach((k,v)-> {
                System.out.printf("Categoria: %s - Custo: R$ %.2f\n", k,v.doubleValue());
            });
            System.out.println("=========================================");
            System.out.printf("O TOTAL CONSUMIDO DA RAÇÃO %s FOI: %d kg\n", r.getProductName(),
                                                                            r.getConsumerPerCategories()
                                                                                .values()
                                                                                .stream()
                                                                                .mapToInt(Integer::intValue)
                                                                                .sum()
            );
            System.out.println("=========================================\n");
        });
    }

    public static void saveReports(List<ReportInfo> reports) {

        for (ReportInfo report : reports) {
            try (PrintStream out = new PrintStream(new FileOutputStream("src/main/java/com/projeto_estoque/data/" + report.getProductName() + ".txt"))) {
                out.printf("O produto: %s, possui %d registros\n", report.getProductName(), report.getRegisters());
                out.println("=========================================");
                out.println("O Estoque máximo da loja");
                report.getMaxStockPerCategories().forEach((category, stock) -> {
                    out.printf("Categoria: %s - Estoque Máximo: %d kg\n", category, stock);
                });
                out.println("=========================================");
                out.println("Consumo por categorias");
                report.getConsumerPerCategories().forEach((category, consumption) -> {
                    out.printf("Categoria: %s - Consumo: %d kg\n", category, consumption);
                });
                out.println("=========================================");
                out.println("Categorias com estoque crítico");
                report.getLowStockPerCategories().forEach((category, stock) -> {
                    out.printf("Categoria: %s - Estoque: %d kg\n", category, stock);
                });
                out.println("=========================================");
                out.println("Custo de renovação do estoque");
                report.getCostToRenovePerCategories().forEach((category, cost) -> {
                    out.printf("Categoria: %s - Custo: R$ %.2f\n", category, cost.doubleValue());
                });
                out.println("=========================================");
                out.printf("O TOTAL CONSUMIDO DA RAÇÃO %s FOI: %d kg\n", report.getProductName(),
                        report.getConsumerPerCategories()
                                .values()
                                .stream()
                                .mapToInt(Integer::intValue)
                                .sum()
                );
                out.println("=========================================\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void cleanConsole(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
