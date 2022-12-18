package mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.my.vo.OrderInfo;
import com.my.vo.OrderLine;

@Mapper
public interface OrderMapper {
	
	
	@Insert("INSERT INTO order_info(order_no, order_id , order_dt)\r\n"
			+ "	VALUES (order_seq.NEXTVAL, #{orderId}, SYSDATE)")
	void insertInfo(String orderId);
		
	@Insert("INSERT INTO order_line(order_no, order_prod_no, order_quantity)\r\n"
			+ "	VALUES (order_seq.CURRVAL, #{orderp.prodNo},#{orderQuantity})")
	void insertLine(OrderLine line);
	
	@ResultMap("com.my.mybatis.OrderMapper.OrderInfoResultMap")
	@Select("SELECT info.*,\r\n"
			+ "	line.order_prod_no, line.order_quantity,\r\n"
			+ "	p.prod_name, p.prod_price, p.prod_info\r\n"
			+ "	FROM order_info info \r\n"
			+ "	JOIN order_line line ON (info.order_no = line.order_no)\r\n"
			+ "	JOIN product p ON (line.order_prod_no = p.prod_no)\r\n"
			+ "	WHERE order_id = #{orderId}\r\n"
			+ "	ORDER BY info.order_no DESC	")
	List<OrderInfo> selectById(String orderId);
}
