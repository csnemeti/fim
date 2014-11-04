package pfa.alliance.fim.model.project;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import pfa.alliance.fim.model.GenericModel;
import pfa.alliance.fim.model.Identifiable;
import pfa.alliance.fim.model.project.users.UserProjectData;

@Entity
@Table(name = "project")
public class Project extends GenericModel implements Identifiable<Integer> {
  private static final long serialVersionUID = 177356267632L;
  @Column(name="id")
  private int id;
  @Column(name = "name")
  private String name;
  @Column(name = "description")
  private String description;
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.board", orphanRemoval = true, cascade = {
      CascadeType.PERSIST, CascadeType.MERGE})
  private List<UserProjectData> userBoardData;

  public Project() {
    super();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
  
  @Override
  public Integer getId() {
   return id;
  }

  @Override
  public void setId(Integer id) {
    this.id = id;
  }

  @Override
  public int hashCode() {
    HashCodeBuilder hcb = new HashCodeBuilder();

    hcb.append(name);
    hcb.append(description);

    return hcb.toHashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (!(obj instanceof Project)) {
      return false;
    }

    Project that = (Project) obj;
    EqualsBuilder eb = new EqualsBuilder();

    eb.append(name, that.name);
    eb.append(description, that.description);

    return eb.isEquals();
  }

  @Override
  public String toString() {
    ToStringBuilder tsb = new ToStringBuilder(this);

    tsb.append("id", id);
    tsb.append("name", name);
    tsb.append("description", description);

    return tsb.toString();
  }

}
