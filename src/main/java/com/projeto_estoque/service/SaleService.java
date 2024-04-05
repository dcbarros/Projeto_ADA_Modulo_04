package com.projeto_estoque.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import com.projeto_estoque.model.Response;
import com.projeto_estoque.model.ResponseInfo;
import com.projeto_estoque.model.Sale;
import com.projeto_estoque.repository.SalesRepository;

public class SaleService {
    
    private final SalesRepository salesRepository;

    public SaleService() {
        this.salesRepository = new SalesRepository();
    }
    
    public Optional<Object> getById(Long id){
        Sale sale = new Sale();
        try{
            if(id == null) {
                Response response = new Response("Requisição não pode ser efetuada.");
                response.setContent("O id introduzido não pode ser vazio.");
                return Optional.of(response);
            }
            sale = salesRepository.getById(id);
            if(sale == null){
                Response response = new Response("Requisição não pode ser efetuada.");
                response.setContent("O id não foi encontrado nos registros");
                return Optional.of(response);
            }
            return Optional.of(sale);
        }catch(Exception e){
            Response response = new Response("Requisição não pode ser efetuada.");
            response.setContent("Ocorreu um problema na busca do objeto requisitado");
            return Optional.of(response);
        }
    }

    public Optional<Object> getByProductName(String productName){
        if(productName == null || productName.isBlank()){
            Response response = new Response("Requisição não pode ser efetuada.");
            response.setContent("O nome do produto requisitado está vazio!");
            return Optional.of(response);
        }
        return Optional.of(salesRepository.getAllSaleByProductName(productName));
    }

    public Optional<Object> getAllSaleByCategorie(String categorie){
        if(categorie == null || categorie.isBlank()){
            Response response = new Response("Requisição não pode ser efetuada.");
            response.setContent("A categoria requisitada está vazia!");
            return Optional.of(response);
        }
        return Optional.of(salesRepository.getAllSaleByProductName(categorie));
    }

    public Optional<Object> getAllSaleByCategorieAndProductName(String categorie, String productName) {
        if(categorie == null || productName == null || categorie.isBlank() || productName.isBlank()){
            Response response = new Response("Requisição não pode ser efetuada.");
            response.setContent("As categorias requisitadas estão vazias!");
            return Optional.of(response);
        }
        return Optional.of(salesRepository.getAllSaleByProductNameAndCategorie(productName, categorie));

    }
    
    public Optional<Object> getAllCategorieName(){
        List<String> categories = salesRepository.getAllCategorieName();
        if(categories.isEmpty()){
            Response response = new Response("Requisição falhou.");
            response.setContent("Não existem dados no banco");
            return Optional.of(response);
        }
        return Optional.of(categories);
    }

    public Optional<Object> getAllProductName(){
        List<String> productsName = salesRepository.getAllProductName();
        if(productsName.isEmpty()){
            Response response = new Response("Requisição falhou.");
            response.setContent("Não existem dados no banco");
            return Optional.of(response);
        }
        return Optional.of(productsName);
    }

    public Optional<Object> getConsumByProductNameAndCategorie(){
        List<ResponseInfo> responseInfos = salesRepository.getInfosByProductNameAndCategories();
        if(responseInfos.isEmpty()){
            Response response = new Response("Requisição falhou.");
            response.setContent("Não existem dados no banco");
            return Optional.of(response);
        }
        return Optional.of(responseInfos);
    }

    public Response createNewSale(Sale input) {
        if(input == null) {
            Response response = new Response("Requisição não pode ser efetuada.");
            response.setContent("O objeto requisitado está vazio!");
            return response;
        };
        return salesRepository.save(input);
    }

    public Response updateSale(Long id, Sale updateRequest) {
        if(id == null) {
            Response response = new Response("Requisição não pode ser efetuada.");
            response.setContent("O id introduzido não pode ser vazio.");
            return response;
        }
        Sale sale = salesRepository.getById(id);
        if(sale == null) {
            Response response = new Response("Requisição não pode ser efetuada.");
            response.setContent("O objeto requisitado não existe no banco de dados!");
            return response;
        };
        Function<Sale, Sale> updateFunction = s -> {
            if(s.getProductName() != null || !s.getProductName().isBlank() || !s.getProductName().equals(updateRequest.getProductName())){
                s.setProductName(updateRequest.getProductName());
            }
            if(s.getCategories() != null || !s.getCategories().isBlank() || !s.getCategories().equals(updateRequest.getCategories())){
                s.setCategories(updateRequest.getCategories());
            }
            if(s.getQuantity() != null || s.getQuantity() != updateRequest.getQuantity()){
                s.setQuantity(updateRequest.getQuantity());
            }
            if(s.getCosts() != null || s.getCosts() != updateRequest.getCosts()){
                s.setCosts(updateRequest.getCosts());
            }
            return s;
        };
        return salesRepository.save(updateFunction.apply(sale));
    }

    public Response deleteSale(Long id) {
        if(id == null) {
            Response response = new Response("Requisição não pode ser efetuada.");
            response.setContent("O id introduzido não pode ser vazio.");
            return response;
        }
        Optional<Object> optionalObject = getById(id);
        if(optionalObject.isPresent()){
            if(optionalObject.get() instanceof Sale){
                this.salesRepository.deleteById((Sale) optionalObject.get());
                Response response = new Response("Requisição de apagar foi efetuada com sucesso.");
                return response;
            }else{
                return (Response) optionalObject.get();
            }
        }
        return null;
    }

}
