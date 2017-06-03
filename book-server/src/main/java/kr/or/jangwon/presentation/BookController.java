package kr.or.jangwon.presentation;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import kr.or.jangwon.domain.Book;
import kr.or.jangwon.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Spring MVC: Spring framework의 모듈로 HTTP 요청을 받고 응답을 보내는 웹 레이어를 담당합니다.
// Spring Boot 환경에서 Spring MVC의 어노테이션을 이용해서 REST API를 합니다.
// Spring 어플리케이션에서는 @Controller가 붙은 클래스 -> @Service가 붙은 클래스 -> @Repository가 붙은 클래스로 요청의 흐름이 이어집니다.
@RestController
@RequestMapping("/api/books")
public class BookController {
	private final BookService service;
	private final Logger log = LoggerFactory.getLogger(BookController.class);
	
	@Autowired
	public BookController(BookService service) {
		this.service = service;
	}
	
	@GetMapping
	Collection<Book> readList() {
		return service.findAll();
	}

	@GetMapping("/{id}")
	Book read(@PathVariable  Integer id) {
		return service.findById(id);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	Book create(@RequestBody Book book) {
		Book newBook = service.create(book);
		// Spring Boot에서는 Sl4j 와 Logback이 default 로깅 인터페이스 / 구현 프레임워크로 제공된다.
		// spring-framework에서는 내부적으로 Apache Commons Logging을 사용하고 있다.
		// 때문에 spring-framework을 사용하는 어플리케이션에서 SLF4J를 사용하고자한다면 로깅 프레임워크의 의존 관계를 조정해야 한다.
		log.info("book created : {}" , newBook);
		return service.create(newBook);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void update(@PathVariable Integer id, @RequestBody Book book) {
		book.setId(id);
		service.update(book);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void delete(@PathVariable Integer id) {
		service.delete(id);
	}

}
