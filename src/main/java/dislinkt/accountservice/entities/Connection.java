package dislinkt.accountservice.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "connections")
public class Connection {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "followed_account_id")
	private Long followedAccountId;

	@Column(name = "mute_messages")
	private Boolean muteMessages;

	@Column(name = "mute_posts")
	private Boolean mutePosts;

	@ManyToMany(mappedBy = "connections", cascade = CascadeType.ALL)
	private List<Account> accounts;

	public Connection(Long followedResumeId, Boolean muteMessages, Boolean mutePosts) {
		super();
		this.followedAccountId = followedResumeId;
		this.muteMessages = muteMessages;
		this.mutePosts = mutePosts;
		this.accounts = new ArrayList<Account>();
	}
	
	

}
