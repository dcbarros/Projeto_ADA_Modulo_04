package com.projeto_estoque.model;

public class ResponseInfo {

    private String productName;
    private String categorie;
    private String registers;

    public ResponseInfo(String productName, String categorie, String registers) {
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
    public String getRegisters() {
        return registers;
    }
    public void setRegisters(String registers) {
        this.registers = registers;
    }
    
}
