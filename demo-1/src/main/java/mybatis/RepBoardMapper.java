package mybatis;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.my.vo.RepBoard;
@Mapper //RepBoardMapper.xml 대신 사용된다.
public interface RepBoardMapper {
	@Select("SELECT rb.*, c.name \"boardC.name\", rb.board_id \"boardC.id\"\r\n"
			+ "	FROM rep_board rb JOIN customer c ON rb.board_id = c.id\r\n"
			+ "	WHERE board_no=#{boardNo}")
	RepBoard selectByBoardNo(int boardNo);
	
	@Select("SELECT level, rb.*, c.name \"boardC.name\", rb.board_id \"boardC.id\"\r\n"
			+ "	FROM rep_board rb JOIN customer c ON rb.board_id = c.id\r\n"
			+ "	START WITH parent_no = 0\r\n"
			+ "	CONNECT BY prior board_no=parent_no\r\n"
			+ "	ORDER SIBLINGS BY board_no DESC")
	List<RepBoard> selectAll();
	
	@Select("SELECT *\r\n"
			+ "FROM (SELECT rownum rm, a.* \r\n"
			+ "      FROM (\r\n"
			+ "       SELECT level, rb.*, c.name \"boardC.name\", rb.board_id \"boardC.id\"\r\n"
			+ "       FROM rep_board rb JOIN customer c ON rb.board_id = c.id\r\n"
			+ "       START WITH parent_no = 0\r\n"
			+ "       CONNECT BY prior board_no=parent_no\r\n"
			+ "        ORDER SIBLINGS BY board_no DESC\r\n"
			+ "      )a\r\n"
			+ ")WHERE rm BETWEEN #{startRow} AND #{endRow}\r\n"
			)
	List<RepBoard> selectAllPage(Map<String,Integer> map);
	
	@Select("SELECT COUNT(*) FROM rep_board")
	int selectCount();
	@SelectKey(keyProperty = "boardNo", resultType = Integer.class, before = false, statement = "SELECT rep_board_seq.CURRVAL from dual")
	@Insert("INSERT INTO rep_board(board_no, parent_no,   board_title,    board_id,     board_content)\r\n"
			+ "VALUES (rep_board_seq.NEXTVAL, #{parentNo}, #{boardTitle}, #{boardC.id}, #{boardContent}) "
			)
	void insert(RepBoard rb);
	
	@Update("UPDATE rep_board SET board_view_cnt = board_view_cnt+1\r\n"
			+ "WHERE board_no=#{boardNo} ")
	int updateViewCnt(int boardNo);

	/* 동적 SQL 처리용 updaterrovider 을작성해야한다. 
	 * @Update("UPDATE rep_board\r\n" +
	 * "SET board_title=#{boardTitle}, board_content = #{boardContent}\r\n" +
	 * "WHERE board_no=#{boardNo} AND board_id=#{boardC.id}") int update(RepBoard
	 * rb);
	 */
	@Delete("DELETE rep_board\r\n"
			+ "WHERE board_no IN ( SELECT board_no\r\n"
			+ "FROM rep_board\r\n"
			+ "START WITH board_no=#{boardNo} \r\n"
			+ "CONNECT BY PRIOR board_no = parent_no)")
	int delete(int boardNo);
}
