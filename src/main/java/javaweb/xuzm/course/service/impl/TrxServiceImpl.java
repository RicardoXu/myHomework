package javaweb.xuzm.course.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javaweb.xuzm.course.dao.TrxDao;
import javaweb.xuzm.course.meta.Buy;
import javaweb.xuzm.course.meta.Product;
import javaweb.xuzm.course.meta.Trx;
import javaweb.xuzm.course.service.ProductService;
import javaweb.xuzm.course.service.TrxServices;

@Service
public class TrxServiceImpl implements TrxServices {
	@Resource
	private TrxDao trxDao;
	@Resource
	private ProductService productService;

	@Transactional
	public int buyProduct(Trx trx) {
		int ret = trxDao.buyProduct(trx);
		return ret;
	}

	public Product show(Trx trx) {
		Product product = new Product();
		product = productService.showProduct(trx.getContentId());
		if (trx.getPersonId() != 0) {
			trx.setPersonId(trx.getPersonId());
		}
		Product product1 = trxDao.sale(trx);
		Product product2 = trxDao.buy(trx);
		if (product1 != null) {
			product.setSaleNum(product1.getSaleNum());
			product.setIsSell(product1.getIsSell());
		} else {
			product.setSaleNum(0);
		}
		if (product2 != null) {
			product.setBuyNum(product2.getBuyNum());
			product.setBuyPrice(product2.getBuyPrice());
			product.setIsBuy(product2.getIsBuy());
		} else {
			product.setBuyNum(0);
		}
		return product;
	}

	public List<Buy> account(int personId) {
		return trxDao.account(personId);
	}
}
