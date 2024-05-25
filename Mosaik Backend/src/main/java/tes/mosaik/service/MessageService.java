package tes.mosaik.service;

import tes.mosaik.modal.Message;

import java.util.List;

public interface MessageService {
    Message sendMessage(Long senderID, Long projectID, String content) throws Exception;

    List<Message> getMessagesByProjectID(Long projectID) throws Exception;
}
