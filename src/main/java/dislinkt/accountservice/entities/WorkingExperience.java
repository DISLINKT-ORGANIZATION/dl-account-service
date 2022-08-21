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
@Table(name = "working_experiences")
public class WorkingExperience {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "job_position")
	private String jobPosition;

	@Column(name = "seniority")
	private SeniorityLevel seniority;

	@Column(name = "start_date")
	private Long startDate;

	@Column(name = "end_date")
	private Long endDate;

	@ManyToMany(mappedBy = "workingExperiences", cascade = CascadeType.ALL)
	private List<Account> accounts;

	public WorkingExperience(String jobPosition, SeniorityLevel seniority, Long startDate, Long endDate) {
		this.jobPosition = jobPosition;
		this.seniority = seniority;
		this.startDate = startDate;
		this.endDate = endDate;
		this.accounts = new ArrayList<Account>();
	}

}
