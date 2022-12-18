package control;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.service.OrderService;
import com.my.vo.Customer;
import com.my.vo.OrderInfo;
import com.my.vo.OrderLine;
import com.my.vo.Product;

@Controller
public class OrderController {
	
	@Autowired OrderService service;
	
	@GetMapping("addorder")
	@ResponseBody
	public Map<String,Object>add(HttpServletRequest request) {
		
		ObjectMapper mapper = new ObjectMapper();
		Map<String,Object> map = new HashMap<>();
		HttpSession session = request.getSession();
		Map<String,Integer> cart = (Map)session.getAttribute("cart");
		String id = (String)session.getAttribute("loginedId");
		
		
		if(cart == null || cart.size() == 0) { 
			map.put("status", 0);
			map.put("msg", "장바구니가 비었습니다");
		}else {
			//장바구니내용을 OrderInfo객체화한다
			OrderInfo info = new OrderInfo();
			//info.setOrderNo(0);
			//info.setOrderDt(null);
			Customer c = new Customer();
			c.setId(id);
			info.setOrderC(c);
			
			List<OrderLine> lines = new ArrayList<>();
			for(String prodNo : cart.keySet()) {
				int quantity = cart.get(prodNo);
				OrderLine line = new OrderLine();
				Product p = new Product();
				p.setProdNo(prodNo);
				line.setOrderp(p);
				line.setOrderQuantity(quantity);
				lines.add(line);
			}
			info.setLines(lines);
			
			try {
				service.addOrder(info);				
				session.removeAttribute("cart");				
				map.put("status", 1);				
			} catch (AddException e) {
				e.printStackTrace();
				map.put("status", 0);
				map.put("msg", e.getMessage());
			}
		}		
		return map;
	}
	
	@GetMapping("orderlist")
	public ModelAndView list(HttpServletRequest request,HttpSession session) {
		ModelAndView mnv = new ModelAndView();
		
		mnv.addObject("orderlist");
		
		String id = (String)session.getAttribute("loginedId");
		if(id == null) {
			request.setAttribute("status", 0);
			request.setAttribute("msg", "로그인하세요");
		}else {
			try {
				List<OrderInfo> infos = service.findById(id);
				request.setAttribute("status", 1);
				request.setAttribute("orderlist", infos);
						
			} catch (FindException e) {
				e.printStackTrace();
				request.setAttribute("status", 0);
				request.setAttribute("msg", e.getMessage());
			}
		}
		return mnv;
	}

	
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		response.setHeader("Access-Control-Allow-Origin",request.getHeader("origin"));
//		response.setHeader("Access-Control-Allow-Credentials", "true");
//		
//		HttpSession session = request.getSession();
//		String id = (String)session.getAttribute("loginedId");
//		if(id == null) {
//			request.setAttribute("status", 0);
//			request.setAttribute("msg", "로그인하세요");
//		}else {
//			try {
//				List<OrderInfo> infos = service.findById(id);
//				request.setAttribute("status", 1);
//				request.setAttribute("orderlist", infos);
//						
//			} catch (FindException e) {
//				e.printStackTrace();
//				request.setAttribute("status", 0);
//				request.setAttribute("msg", e.getMessage());
//			}
//		}
//		RequestDispatcher rd = request.getRequestDispatcher("orderlist.jsp");
//		rd.forward(request, response);		
//	}

}