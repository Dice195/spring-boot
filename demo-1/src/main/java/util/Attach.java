package util;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.my.exception.AddException;

public class Attach {
	public static final String SAVE_DIRECTORY = "D://files";

	public static void upload(int no, MultipartFile f) throws AddException {
		File fDir = new File(SAVE_DIRECTORY);
		if (!fDir.exists()) { // 업로드 경로가 없는 경우
			fDir.mkdir();
		}
		String originName = f.getOriginalFilename();
		long fileLength = f.getSize();
		System.out.println("파일이름:" + originName + ", 파일크기:" + fileLength);

		if (fileLength == 0 || "".equals(originName)) {
			throw new AddException("첨부파일이 비었거나 파일이름이 없습니다");
		}

		String saveFileName = no + "_" + UUID.randomUUID() + "_" + originName;
		File saveFile = new File(SAVE_DIRECTORY, saveFileName);
		try {
			// 원본의 내용을 복사본에 붙여넣기
			FileCopyUtils.copy(f.getBytes(), saveFile);
		} catch (IOException e) {
			e.printStackTrace();
			throw new AddException(e.getMessage());
		}
	}

	public static void upload(int no, List<MultipartFile> list) throws AddException {
		for (MultipartFile f : list) {
			upload(no, f);
		}
	}
}