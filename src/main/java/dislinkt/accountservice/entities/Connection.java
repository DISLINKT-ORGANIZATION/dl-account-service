package dislinkt.accountservice.entities;

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

	@Column(name = "followed_user_id")
	private Long followedUserId;

	@Column(name = "mute_messages")
	private Boolean muteMessages;

	@Column(name = "mute_posts")
	private Boolean mutePosts;

	@ManyToMany(mappedBy = "connections", cascade = CascadeType.ALL)
	private List<Resume> resumes;

}
