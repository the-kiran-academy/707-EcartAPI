package com.jbk.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbk.dao.ProductDao;
import com.jbk.entity.ProductEntity;
import com.jbk.exceptions.ResourceNotExist;
import com.jbk.model.ProductModel;
import com.jbk.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private ProductDao productDao;

	@Override
	public int addProduct(ProductModel productModel) {
		return productDao.addProduct(mapper.map(productModel, ProductEntity.class));
	}

	@Override
	public ProductModel getProductById(long productId) {
ProductEntity productEntity = productDao.getProductById(productId);
		
		System.out.println("in service");
		ProductModel productModel =null;
		
		if(productEntity!=null) {
			 productModel=mapper.map(productEntity, ProductModel.class);
		}
			return productModel;
	}

	@Override
	public List<ProductModel> getAllProducts() {
		List<ProductEntity> allProduct = productDao.getAllProducts();
		List<ProductModel> list=null;
		if(!allProduct.isEmpty()) {
			
		 list = allProduct.stream().map(entity -> mapper.map(entity, ProductModel.class)).collect(Collectors.toList());
			
		}else {
			throw new ResourceNotExist("Product Not Exists");
		}
		return list;
	}

}
