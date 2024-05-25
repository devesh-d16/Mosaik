package tes.mosaik.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tes.mosaik.modal.Message;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    public List<Message> findByChatIdOrderByCreatedAtAsc(Long chatId);
}
