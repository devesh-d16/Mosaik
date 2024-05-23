package tes.mosaik.modal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    private String content;

    private LocalDateTime creationDateTime;

    @ManyToOne
    private User user;

    @ManyToOne
    private Issue issue;
}
