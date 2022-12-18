package control;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class CartController {
	
	
	@GetMapping("putcart")
	@ResponseBody
	public void put(String prodNo, int quantity, HttpSession session) {
		
		Map<String, Integer> cart = (Map)session.getAttribute("cart");
		if(cart == null) {
			cart = new HashMap<>();
			session.setAttribute("cart", cart);
		}
		
		Integer quantity2 = cart.get(prodNo);
		if (quantity2 != null) {	
			quantity += quantity2;	
		}
		cart.put(prodNo, quantity);
		System.out.println(prodNo+ "=" + cart.get(prodNo));
	}
	
	@GetMapping("cartlist")
	@ResponseBody
	public Map<String,Object> list(HttpSession session) {
		
		Map<String, Integer> cart = (Map)session.getAttribute("cart");
		Map<String, Object> map = new HashMap<>();
		
		if(cart == null || cart.size() == 0) {
			map.put("status", 0);
		}else {
			map.put("status", 1);
			map.put("cart", cart);
		}
		return map;
		
	}
	
}
	
	
	

