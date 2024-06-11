package tes.mosaik.service;

import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;
import tes.mosaik.modal.Invitation;
import tes.mosaik.repository.InvitationRepository;

import java.util.UUID;

@Service
public class InvitationServiceImpl implements InvitationService{

    private final InvitationRepository invitationRepository;
    private final EmailService emailService;

    public InvitationServiceImpl(InvitationRepository invitationRepository, EmailService emailService) {
        this.invitationRepository = invitationRepository;
        this.emailService = emailService;
    }

    @Override
    public void sendInvitation(String email, Long projectID) throws MessagingException {
        String invitationToken = UUID.randomUUID().toString();
        Invitation invitation = new Invitation();
        invitation.setToken(email);
        invitation.setProjectID(projectID);
        invitation.setToken(invitationToken);

        invitationRepository.save(invitation);
        String invitationLink = "http://localhost:5173/accept_invitation?token=" + invitationToken;
        emailService.sendEmailWithToken(email,invitationLink);
    }

    @Override
    public Invitation acceptInvitation(String token, Long userID) throws Exception {
        Invitation invitation = invitationRepository.findByToken(token);
        if(invitation == null) {
            throw new Exception("Invalid invitation token");
        }
        return invitation;
    }

    @Override
    public String getTokenByUserMail(String userEmail) {
        Invitation invitation = invitationRepository.findByEmail(userEmail);
        return invitation.getToken();
    }

    @Override
    public void deleteToken(String token) {
        Invitation invitation = invitationRepository.findByToken(token);
        invitationRepository.delete(invitation);
    }
}
