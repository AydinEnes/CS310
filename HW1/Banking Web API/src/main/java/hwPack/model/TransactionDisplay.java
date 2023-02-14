package hwPack.model;

public class TransactionDisplay {
	
	private String id;

	
	private Account from;
	private Account to;
	
	private String createDate;
	private Double amount;
	

	public TransactionDisplay() {
		
	}
	
	public TransactionDisplay(String id, Account fromAccount, Account toAccount, String createDate, Double amount) {
		super();
		this.id = id;
		this.from = fromAccount;
		this.to = toAccount;
		this.createDate = createDate;
		this.amount = amount;
	}
	

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Account getFrom() {
		return from;
	}
	public void setFrom(Account from) {
		this.from = from;
	}
	public Account getTo() {
		return to;
	}
	public void setTo(Account to) {
		this.to = to;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
}
