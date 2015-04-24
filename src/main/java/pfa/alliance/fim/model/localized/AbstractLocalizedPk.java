package pfa.alliance.fim.model.localized;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public abstract class AbstractLocalizedPk<ID extends Serializable> implements Serializable {
	private static final long serialVersionUID = -8658183413384357644L;
	
	@Column(name = "idlanguage")
	private Integer languageId;
	
	@Transient
	public abstract ID getEntityId();
	
	@Transient
	public abstract void setEntityId(ID id);


	public Integer getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
	}
}
