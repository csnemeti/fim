package pfa.alliance.fim.model.localized;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import pfa.alliance.fim.model.GenericModel;

@MappedSuperclass
public abstract class AbstractLocalizedModel extends GenericModel {
	private static final long serialVersionUID = 4465242012629572128L;
	
	@Column(name = "idlanguage")
	protected Integer languageId;
	
	@Transient
    public abstract String getName();
    
    @Transient
    public abstract void setName(String name);       

    /**
     * @return the language
     */    
    public Integer getLanguageId() {
        return languageId;
    }   
    
    
}
