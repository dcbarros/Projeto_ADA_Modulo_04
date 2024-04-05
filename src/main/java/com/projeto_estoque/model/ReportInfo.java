package com.projeto_estoque.model;

import java.math.BigDecimal;
import java.util.Map;

public class ReportInfo {

    private String productName;
    private Integer registers;
    private Map<String,Integer> maxStockPerCategories;
    private Map<String,Integer> consumerPerCategories;
    private Map<String,Integer> lowStockPerCategories;
    private Map<String,BigDecimal> costToRenovePerCategories;
    
    public ReportInfo(String productName, Map<String, Integer> maxStockPerCategories,
            Map<String, Integer> consumerPerCategories, Map<String, Integer> lowStockPerCategories,
            Map<String, BigDecimal> costToRenovePerCategories, Integer registers) {
        this.productName = productName;
        this.maxStockPerCategories = maxStockPerCategories;
        this.consumerPerCategories = consumerPerCategories;
        this.lowStockPerCategories = lowStockPerCategories;
        this.costToRenovePerCategories = costToRenovePerCategories;
        this.registers = registers;
    }

    public ReportInfo() {
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Map<String, Integer> getMaxStockPerCategories() {
        return maxStockPerCategories;
    }

    public void setMaxStockPerCategories(Map<String, Integer> maxStockPerCategories) {
        this.maxStockPerCategories = maxStockPerCategories;
    }

    public Map<String, Integer> getConsumerPerCategories() {
        return consumerPerCategories;
    }

    public void setConsumerPerCategories(Map<String, Integer> consumerPerCategories) {
        this.consumerPerCategories = consumerPerCategories;
    }

    public Map<String, Integer> getLowStockPerCategories() {
        return lowStockPerCategories;
    }

    public void setLowStockPerCategories(Map<String, Integer> lowStockPerCategories) {
        this.lowStockPerCategories = lowStockPerCategories;
    }

    public Map<String, BigDecimal> getCostToRenovePerCategories() {
        return costToRenovePerCategories;
    }

    public void setCostToRenovePerCategories(Map<String, BigDecimal> costToRenovePerCategories) {
        this.costToRenovePerCategories = costToRenovePerCategories;
    }

    public Integer getRegisters() {
        return registers;
    }

    public void setRegisters(Integer registers) {
        this.registers = registers;
    } 
}
