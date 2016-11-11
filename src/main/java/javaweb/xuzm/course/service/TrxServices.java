package javaweb.xuzm.course.service;

import java.util.List;

import javaweb.xuzm.course.meta.Buy;
import javaweb.xuzm.course.meta.Product;
import javaweb.xuzm.course.meta.Trx;

public interface TrxServices {

	// 购买商品
	int buyProduct(Trx trx);

	// show页面
	public Product show(Trx trx);

	// 账务页面
	public List<Buy> account(int personId);
}
