package com.jbk.service;

import java.util.List;

import com.jbk.model.ProductModel;

public interface ProductService {
	public int addProduct(ProductModel productModel);
	public ProductModel getProductById(long productId);
	public List<ProductModel> getAllProducts();
	public int updateProduct(ProductModel  productModel);
	public int deleteProduct(long productId);
}
