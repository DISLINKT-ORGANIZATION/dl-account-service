package dislinkt.accountservice.entities;

import javax.persistence.*;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

	@ManyToOne
	private Account account;

	public Education(String school, FieldOfStudy field, Long startDate, Long endDate, Account account) {
		this.school = school;
		this.field = field;
		this.startDate = startDate;
		this.endDate = endDate;
		this.account = account;
	}

}
