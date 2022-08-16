package dislinkt.accountservice.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "resumes")
public class Resume {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_id", unique = true)
	private Long userId;

	@Column(name = "biography")
	private String biography;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "public_account")
	private Boolean publicAccount;

	@Column(name = "mute_connection_notifications")
	private Boolean muteConnectionNotifications;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "resumes_connections", joinColumns = { @JoinColumn(name = "resume_id") }, inverseJoinColumns = {
			@JoinColumn(name = "connection_id") })
	private List<Connection> connections;

	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "resumes_interests", joinColumns = { @JoinColumn(name = "resume_id") }, inverseJoinColumns = {
			@JoinColumn(name = "interest_id") })
	private List<Interest> interests;

	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "resumes_skills", joinColumns = { @JoinColumn(name = "resume_id") }, inverseJoinColumns = {
			@JoinColumn(name = "skill_id") })
	private List<Skill> skills;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "resumes_educations", joinColumns = { @JoinColumn(name = "resume_id") }, inverseJoinColumns = {
			@JoinColumn(name = "education_id") })
	private List<Education> educations;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "resumes_working_experiences", joinColumns = {
			@JoinColumn(name = "resume_id") }, inverseJoinColumns = { @JoinColumn(name = "working_experience_id") })
	private List<WorkingExperience> workingExperiences;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "resumes_blocked_accounts", joinColumns = {
			@JoinColumn(name = "resume_id") }, inverseJoinColumns = { @JoinColumn(name = "blocked_account_id") })
	private List<Resume> blockedAccounts;

	@ManyToMany(mappedBy = "blockedAccounts", cascade = CascadeType.ALL)
	private List<Resume> resumes;

	public Resume(Long userId) {
		this.userId = userId;
		this.publicAccount = true;
		this.muteConnectionNotifications = false;
		this.connections = new ArrayList<Connection>();
		this.interests = new ArrayList<Interest>();
		this.skills = new ArrayList<Skill>();
		this.educations = new ArrayList<Education>();
		this.workingExperiences = new ArrayList<WorkingExperience>();
		this.blockedAccounts = new ArrayList<Resume>();
	}

}
