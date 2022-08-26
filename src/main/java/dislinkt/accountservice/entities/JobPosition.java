package dislinkt.accountservice.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "job_positions")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class JobPosition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", unique = false, nullable = false)
    @NonNull
    private String title;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Skill> skills = new ArrayList<>();

}
