package javaweb.xuzm.course.web.controller;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javaweb.xuzm.course.meta.Buy;
import javaweb.xuzm.course.meta.Content;
import javaweb.xuzm.course.meta.Person;
import javaweb.xuzm.course.meta.Product;
import javaweb.xuzm.course.meta.Trx;
import javaweb.xuzm.course.meta.User;
import javaweb.xuzm.course.service.ContentService;
import javaweb.xuzm.course.service.PersonService;
import javaweb.xuzm.course.service.ProductService;
import javaweb.xuzm.course.service.TrxServices;
import javaweb.xuzm.course.utils.SesssionOP;

@Controller
public class DefaultController {
	@Resource
	private SesssionOP sesssionOP;
	@Resource
	private ContentService contentService;
	@Resource
	private PersonService personService;
	@Resource
	private ProductService productService;
	@Resource
	private TrxServices trxServices;

	// Session
	public User getUser(HttpServletRequest request, ModelMap map) {
		User user = sesssionOP.getSession(request);
		map.addAttribute("user", user);
		return user;
	}
	
	// 同步数据
	// 1.登录页
	@RequestMapping("/login")
	public String login(HttpServletRequest request, ModelMap map) {
		getUser(request, map);
		return "login";
	}

	// 2.退出
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		sesssionOP.destroySession(request);
		return "redirect:/login";
	}

	// 3.展示页
	@RequestMapping("/")
	public String home(HttpServletRequest request, ModelMap map) {
		Integer personId = 0;
		User user = getUser(request, map);
		if (user != null) {
			String name = user.getUsername();
			personId = personService.id(name);
		}
		List<Product> productList = productService.productList();
		map.addAttribute("productList", productList);
		return "index";
	}

	// 4.查看页
	@RequestMapping("/show")
	public String show(@RequestParam("id") int id, HttpServletRequest request, ModelMap map) {
		// 根据id去数据库查询商品信息
		Trx trx = new Trx();
		trx.setContentId(id);
		User user = getUser(request, map);
		if (user != null) {
			String name = user.getUsername();
			trx.setPersonId(personService.id(name));
		} else {
			trx.setPersonId(0);
		}
		Product product = trxServices.show(trx);
		map.addAttribute("product", product);
		return "show";
	}

	// 5.账务页
	@RequestMapping("/account")
	public String account(HttpServletRequest request, ModelMap map) {
		User user = getUser(request, map);
		if (user != null) {
			int personId = 1;
			List<Buy> buyList = trxServices.account(personId);
			// 数据库中查询交易记录
			map.addAttribute("buyList", buyList);
			return "account";
		} else {
			return "redirect:/login";
		}
	}

	// 6.发布页
	@RequestMapping("/public")
	public String publicPage(HttpServletRequest request, ModelMap map) {
		User user = getUser(request, map);
		if (user != null) {
			return "public";
		} else {
			return "redirect:/login";
		}
	}

	// 7.发布提交页
	@RequestMapping("/publicSubmit")
	public String publicSubmit(@RequestParam("title") String title, @RequestParam("image") String image,
			@RequestParam("detail") String detail, @RequestParam("price") Double price,
			@RequestParam("summary") String summary, HttpServletRequest request, ModelMap map) {
		User user = getUser(request, map);
		// 加入商品信息
		Content content = new Content();
		content.setTitle(title);
		content.setIcon(image);
		content.setText(detail);
		content.setPrice(price);
		content.setAbst(summary);
		// 将商品信息写入数据库，作为事务执行
		contentService.addProduct(content);
		// 从数据库查询刚刚写入的商品信息
		Integer id = null;
		id = contentService.chenkProduct(content);
		if (id != null) {
			content.setId(id);
		} else {
			content = null;
		}
		map.addAttribute("product", content);
		return "publicSubmit";
	}

	// 8.编辑页
	@RequestMapping("/edit")
	public String edit(@RequestParam("id") int id, HttpServletRequest request, ModelMap map) {
		User user = getUser(request, map);
		if (user != null) {
			Product product = productService.showProduct(id);
			map.addAttribute("product", product);
			return "edit";
		} else {
			return "redirect:/login";
		}
	}

	// 9.编辑提交
	@RequestMapping("/editSubmit")
	public String editSubmit(@RequestParam("id") int id, @RequestParam("title") String title,
			@RequestParam("image") String image, @RequestParam("detail") String detail,
			@RequestParam("price") Double price, @RequestParam("summary") String summary, HttpServletRequest request,
			ModelMap map) {
		// 加入商品信息
		Content content = new Content();
		content.setId(id);
		content.setTitle(title);
		content.setIcon(image);
		content.setText(detail);
		content.setPrice(price);
		content.setAbst(summary);
		// 将商品信息写入数据库，作为事务执行
		contentService.editProduct(content);
		// 从数据库查询刚刚写入的商品信息
		Integer id1 = null;
		id1 = contentService.chenkProduct(content);
		if (id1 != null) {
			content.setId(id1);
		} else {
			content = null;
		}
		map.addAttribute("product", content);
		return "publicSubmit";
	}

	// 异步数据
	// 1.登录
	@ResponseBody
	@RequestMapping("/api/login")
	public Object checklogin(@RequestParam("userName") String userName, @RequestParam("password") String password,
			HttpServletRequest request, HttpServletResponse response, ModelMap map) {
		Person person = new Person();
		person.setUserName(userName);
		person.setPassword(password);
		// 数据库中查询
		Integer type = null;
		String name = null;
		name = personService.userlogin(person);
		// 如果返回值不为null，则是合法用户
		if (name != null) {
			type = personService.findtype(person);
			// 创建Session
			User user = new User();
			user.setUsername(userName);
			user.setUsertype(type);
			sesssionOP.setSession(user, request);
			// 登录成功
			response.setStatus(200);
			map.addAttribute("code", 200);
			map.addAttribute("message", "Success");
			map.addAttribute("return", true);
			return map;
		} else {
			response.setStatus(400);
			map.addAttribute("code", 400);
			map.addAttribute("message", "用户名或者密码错误");
			map.addAttribute("return", false);
			return map;
		}
	}

	// 2.删除产品
	@ResponseBody
	@RequestMapping("/api/delete")
	public Object delete(@RequestParam("id") int id, HttpServletRequest request, HttpServletResponse response,
			ModelMap map) {
		Boolean del = contentService.delProduct(id);
		if (del) {
			response.setStatus(200);
			map.addAttribute("code", 200);
			map.addAttribute("message", "Success");
			map.addAttribute("return", true);
			return map;
		} else {
			response.setStatus(400);
			map.addAttribute("code", 400);
			map.addAttribute("message", "删除失败");
			map.addAttribute("return", false);
			return map;
		}

	}

	// 3.购买
	@ResponseBody
	@RequestMapping("/api/buy")
	public Object buy(@RequestParam("id") int id, HttpServletRequest request, HttpServletResponse response,
			ModelMap map) {
		Trx trx = new Trx();
		Date date = new Date();
		Long time = date.getTime();
		User user = getUser(request, map);
		if (user != null) {
			String name = user.getUsername();
			trx.setPersonId(personService.id(name));
		} else {
			trx.setPersonId(0);
		}

		trx.setContentId(id);
		trx.setNum(1);
		trx.setTime(time);
		trx.setPrice(contentService.productPrice(id));
		trx.setBuy("1");
		int i = trxServices.buyProduct(trx);

		if (i != 0) {
			response.setStatus(200);
			map.addAttribute("code", 200);
			map.addAttribute("message", "Success");
			map.addAttribute("return", true);
			return map;
		} else {
			response.setStatus(400);
			map.addAttribute("code", 400);
			map.addAttribute("message", "购买失败");
			map.addAttribute("return", false);
			return map;
		}
	}
}
