package kr.or.jangwon.domain;

public class Book {
	private Integer id;
	private String author;
	private String title;
	private Integer pages;
	
	public Book() {
	}

	public Book(String title, String author, Integer pages) {
		this.title = title;
		this.author = author;
		this.pages = pages;
	}
	
	public Book(Integer id, String title, String author, Integer pages) {
		this(title, author, pages);
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getPages() {
		return pages;
	}
	public void setPages(Integer pages) {
		this.pages = pages;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Book [id=" + id + ", title=" + title + ", author=" + author + ", pages=" + pages + "]";
	}
}
