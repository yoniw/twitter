package twitter.data;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private int id;
	
	@Column(name="user_name")
	private String name;

	@JsonIgnore
	@OneToMany(mappedBy="author")
	private Set<Message> createdMessages;
	
	@JsonIgnore
	@ManyToMany
	@JoinTable(
			name="users_to_followed_users",
			joinColumns = {@JoinColumn(name="user_id")},
			inverseJoinColumns = {@JoinColumn(name="followed_user_id")}
	)
	private Set<User> followedUsers;
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Set<User> getFollowedUsers() {
		return followedUsers;
	}
	
	public Set<Message> getCreatedMessages() {
		return createdMessages;
	}
}