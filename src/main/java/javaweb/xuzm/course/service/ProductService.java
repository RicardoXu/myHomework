package javaweb.xuzm.course.service;

import java.util.List;

import javaweb.xuzm.course.meta.Product;

public interface ProductService {

	// 查询商品List
	List<Product> productList();

	// show页面，根据id查询商品
	Product showProduct(int id);
}
