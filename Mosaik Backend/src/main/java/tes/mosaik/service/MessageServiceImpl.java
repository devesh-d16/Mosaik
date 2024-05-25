package tes.mosaik.service;

import org.springframework.stereotype.Service;
import tes.mosaik.modal.Chat;
import tes.mosaik.modal.Message;
import tes.mosaik.modal.User;
import tes.mosaik.repository.MessageRepository;
import tes.mosaik.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    public final MessageRepository messageRepository;
    public final UserRepository userRepository;
    public final ProjectService projectService;

    public MessageServiceImpl(MessageRepository messageRepository, UserRepository userRepository, ProjectService projectService) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.projectService = projectService;
    }

    @Override
    public Message sendMessage(Long senderID, Long projectID, String content) throws Exception {
        User sender = userRepository.findById(senderID)
                .orElseThrow(() -> new Exception("User not found with ID: " + senderID));

        Chat chat = projectService.getProjectByID(projectID).getChat();

        Message message = new Message();
        message.setContent(content);
        message.setSender(sender);
        message.setCreationDateTime(LocalDateTime.now());
        message.setChat(chat);

        Message savedMessage = messageRepository.save(message);
        chat.getMessages().add(savedMessage);
        return savedMessage;
    }

    @Override
    public List<Message> getMessagesByProjectID(Long projectID) throws Exception {
        Chat chat = projectService.getChatByProjectID(projectID);
        return messageRepository.findByChatIdOrderByCreatedAtAsc(projectID);
    }
}
