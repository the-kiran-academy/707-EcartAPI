package com.jbk.dao.impl;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.jbk.dao.ProductDao;
import com.jbk.entity.ProductEntity;
import com.jbk.exceptions.SomethingWentWrongException;

@Repository
public class ProductDaoImpl implements ProductDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	

	@Override
	public int addProduct(ProductEntity productEntity) {
		int status = 0;
		try {
			Session session = sessionFactory.openSession();
			session.save(productEntity);
			session.beginTransaction().commit();
			status = 1;
		} 
		catch (RollbackException e) {
			
			status=3;
		}
		
		catch (PersistenceException e) {
			status = 2;
		}

		catch (Exception e) {
			status = 3;
			e.printStackTrace();
		}

		return status;
	}

	@Override
	public ProductEntity getProductById(long productId) {
		ProductEntity productEntity = null;
		try {
			Session session = sessionFactory.openSession();
			productEntity = session.get(ProductEntity.class, productId);
		} catch (Exception e) {
			throw new SomethingWentWrongException("Something Went Wrong");

		}
		return productEntity;
	}

	@Override
	public List<ProductEntity> getAllProducts() {
		List<ProductEntity> list=null;
		try {
			Session session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(ProductEntity.class);
			list = criteria.list();  // from ProductEntity 

		} catch (Exception e) {
			e.printStackTrace();
			throw new SomethingWentWrongException("Something Went Wrong During Retrive All Product !");
		}
		return list;
	}

}
