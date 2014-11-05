package pfa.alliance.fim.model.user;


/**
 * 
 * @author Dennis
 * 
 */
public enum UserScrumRole {
	MANAGER("Manager"), USER("User"), CUSTOMER("Customer"), SCRUM_MASTER(
			"Scrum master"), PRODUCT_OWNER("Product owner");

	UserScrumRole(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	private String value;
}
