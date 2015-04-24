package pfa.alliance.fim.model.issue.states;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import pfa.alliance.fim.model.GenericModel;
import pfa.alliance.fim.model.Identifiable;

@Entity(name = "issue_state")
public class IssueState extends GenericModel implements Identifiable<Integer> {
	private static final long serialVersionUID = -6815173726653278343L;

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "id" )
	private Integer id;
	
	@Column(name = "code")
	private String code;
	
    @OneToMany(mappedBy = "issueState", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private Set<IssueStateLocalized> localizedNames;

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public Set<IssueStateLocalized> getLocalizedNames() {
		return localizedNames;
	}
	
	public Map<String, String> getLocalizedNamesMap() {
		Map<String, String> localizedNames = new HashMap<String, String>();
		for(IssueStateLocalized issueStateLocalized : this.localizedNames){
			localizedNames.put(issueStateLocalized.getLanguageId().toString(), issueStateLocalized.getName());
		}
		return localizedNames;
	}

	public void setLocalizedNames(Set<IssueStateLocalized> localizedNames) {
		this.localizedNames = localizedNames;
	}
	
	
}
