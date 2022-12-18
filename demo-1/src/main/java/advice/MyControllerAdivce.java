package advice;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
public class MyControllerAdivce {
	
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ResponseEntity<?> except(Exception e) {
		System.out.println("Exception handler: e.className:"+ e.getClass().getName());
		return new ResponseEntity<>(e.getMessage() ,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	@ResponseBody
	public ResponseEntity<?> exceptMaxUploadSize(MaxUploadSizeExceededException e){
		HttpHeaders resHeaders = new HttpHeaders();
		resHeaders.add("Content-Type", "application/json;charset=UTF-8"); 
		resHeaders.add("Access-Control-Allow-Origin","http://192.168.2.20:9999");
		resHeaders.add("Access-Control-Allow-Credentials","true");
		return new ResponseEntity<>("파일이 너무큽니다.", resHeaders, HttpStatus.BAD_REQUEST);
	}

}






