package pfa.alliance.fim.model.project.users;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import pfa.alliance.fim.model.project.Project;
import pfa.alliance.fim.model.user.User;
import pfa.alliance.fim.model.user.UserScrumRole;

/**
 * The entity that contains the mapping between the Board and User . This entity
 * contains the key (UserBoardDataPk ) and the common data that is shared
 * beetwen this two entities.
 * 
 * @author Dennis
 * 
 */
@Entity
//@Audited
@Table(name = "user_project")
@AssociationOverrides({
		@AssociationOverride(name = "pk.project", joinColumns = @JoinColumn(name = "project_id")),
		@AssociationOverride(name = "pk.user", joinColumns = @JoinColumn(name = "user_id")) })
public class UserProjectData{
	@EmbeddedId
	private UserProjectDataPk pk;
	@Enumerated(EnumType.STRING)
	@Column(name = "scrumRole")
	private UserScrumRole scrumRole;

	public UserProjectData() {
		super();
		pk = new UserProjectDataPk();
	}

	public UserProjectDataPk getPk() {
		return pk;
	}

	public void setPk(UserProjectDataPk pk) {
		this.pk = pk;
	}

	@Transient
	public User getUser() {
		return pk.getUser();
	}

	public void setUser(User user) {
		pk.setUser(user);
	}

	@Transient
	public Project getProject() {
		return pk.getProject();
	}

	public void setBoard( Project project) {
		pk.setProject(project);
	}

	public UserScrumRole getScrumRole() {
		return scrumRole;
	}

	public void setScrumRole(UserScrumRole scrumRole) {
		this.scrumRole = scrumRole;
	}
	
	@Override
	public int hashCode() {
		HashCodeBuilder hcb = new HashCodeBuilder();

		hcb.append(pk);
		hcb.append(scrumRole);

		return hcb.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof UserProjectData)) {
			return false;
		}

		UserProjectData that = (UserProjectData) obj;
		EqualsBuilder eb = new EqualsBuilder();

		eb.append(pk , that.pk);
		eb.append(scrumRole, that.scrumRole);

		return eb.isEquals();
	}

	@Override
	public String toString() {
		ToStringBuilder tsb = new ToStringBuilder(this);

		tsb.append("pk", pk);
		tsb.append("scrumRole", scrumRole);

		return tsb.toString();
	}

}
