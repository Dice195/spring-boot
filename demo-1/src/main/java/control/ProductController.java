package control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.dto.PageBean;
import com.my.exception.FindException;
import com.my.service.ProductService;
import com.my.vo.Product;


@Controller
public class ProductController {
	
	@Autowired
	private ProductService service;
	
//	public Map<String,Object> productlist(int currentPage) throws FindException {
//		Map<String,Object> map = new HashMap<>();
//		List<Product> list = service.findAll(currentPage, PageBean.CNT_PER_PAGE);
//		map.put("list", list);
//		
//		return map;
//
//	}
	
	//http://localhost:8888/mvc/productlist?currentPage=3

	@GetMapping(value="productlist")
	@ResponseBody
	public  Map<String,Object> productlist(int currentPage) throws FindException{
		ObjectMapper mapper = new ObjectMapper();
		Map<String,Object> map = new HashMap<>();
		
		try {
			PageBean<Product> pb = service.getPageBean(currentPage);				//서비스에게 넘겨서처리한다.
			
			map.put("status", 1);
			map.put("pb", pb);
			
		} catch (FindException e) {
			e.printStackTrace();	
			map.put("status", 0);			
		}
		return map;
	}
	
	//http://localhost:8888/mvc/productdetail2?prodNo=C0001
//	@GetMapping("productdetail2")
//	@ResponseBody
//	public Product productdetail2(String prodNo) throws FindException {
//		Product p = service.findByNo(prodNo);
//		return p; 
//
//	}
//	
	//http://localhost:8888/mvc/detail?prodNo=C0001
	@GetMapping("productdetail")
	@ResponseBody
	public Map<String,Object> detail(//HttpServletRequest request, 
									 //HttpServletResponse response,
										String prodNo) {
		
//		response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
//		response.setHeader("Access-Control-Allow-Credentials","true");
	
		Map<String, Object>map = new HashMap<>();
		try {
			Product p = service.findByNo(prodNo);
			map.put("status", 1);
			map.put("p", p);			
		} catch (FindException e) {
			e.printStackTrace();
			map.put("status", 0);
			map.put("msg", e.getMessage());						
		}
		return map;
		
	}

	
}
