package dislinkt.accountservice.entities;

import java.util.List;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "working_experience")
public class WorkingExperience {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "seniority")
	private SeniorityLevel seniority;

	@Column(name = "start_date")
	private Long startDate;

	@Column(name = "end_date")
	private Long endDate;

	@ManyToOne
	private JobPosition position;

	@ManyToMany
	private List<Skill> skills;

	@ManyToOne
	private Account account;

	public WorkingExperience(JobPosition jobPosition, SeniorityLevel seniority, Long startDate, Long endDate, Account account) {
		this.seniority = seniority;
		this.startDate = startDate;
		this.endDate = endDate;
		this.position = jobPosition;
		this.account = account;
	}

}
