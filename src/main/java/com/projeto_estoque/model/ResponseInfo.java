package com.projeto_estoque.model;

public class ResponseInfo {

    private String productName;
    private String categorie;
    private Long registers;

    public ResponseInfo(String productName, String categorie, Long registers) {
        this.productName = productName;
        this.categorie = categorie;
        this.registers = registers;
    }
    
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getCategorie() {
        return categorie;
    }
    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }
    public Long getRegisters() {
        return registers;
    }
    public void setRegisters(Long registers) {
        this.registers = registers;
    }
    
}
