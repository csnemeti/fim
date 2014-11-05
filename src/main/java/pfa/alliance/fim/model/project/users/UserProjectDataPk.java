package pfa.alliance.fim.model.project.users;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import pfa.alliance.fim.model.project.Project;
import pfa.alliance.fim.model.user.User;

/**
 * The composed key that is formed by a {@link User} and a Project. The role of the entity is
 * pure for Hibernate necessity
 * 
 * @author Dennis
 * 
 */
@Embeddable
public class UserProjectDataPk implements Serializable {
  private static final long serialVersionUID = -8736637271L;
  @ManyToOne
  private User user;
  @ManyToOne
  private Project project;

  public UserProjectDataPk() {}

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  @Override
  public int hashCode() {
    HashCodeBuilder hcb = new HashCodeBuilder();

    hcb.append(user);
    hcb.append(project);

    return hcb.toHashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (!(obj instanceof UserProjectDataPk)) {
      return false;
    }

    UserProjectDataPk that = (UserProjectDataPk) obj;
    EqualsBuilder eb = new EqualsBuilder();

    eb.append(user, that.user);
    eb.append(project, that.project);

    return eb.isEquals();
  }

  @Override
  public String toString() {
    ToStringBuilder tsb = new ToStringBuilder(this);

    tsb.append("user", user);
    tsb.append("board", project);

    return tsb.toString();
  }
}
