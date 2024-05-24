package tes.mosaik.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tes.mosaik.modal.Invitation;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {

    Invitation findByToken(String token);

    Invitation findByEmail(String userEmail);
}
