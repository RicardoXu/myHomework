package javaweb.xuzm.course.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import javaweb.xuzm.course.dao.ProductDao;
import javaweb.xuzm.course.meta.Product;
import javaweb.xuzm.course.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	@Resource
	private ProductDao productDao;

	public List<Product> productList() {
		return productDao.productList();
	}

	public Product showProduct(int id) {
		return productDao.showProduct(id);
	}
}
