package tes.mosaik.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tes.mosaik.modal.Chat;
import tes.mosaik.modal.Message;
import tes.mosaik.modal.User;
import tes.mosaik.request.CreateMessageRequest;
import tes.mosaik.service.MessageService;
import tes.mosaik.service.ProjectService;
import tes.mosaik.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;
    private final UserService userService;
    private final ProjectService projectService;

    public MessageController(MessageService messageService, UserService userService, ProjectService projectService) {
        this.messageService = messageService;
        this.userService = userService;
        this.projectService = projectService;
    }

    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(
            @RequestBody CreateMessageRequest request,
            @RequestHeader("Authorization") String jwt
    )throws Exception {

//        User user = userService.findUserProfileByJwt(jwt);
        User user = userService.findUserProfileByID(request.getSenderID());
        if(user == null) {
            throw new Exception("User not found with ID: " + request.getSenderID());
        }

        Chat chat = projectService.getProjectByID(request.getProjectID()).getChat();
        if(chat == null) {
            throw new Exception("Chat not found with ID: " + request.getProjectID());
        }

        Message sentMessage = messageService.sendMessage(
                request.getSenderID(),
                request.getProjectID(),
                request.getContent()
        );

        return ResponseEntity.ok(sentMessage);
    }

    @GetMapping("/chat/{projectID}")
    public ResponseEntity<List<Message>> getMessagesByChatID(
            @PathVariable Long projectID
    ) throws Exception{
        List<Message> messages = messageService.getMessagesByProjectID(projectID);
        return ResponseEntity.ok(messages);
    }

}
