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
@Table(name = "education")
public class Education {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "school")
	private String school;

	@Column(name = "field")
	private FieldOfStudy field;

	@Column(name = "start_date")
	private Long startDate;

	@Column(name = "end_date")
	private Long endDate;

	@Column(name = "grade")
	private Float grade;

	@ManyToMany(mappedBy = "education", cascade = CascadeType.ALL)
	private List<Account> accounts;

	public Education(String school, FieldOfStudy field, Long startDate, Long endDate, Float grade) {
		this.school = school;
		this.field = field;
		this.startDate = startDate;
		this.endDate = endDate;
		this.grade = grade;
		this.accounts = new ArrayList<>();
	}

}
