package tes.mosaik.modal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDateTime subscriptionStartDate;
    private LocalDateTime subscriptionEndDate;

    private PlanType planType;
    private boolean isValid;

    @OneToOne
    private User user;
}
