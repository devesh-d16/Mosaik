package tes.mosaik.service;


import org.springframework.stereotype.Service;
import tes.mosaik.modal.Chat;
import tes.mosaik.modal.Project;
import tes.mosaik.modal.User;
import tes.mosaik.repository.ProjectRepository;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService{

    private final ProjectRepository projectRepository;
    private final UserService userService;

    public ProjectServiceImpl(ProjectRepository projectRepository, UserService userService) {
        this.projectRepository = projectRepository;
        this.userService = userService;
    }

    @Override
    public Project createProject(Project project, User user) throws Exception {
        Project createdProject = new Project();
        createdProject.setOwner(user);
        createdProject.setTags(project.getTags());
        createdProject.setName(project.getName());
        createdProject.setCategory(project.getCategory());
        return null;
    }

    @Override
    public List<Project> getProjectByTeam(User user, String category, String tag) throws Exception {
        return List.of();
    }

    @Override
    public List<Project> getProjectById(Long projectID) throws Exception {
        return List.of();
    }

    @Override
    public void deleteProject(Long projectID, Long userID) throws Exception {

    }

    @Override
    public Project updateProject(Project updatedProject, Long id) throws Exception {
        return null;
    }

    @Override
    public void addUserToProject(Long projectID, Long userID) throws Exception {

    }

    @Override
    public void removeUserFromProject(Long projectID, Long userID) throws Exception {

    }

    @Override
    public Chat getChatByProjectID(Long projectID) throws Exception {
        return null;
    }
}
