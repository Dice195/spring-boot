package control;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.my.vo.Customer;

@Controller
public class TestController {
	
	@GetMapping("a")
	@ResponseBody
	public String a() {
		return "a...";
	}
	//http://localhost:8888/mvc/b?one=hello&num=3
	//http://localhost:8888/mvc/b?num=3
	//http://localhost:8888/mvc/b?one=hi
	@GetMapping("b")
	@ResponseBody
	public String b(@RequestParam(defaultValue="hello")String one, @RequestParam(defaultValue="0")int num) {
//		Integer.parseInt(num)*10; 
		return "요청전달데이터는 one=" + one + ", num=" + num;
	}
	
	//http://localhost:8888/mvc/c?one=a&one=b&one=c&two=d
	//http://localhost:8888/mvc/c?two=d
	
	
//	http://localhost:8888/mvc1/c?one=a&one=b&two=c
//	http://localhost:8888/mvc1/c?two=c

	String returnValue;
	
	@GetMapping("c")
	@ResponseBody
	public String c(@RequestParam(name = "one" )Optional<String[]>optOne, String two) {
		returnValue = "요청전달데이터";
//		if(optOne.isPresent()) {
//			String[] arr = optOne.get();
//			for(String o: arr) {
//				returnValue += "one=" + o +", ";
//			}
//		}		
		optOne.ifPresent(arr ->{
			for(String o: arr) {
				returnValue += "one=" + o +", ";
			}
		});
		returnValue += "two=" + two;
		return returnValue;
	}
	
	
	//http://localhost:8888/mvc/d?id=id1&pwd=p1&name=n1
	@GetMapping("d")
	@ResponseBody
	public String d(Customer customer) {		//커맨드 객체 
		return "요청된 고객정보는" + customer.getId() + ":" + customer.getName();
	}
	
	//json 문자혈 형태로 응답이된다.
	@GetMapping("e")
	@ResponseBody
	public Customer e() {
		return new Customer("id1","p1","김태현");
	}
	
	@GetMapping("f")
	@ResponseBody
	public List<Customer> f() {
		List<Customer> list = new ArrayList<>();
		list.add(new Customer("id1", "p1", "김태현"));
		list.add(new Customer("id2", "p2", "김태현"));
		list.add(new Customer("id3", "p3", "김태현"));
		return list;
		
	}
	
	@GetMapping(value="g", produces="application/json;charset=utf-8")
	public ResponseEntity<String>  g() {
//		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		ResponseEntity<String> re = new ResponseEntity<String>(
				"이미 존재하는 아이디입니다.",HttpStatus.NOT_FOUND);
		return re;
		//응답코드를 조작 한다.
	}
	
	@GetMapping("h")
	public String h(){
		System.out.println("h controller...");
//		return "h.jsp";
		return "h";
	}
	
	@GetMapping("i")
	public ModelAndView i() {
		ModelAndView mnv = new ModelAndView();
//		mnv.setViewName("i.jsp");
		mnv.setViewName("i");
		mnv.addObject("result", new String("view에서 사용할 정보"));
		//request.setAttribute("result", new String("viewer에서 사용할 정보"));와 같음
		return mnv;
	}

	@GetMapping("j")
	public void j(Model model) {
		model.addAttribute("result",new String("viewer에서 사용할 정보"));
	}
	
//	@PostMapping("upload")
	@ResponseBody
	public void k(HttpServletRequest request) throws IOException {
		request.setCharacterEncoding("utf-8");

		InputStream is = request.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String readValue =null;
		while ((readValue = br.readLine()) != null) {
			System.out.print(readValue);
		}
	}
		
		@PostMapping("upload")
		@ResponseBody
		public void l(HttpServletRequest request,
				@RequestPart List<MultipartFile> f,
				@RequestPart MultipartFile fImg,		//요청전달데이터가 오지않으면 오류발생- 내용이 반드시 있는것이 아니다.
				String t) throws IOException {
//			request.setCharacterEncoding("utf-8");
			
			//파일 업로드 경로 생성
			String saveDirectory = "D:\\files";
			File fDir = new File(saveDirectory); 
			if(!fDir.exists()) {
				fDir.mkdir();
			//객체와 연결되어있는 경로가있는 확인한다.
			}
			System.out.println("요청전달데이터 t=" + t);
			System.out.println("업로드파일개수:" + f.size());
			System.out.println();
			for(MultipartFile mf : f) {								//업로드된 파일은 Mf가지고있다가
				String originName = mf.getOriginalFilename();
				long fileLength = mf.getSize();
				System.out.println("파일이름:" + originName + ", 파일크기:"+ fileLength);	
				//원본의 내용을 복사본에 붙여넣기 
//				String saveFileName = originName;
				//중복되지않는 임의 문자열값을 
				int boardNo = 1;
				String saveFileName =boardNo + "_" + UUID.randomUUID() + "_" + originName;

				File saveFile = new File(saveDirectory,originName);		//save 붙여넣기 작업을 해준다.
				FileCopyUtils.copy(mf.getBytes(), saveFile);			//원본의 내용을 복사본에 붙여넣는다.
			}
			
			//이미지 파일 처리
			long imgSize = fImg.getSize();
			String imgOriginName = fImg.getOriginalFilename();
			System.out.println("이미지파일 크기:" + imgSize);
			System.out.println("이미지파일 이름:" + imgOriginName);
			
			if(imgSize == 0 || "".equals(imgOriginName)) {
				System.out.println("이미지파일이 첨부되지 않았습니다.");
			}		//이미지가 있는지 체크한다.
		}
		@GetMapping(value="download", produces = {"application/json;charset=utf-8"})
		@ResponseBody
		public ResponseEntity<?> download(String fileName){
			String saveDirectory = "D:\\files";
			File file = new File(saveDirectory, fileName);		
			try {
				if(!file.exists()) {
					throw new IOException("파일이 없습니다");
				}
				HttpHeaders responseHeaders = new HttpHeaders();
				responseHeaders.set(HttpHeaders.CONTENT_LENGTH, file.length()+""); //응답길이 
				
				String contentType = Files.probeContentType(file.toPath());
				System.out.println("Files.probeContentType(file.toPath())=" + contentType); //응답형식을 얻어온다.
				responseHeaders.set(HttpHeaders.CONTENT_TYPE, Files.probeContentType(file.toPath()));
				if(contentType.startsWith("image/")) { //이미지파일인경우 바로 응답
					
					responseHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename="+URLEncoder.encode(fileName, "UTF-8"));
				}else { //이미지파일이 아닌경우 다운로드 inline 경우 그대로 보여주는것이고 
					
					responseHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attechment; filename="+URLEncoder.encode(fileName, "UTF-8"));
				}
				return new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), //FileCopyUtils.copyToByteArray(file),바이트 배열타입으로 응답한다.  
	                    responseHeaders, 
	                    HttpStatus.OK);		//원본파일의 내용을 스트링타입으로 응답해줘야하는데 그걸 도와주는 라이브러리 파일 copyutiles 스프링 프레임워크 라이브러리 
			} catch (IOException e) {
				e.printStackTrace();
				return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		}

}	
