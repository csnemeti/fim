package pfa.alliance.fim.model.issue.states;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import pfa.alliance.fim.model.GenericModel;
import pfa.alliance.fim.model.Identifiable;

@Entity(name = "issue_state_flow")
public class IssueFlow extends GenericModel implements Identifiable<Integer> {
	private static final long serialVersionUID = 3986508871676895638L;

	@Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "id" )
	private int id;
	
    @Column( name = "name", length = 50, nullable = false )
	private String name;

    /** The flow state. */
    @Enumerated( EnumType.STRING )
    @Column( name = "flow_state", length = 20, nullable = false )
    private FlowState state = FlowState.NEW;

	@OneToMany( mappedBy = "flow" )
	private List<IssueStateRelation> relations;

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public List<IssueStateRelation> getRelations() {
		return relations;
	}

	public void setRelations(List<IssueStateRelation> relations) {
		this.relations = relations;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
