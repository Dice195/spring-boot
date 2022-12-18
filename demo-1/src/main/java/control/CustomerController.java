package control;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.service.CustomerService;
import com.my.vo.Customer;


@Controller
public class CustomerController {

	@Autowired
	private CustomerService service;
	
	
	@GetMapping("logout")
	@ResponseBody
	public void logout(HttpServletRequest request){
		HttpSession session = request.getSession();
		session.removeAttribute("loginedId");
		session.invalidate();
	}
	
	@PostMapping("login")
	@ResponseBody
	public Map<String,Object> login(HttpServletRequest request) {
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
	
		HttpSession session = request.getSession();
		System.out.println("11111111111****************" + session.getId());
		session.removeAttribute("loginedId");
		Map<String, Object>map = new HashMap<>();
		try {
			service.login(id,pwd);
			session.setAttribute("loginedId",id);
			map.put("status", 1);
			map.put("msg", "로그인성공");			
		} catch (FindException e) {			
			map.put("status", 0);
			map.put("msg", "로그인실패");	
			e.printStackTrace();			
		} 
		return map;
	}
	
	@GetMapping("idDupChk")
	@ResponseBody
	public Map<String,Object> idDupchk(String id) throws FindException {
		
//		String id = request.getParameter("id");
		Map<String, Object>map = new HashMap<>();
		try {
			service.idDupchk(id);
			map.put("status", 0);
			map.put("msg", "이미 사용중인 아이디입니다.");				
		} catch (FindException e) {			
			map.put("status", 1);
			map.put("msg", "사용하실수있는 아이디입니다.");			
			e.printStackTrace();			
		} 
		return map;
			
	}
	
	@PostMapping("signUp")
	@ResponseBody
	public Map<String,Object> signup(
			//HttpServletRequest request,
			Customer c) {

//		String id = request.getParameter("id");
//		String pwd = request.getParameter("pwd");
//		String name = request.getParameter("name");
//		Customer c = new Customer(id, pwd, name);
		Map<String, Object>map = new HashMap<>();
		try {
			service.signup(c);
			map.put("status", 1);
			map.put("msg", "가입성공");
		} catch (AddException e) {
			e.printStackTrace();
			map.put("status", 0);
			map.put("msg", e.getMessage());
		}
		return map;
	}
	
}
