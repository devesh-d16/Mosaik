package tes.mosaik.service;

import jakarta.mail.MessagingException;
import tes.mosaik.modal.Invitation;

public interface InvitationService {

    public void sendInvitation(String email, Long projectID) throws MessagingException;

    public Invitation acceptInvitation(String token, Long userID) throws Exception;

    public String getTokenByUserMail(String userEmail);

    public void deleteToken(String token);
}
