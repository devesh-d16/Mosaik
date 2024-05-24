package tes.mosaik.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tes.mosaik.modal.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {

}
