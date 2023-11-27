package com.api.javalabsapi.controllers;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.javalabsapi.dto.ProductRecordDto;
import com.api.javalabsapi.model.ProductModel;
import com.api.javalabsapi.repositories.ProductRepository;
import jakarta.validation.Valid;

/*
 * Baseado no modelo de maturidade de Leonard Richardson
 * 0 - deve usar o protocolo HTTP 
 * 1 - tenha recusos bem definidos e os dados das uri, utilizando substantivos dentro dos padrões ex: /products
 * 2 - utilize os métodos HTTP de forma semantica GET, POST, DELETE, PUT, PATCH etc
 * 3 - HATEOAS significa Hypermedia as the Engine of Application State. Fornece links que indicarão como poderá ser feita a navegação entre seus recursos
 */
@RestController
public class ProductControler {
    
    @Autowired
    ProductRepository productRepository;

    @GetMapping("/products")
	public ResponseEntity<List<ProductModel>> getAllProducts(){
		//List<ProductModel> productsList = productRepository.findAll();
        /*
		if(!productsList.isEmpty()) {
			for(ProductModel product : productsList) {
				UUID id = product.getIdProduct();
				//product.add(linkTo(methodOn(ProductController.class).getOneProduct(id)).withSelfRel());
			}
		}*/
		return ResponseEntity.status(HttpStatus.OK).body(productRepository.findAll());
	}

    @GetMapping("/products/{id}")
	public ResponseEntity<Object> getOneProduct(@PathVariable(value="id") UUID id){
		Optional<ProductModel> productId = productRepository.findById(id);

		if(productId.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
		}
		//productO.get().add(linkTo(methodOn(ProductController.class).getAllProducts()).withRel("Products List"));

		return ResponseEntity.status(HttpStatus.OK).body(productId.get());
	}

    @PutMapping("/products/{id}")
	public ResponseEntity<Object> updateProduct(@PathVariable(value="id") UUID id, @RequestBody @Valid ProductRecordDto productRecordDto) {
		Optional<ProductModel> productId = productRepository.findById(id);
		if(productId.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
		}
		var productModel = productId.get();
		BeanUtils.copyProperties(productRecordDto, productModel);
		return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(productModel));
	}

    @PostMapping("/products")
	public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductRecordDto productRecordDto) {
		var productModel = new ProductModel();
		BeanUtils.copyProperties(productRecordDto, productModel); //converte o DTO para módel
		return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));
	}
     
	@DeleteMapping("/products/{id}")
	public ResponseEntity<Object> deleteProduct(@PathVariable(value="id") UUID id) {
		Optional<ProductModel> productId = productRepository.findById(id);
		if(productId.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
		}
		productRepository.delete(productId.get());
		return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully.");
	}  
	
}
