package control;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.my.dto.PageBean;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.exception.RemoveException;
import com.my.service.RepBoardService;
import com.my.vo.Customer;
import com.my.vo.RepBoard;

import util.Attach;

//@Controller
public class RepBoardController {
	@Autowired
	private RepBoardService service;
		
	@PostMapping("write")
	@ResponseBody
	public ResponseEntity<?> write(HttpSession session,
			@RequestPart List<MultipartFile> f,
			@RequestPart MultipartFile fImg, 
			RepBoard rb) throws AddException {
		String loginedId = (String)session.getAttribute("loginedId");
		Customer c = new Customer();
		c.setId(loginedId);
		rb.setBoardC(c);
//		try {
			int boardNo = service.writeBoard(rb);
			Attach.upload(boardNo, f);
			Attach.upload(boardNo, fImg);
			return new ResponseEntity<>(HttpStatus.OK);
//		} catch (AddException e) {
//			e.printStackTrace();
//			return new ResponseEntity<>(e.getMessage() ,HttpStatus.INTERNAL_SERVER_ERROR);
//		}
			//메서드 매개변수 예외 처리 1
			//메서드 내부에서 예외 처리 2 
	}
	
	@GetMapping("boardlist")
	@ResponseBody
	public ResponseEntity<?> list(int currentPage) throws FindException {
			PageBean<RepBoard> pb = service.getPageBean(currentPage);
			return new ResponseEntity<>(pb, HttpStatus.OK);
	}
	
	@GetMapping("boarddetail")
	@ResponseBody
	public ResponseEntity<?> boarddetail(int boardNo) throws ModifyException, FindException {
			RepBoard rb = service.findByBoardNo(boardNo);
			//글번호별 첨부파일의 이름들을 같이 응답
			List<String>fileNames = new ArrayList<>();
			String saveDirectory =  "D://files";
			File dir = new File(saveDirectory);
			String[] allFileNamess = dir.list();
			for(String fn : allFileNamess) {
				if(fn.startsWith(boardNo + "_")) {
					fileNames.add(fn);
				}
			}
			Map<String, Object> map = new HashMap<> ();
			map.put("rb", rb);
			map.put("fileNames", fileNames);
			
			return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	@PostMapping("boardmodify")
	@ResponseBody
	public ResponseEntity<?> modify(int boardNo, String boardTitle, String boardContent, HttpSession session/*,
									@RequestPart List<MultipartFile> f, @RequestPart MultipartFile fImg*/) throws ModifyException {
		String loginedId = (String)session.getAttribute("loginedId");
		RepBoard rb = new RepBoard();
		rb.setBoardNo(boardNo);
		rb.setBoardTitle(boardTitle);
		rb.setBoardContent(boardContent);
		Customer c = new Customer();
		c.setId(loginedId);
		rb.setBoardC(c);
			service.modify(rb);
			
			Map<String, Object> map = new HashMap<>();
			map.put("boardTitle", boardTitle);
			map.put("boardContent", boardContent);
			
//			boolean flag = false; //첨부가 되지 않은 경우
//			for(MultipartFile mf : f) {
//				String orignName = mf.getOriginalFilename();
//				long fileLength = mf.getSize();
//				if(fileLength > 0 && !"".equals(orignName)) {
//					flag = true;
//					break;
//				}
//			}
			
//			String orignImgName = fImg.getOriginalFilename();
//			long imgFileLength = fImg.getSize();
//			if(imgFileLength > 0 && !"".equals(orignImgName)) {
//				flag = true;
//			}
//			if(flag) { //첨부된 경우 기존 첨부파일들을 모두 삭제(저장 경로에서 boardNo 값으로 시작하는 파일들을 찾아서 삭제)
//				String saveDirectory = "/Users/user/Desktop/files";
//				File dir = new File(saveDirectory);
//				File[] files = dir.listFiles();
//				for(File f1 : files) {
//					if(f1.getName().startsWith(boardNo + "_")) {
//						f1.delete();
//					}
//				}
//				
//			}
//			// 새로운 첨부파일 저장
//			Attach.upload(boardNo, f);
//			Attach.upload(boardNo, fImg);	
			return new ResponseEntity<>(HttpStatus.OK);
//		} catch (AddException e) {
//			e.printStackTrace();
//			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("boardremove")
	@ResponseBody
	public ResponseEntity<?> remove(int boardNo
			) throws RemoveException {
//
			service.remove(boardNo);
			//첨부된 경우 기존 첨부파일들을 모두 삭제(저장 경로에서 boardNo 값으로 시작하는 파일들을 찾아서 삭제)
//			String saveDirectory = "D://files";
//			File dir = new File(saveDirectory);
//			File[] files = dir.listFiles();
//			for(File f1 : files) {
//				if(f1.getName().startsWith(boardNo + "_")) {
//					f1.delete();
//				}
//			}
			return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("reply")
	@ResponseBody
	public ResponseEntity<?> reply(HttpSession session,
//			@RequestPart List<MultipartFile> f, 
//			@RequestPart MultipartFile fImg, 
			RepBoard rb) throws AddException {
		String loginedId = (String)session.getAttribute("loginedId");
//		String loginedId = "id1";
		Customer c = new Customer();
		c.setId(loginedId);
		rb.setBoardC(c);
			int boardNo = service.writeReply(rb);
//			Attach.upload(boardNo, f);
//			Attach.upload(boardNo, fImg);	
			return new ResponseEntity<>(HttpStatus.OK);
	}
}
