package tes.mosaik.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tes.mosaik.modal.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    public Subscription findByUserId(Long userID);
}
