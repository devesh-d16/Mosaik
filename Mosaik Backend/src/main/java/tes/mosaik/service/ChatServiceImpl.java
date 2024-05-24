package tes.mosaik.service;

import org.springframework.stereotype.Service;
import tes.mosaik.modal.Chat;
import tes.mosaik.repository.ChatRepository;

@Service
public class ChatServiceImpl implements ChatService{

    private final ChatRepository chatRepository;

    public ChatServiceImpl(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Override
    public Chat createChat(Chat chat) {
        return chatRepository.save(chat);
    }
}
