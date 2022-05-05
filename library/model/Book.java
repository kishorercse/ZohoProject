package library.model;
import java.util.Date;

public class Book {
	private int id, price, billAmount;
	private String name, author, status, memberName;
	private Date issueDate, dueDate;
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public void setStatus(boolean available) {
		this.status = available?"Available":"Not Available";
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}
	
	public Date getIssueDate() {
		return issueDate;
	}
	
	public void setDueDate(Date dueDate) {
		this.dueDate=dueDate;
	}
	
	public Date getDueDate() {
		return dueDate;
	}
	
	public void setBillAmount(int billAmount) {
		this.billAmount = billAmount;
	}
	
	public int getBillAmount() {
		return billAmount;
	}
	
	public void setMemberName(String name) {
		this.memberName = name;
	}
	
	public String getMemberName() {
		return memberName;
	}
}
