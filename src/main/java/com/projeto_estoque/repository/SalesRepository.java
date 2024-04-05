package com.projeto_estoque.repository;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.projeto_estoque.infra.DAO;
import com.projeto_estoque.model.Response;
import com.projeto_estoque.model.ResponseInfo;
import com.projeto_estoque.model.Sale;

import jakarta.persistence.TypedQuery;


public class SalesRepository extends DAO<Sale>{
    
    public SalesRepository(){
        super(Sale.class);
    }

    public Sale getById(Long id){
        return entityManager.find(this.entityClass, id);
    }

    public List<Sale> getAllSaleByProductName(String productName){
        TypedQuery<Sale> query = entityManager.createQuery(
                "SELECT s FROM Sale s WHERE s.productName = :productName",
                Sale.class);  
        query.setParameter("productName", productName);
        try {
            return query.getResultList();
        } catch (Exception e) {
            return Collections.emptyList(); 
        }   
    }

    public List<Sale> getAllSaleByCategorie(String categorie){
        TypedQuery<Sale> query = entityManager.createQuery(
                "SELECT s FROM Sale s WHERE s.categories = :categorie",
                Sale.class);  
        query.setParameter("categorie", categorie);
        try {
            return query.getResultList();
        } catch (Exception e) {
            return Collections.emptyList(); 
        }    
    }
    
    public List<Sale> getAllSaleByProductNameAndCategorie(String productName, String categorie){
        TypedQuery<Sale> query = entityManager.createQuery(
            "SELECT s FROM Sale s WHERE s.categories = :categorie AND s.productName = :productName",
            Sale.class);
        query.setParameter("productName", productName);
        query.setParameter("categorie", categorie);
        try {
            return query.getResultList();
        } catch (Exception e) {
            return Collections.emptyList(); 
        }  
    }

    public List<String> getAllProductName(){
        TypedQuery<String> query = entityManager.createQuery(
            "SELECT s.productName AS quantity FROM Sale s group by s.productName",
            String.class);  
        try {
            return query.getResultList();
        } catch (Exception e) {
            return Collections.emptyList(); 
        }
    }

    public List<String> getAllCategorieName(){
        TypedQuery<String> query = entityManager.createQuery(
            "SELECT s.categories AS quantity FROM Sale s group by s.categories",
            String.class);  
        try {
            return query.getResultList();
        } catch (Exception e) {
            return Collections.emptyList(); 
        }
    }

    public List<ResponseInfo> getInfosByProductNameAndCategories(){
        TypedQuery<Object[]> query = entityManager.createQuery(
            "SELECT s.productName, s.categories, SUM(s.quantity) FROM Sale s GROUP BY s.productName, s.categories",
            Object[].class);

        List<Object[]> resultList = query.getResultList();

        return resultList.stream()
                .map(result -> {
                    String productName = (String) result[0];
                    String categorie = (String) result[1];
                    Long quantity = (Long) result[2];
                    return new ResponseInfo(productName, categorie, quantity.longValue());
                })
                .collect(Collectors.toList());
    }

    public Response save(Sale input) {
        
        if(input.getId() == null){
            this.addAtomicTransaction(input);
            Response response = new Response("Requisição de adicionar foi efetuada com sucesso.");
            return response;    
        }else{
            this.updateAtomicTransaction(input);
            Response response = new Response("Requisição de update foi efetuada com sucesso.");
            return response;
        }     
    }

    public void deleteById(Sale request) {
        this.removeAtomicTransaction(request);
    }

}
