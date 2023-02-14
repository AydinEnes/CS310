package hwPack.model;


public class AccountPayload {
	
	private String id;

	private String owner;
	
	public AccountPayload() {
		
		
	}

	public AccountPayload(String id, String owner) {
		super();
		this.id = id;
		this.owner = owner;
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
	

}
