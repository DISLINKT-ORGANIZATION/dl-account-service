package dislinkt.accountservice.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "accounts")
public class Account {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_id", unique = true)
	private Long userId;

	@Column(name = "biography", columnDefinition="TEXT")
	private String biography;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "public_account")
	private Boolean publicAccount;

	@Column(name = "mute_message_notifications")
	private Boolean muteMessageNotifications;

	@Column(name = "mute_post_notifications")
	private Boolean mutePostNotifications;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "accounts_connections", joinColumns = { @JoinColumn(name = "account_id") }, inverseJoinColumns = {
			@JoinColumn(name = "connection_id") })
	private List<Connection> connections;

	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "accounts_skill_proficiencies", joinColumns = { @JoinColumn(name = "account_id") }, inverseJoinColumns = {
			@JoinColumn(name = "skill_id") })
	private List<SkillProficiency> skillProficiencies;

	@OneToMany
	private List<Education> education;

	@OneToMany
	private List<WorkingExperience> workingExperience;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "accounts_blocked_accounts", joinColumns = {
			@JoinColumn(name = "account_id") }, inverseJoinColumns = { @JoinColumn(name = "blocked_account_id") })
	private List<Account> blockedAccounts;

//	@ManyToMany(mappedBy = "blockedAccounts", cascade = CascadeType.ALL)
//	private List<Account> accounts;

	public Account(Long userId) {
		this.userId = userId;
		this.publicAccount = true;
		this.muteMessageNotifications = false;
		this.mutePostNotifications = false;
		this.connections = new ArrayList<Connection>();
		this.skillProficiencies = new ArrayList<SkillProficiency>();
		this.education = new ArrayList<Education>();
		this.workingExperience = new ArrayList<WorkingExperience>();
		this.blockedAccounts = new ArrayList<Account>();
	}

}
