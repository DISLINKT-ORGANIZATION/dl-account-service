package dislinkt.accountservice.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "skill_proficiencies")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class SkillProficiency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "proficiency", unique = false, nullable = false)
    @NonNull
    private Proficiency proficiency;

    @ManyToOne
    private Skill skill;

    public SkillProficiency(Proficiency proficiency, Skill skill) {
        this.proficiency = proficiency;
        this.skill = skill;
    }

}
