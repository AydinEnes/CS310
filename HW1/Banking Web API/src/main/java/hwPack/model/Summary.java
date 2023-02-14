package hwPack.model;

import java.util.List;

public class Summary {
	
	private String id;
	private String owner;
	private String createDate;
	private List<TransactionDisplay> transactionsOut;
	private List<TransactionDisplay> transactionsIn;
	private Double balance;
	
	
	public Summary() {
		
	}

	public Summary(String id, String owner, String createDate, List<TransactionDisplay> transactionsOut,
			List<TransactionDisplay> transactionsIn, Double balance){
		this.id = id;
		this.owner = owner;
		this.createDate = createDate;
		this.transactionsOut = transactionsOut;
		this.transactionsIn = transactionsIn;
		this.balance = balance;
	}

	

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getOwner() {
		return owner;
	}


	public void setOwner(String owner) {
		this.owner = owner;
	}


	public String getCreateDate() {
		return createDate;
	}


	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}


	public List<TransactionDisplay> getTransactionsOut() {
		return transactionsOut;
	}


	public void setTransactionsOut(List<TransactionDisplay> transactionsOut) {
		this.transactionsOut = transactionsOut;
	}


	public List<TransactionDisplay> getTransactionsIn() {
		return transactionsIn;
	}


	public void setTransactionsIn(List<TransactionDisplay> transactionsIn) {
		this.transactionsIn = transactionsIn;
	}


	public Double getBalance() {
		return balance;
	}


	public void setBalance(Double balance) {
		this.balance = balance;
	}
	
	
}
