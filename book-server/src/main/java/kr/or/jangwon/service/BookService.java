package kr.or.jangwon.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

import kr.or.jangwon.domain.Book;
import kr.or.jangwon.persistance.BookDao;

@Service
public class BookService {
	private ConcurrentMap<Integer, Book> repo = new ConcurrentHashMap<>();
	private AtomicInteger maxId = new AtomicInteger(0);
	
	private BookDao dao;
	
	public BookService(BookDao dao) {
		this.dao = dao;
	}
	
	public Book findById(Integer id) {
		return dao.selectById(id);
	}

	public Collection<Book> findAll() {
		return dao.selectAll();
	}
	
	public Book create(Book book) {
		Integer id = dao.insert(book);
		book.setId(id);
		return book;
	}
	
	public boolean update(Book book) {
		int affected = dao.update(book);
		return affected == 1;
	}
	public boolean delete(Integer id) {
		int affected = dao.deleteById(id);
		return affected == 1;
	}
}
