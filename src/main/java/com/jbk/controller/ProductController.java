package com.jbk.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jbk.exceptions.ResourceAlreadyExistException;
import com.jbk.exceptions.ResourceNotExist;
import com.jbk.model.CategoryModel;
import com.jbk.model.ProductModel;
import com.jbk.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService productService;

	@PostMapping("/add-product")
	public ResponseEntity<String> addProduct(@RequestBody @Valid ProductModel productModel) {

		int status = productService.addProduct(productModel);

		if (status == 1) {
			return new ResponseEntity<String>("Product Added Successfully !!", HttpStatus.CREATED);
		} else if (status == 2) {
			throw new ResourceAlreadyExistException("Product Already Exists, Check Unique Fields");

		} else {
			return new ResponseEntity<String>("Something Went Wrong", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@GetMapping("/get-product-by-id/{product-id}")
	public ResponseEntity<ProductModel> getProductById(@PathVariable("product-id") long productId) {
		
		ProductModel productModel = productService.getProductById(productId);
		if(productModel!=null) {
			return new ResponseEntity<ProductModel>(productModel,HttpStatus.FOUND);
		}else {
			throw new ResourceNotExist("Product Not Found - Id = "+productId);
		}	
	}
	
	@GetMapping("/get-all-product")
	public ResponseEntity<List<ProductModel>> getAllProduct(){
		
		List<ProductModel> allProduct = productService.getAllProducts();
		
		return new ResponseEntity<List<ProductModel>>(allProduct,HttpStatus.FOUND);
		
	}
	
}
