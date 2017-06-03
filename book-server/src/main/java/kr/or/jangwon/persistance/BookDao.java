package kr.or.jangwon.persistance;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.or.jangwon.domain.Book;

// component-scan을 통해 생성자 주입을 쓸 수 있다.(DataSource)
// BookDao는 사용할 DataSource 객체를 직접 결정하지 않고 외부에서 주입된 DataSource를 의존한다.
@Repository
public class BookDao {
	// NamedParameterJdbcTemplate: 이름이 붙여진 파라미터가 들어간 SQL을 호출.
	// 멀티스레드에서 접근해도 안전하기 때문에 매번 객체를 생성할 필요는 없다. -> 클래스 멤버변수로 두고 참조할 수 있다.
	private NamedParameterJdbcTemplate jdbc;
	// Insert 쿼리를 자동생성(INSERT구문을 작성하지 않고도 DB에 데이터를 저장할 수 있다.)
	private SimpleJdbcInsert insertAction;
	
	// RowMapper: ResultSet에서 값을 추출하여 원하는 객체로 변환
	// BeanPropertyRowMapper: ResultSet -> Bean으로 변환
	private RowMapper<Book> rowMapper = BeanPropertyRowMapper.newInstance(Book.class);
	
	// SQLs
	private static final String COUNT_BOOK = "SELECT COUNT(*) FROM book";
	private static final String SELECT_ALL =
			"SELECT id, title, author, pages FROM book";
	private static final String SELECT_BY_ID =
			"SELECT id, title, author, pages FROM book where id = :id";
	static final String DELETE_BY_ID = "DELETE FROM book WHERE id= :id";
	private static final String UPDATE =
			"UPDATE book SET\n"
			+ "title = :title,"
			+ "author = :author,"
			+ "pages = :pages\n"
			+ "WHERE id = :id";
	
	// DataSource 인터페이스는 DB에서 Connection 객체를 얻어오는데 쓰이는 인터페이스
	// Spring JDBC에서 DB에 연결하는 역할은 위의 DataSource 인터페이스에 의존하고 있다.
	public BookDao(DataSource dataSource) {
		// NamedParameterJdbcTemplate: 이름이 붙여진 파라미터가 들어간 SQL을 호출.
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		// Insert 쿼리를 자동생성
		this.insertAction = new SimpleJdbcInsert(dataSource)
				.withTableName("book")
				.usingGeneratedKeyColumns("id");
	}
	
	public int countBooks() {
		Map<String, Object> params = Collections.emptyMap();
		return jdbc.queryForObject(COUNT_BOOK, params, Integer.class);
	}
	
	public List<Book> selectAll() {
		Map<String, Object> params = Collections.emptyMap();
		return jdbc.query(SELECT_ALL, params, rowMapper);
	}

	public Book selectById(Integer id) {
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		return jdbc.queryForObject(SELECT_BY_ID, params, rowMapper);
	}
	
	public Integer insert(Book book) {
		// SqlParameterSource: SQL에 파라미터 전달
		// BeanPropertySqlParameterSource: Bean 객체로 파라미터 전달
		SqlParameterSource params = new BeanPropertySqlParameterSource(book);
		return insertAction.executeAndReturnKey(params).intValue();
	}

	public int deleteById(Integer id) {
		Map<String, ?> params = Collections.singletonMap("id", id);
		return jdbc.update(DELETE_BY_ID, params);
	}
	
	public int update(Book book) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(book);
		return jdbc.update(UPDATE, params);
	}
}
