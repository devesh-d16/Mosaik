package tes.mosaik.service;

import org.springframework.stereotype.Service;
import tes.mosaik.modal.Chat;
import tes.mosaik.modal.Project;
import tes.mosaik.modal.User;

import java.util.List;

@Service
public interface ProjectService {

    Project createProject(Project project, User user) throws Exception;

    List<Project> getProjectByTeam(User user, String category, String tag) throws Exception;

    List<Project> getProjectById(Long projectID) throws Exception;

    void deleteProject(Long projectID, Long userID) throws Exception;

    Project updateProject(Project updatedProject, Long id) throws Exception;

    void addUserToProject(Long projectID, Long userID) throws Exception;

    void removeUserFromProject(Long projectID, Long userID) throws Exception;

    Chat getChatByProjectID(Long projectID) throws Exception;
}
