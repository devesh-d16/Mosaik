package tes.mosaik.service;


import org.springframework.stereotype.Service;
import tes.mosaik.modal.Chat;
import tes.mosaik.modal.Project;
import tes.mosaik.modal.User;
import tes.mosaik.repository.ProjectRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService{

    private final ProjectRepository projectRepository;
    private final UserService userService;
    private final ChatService chatService;

    public ProjectServiceImpl(ProjectRepository projectRepository, UserService userService, ChatService chatService) {
        this.projectRepository = projectRepository;
        this.userService = userService;
        this.chatService = chatService;
    }

    @Override
    public Project createProject(Project project, User user) throws Exception {

        Project createdProject = new Project();
        createdProject.setOwner(user);

        createdProject.setName(project.getName());
        createdProject.setDescription(project.getDescription());
        createdProject.setCategory(project.getCategory());
        createdProject.setTags(project.getTags());

        createdProject.getTeam().add(user);

        Project savedProject = projectRepository.save(createdProject);
        Chat chat = new Chat();
        chat.setProject(savedProject);

        Chat projectChat = chatService.createChat(chat);
        savedProject.setChat(projectChat);

        return savedProject;
    }

    @Override
    public List<Project> getProjectByTeam(User user, String category, String tag) throws Exception {
        List<Project> projects = projectRepository.findByTeamContainingOrOwner(user, user);
        if(category != null){
            projects = projects.stream()
                    .filter(project -> project.getCategory().equals(category))
                    .toList();
        }
        if(tag != null){
            projects = projects.stream()
                    .filter(project -> project.getTags().contains(tag))
                    .toList();
        }
        return projects;
    }

    @Override
    public Project getProjectByID(Long projectID) throws Exception {

        Optional<Project> optionalProject = projectRepository.findById(projectID);
        if(optionalProject.isEmpty()){
            throw new Exception("Project not found");
        }

        return optionalProject.get();
    }

    @Override
    public void deleteProject(Long projectID, Long userID) throws Exception {
        Project project = getProjectByID(projectID);
        User user = userService.findUserProfileByID(userID);

        if(user != project.getOwner()){
            throw new Exception("User is not the owner");
        }else{
            projectRepository.deleteById(projectID);
        }
    }

    @Override
    public Project updateProject(Project updatedProject, Long id) throws Exception {
        Project project = getProjectByID(id);
        project.setName(updatedProject.getName());
        project.setDescription(updatedProject.getDescription());
        project.setTags(updatedProject.getTags());

        return projectRepository.save(project);
    }

    @Override
    public void addUserToProject(Long projectID, Long userID) throws Exception {
        Project project = getProjectByID(projectID);
        User user = userService.findUserProfileByID(userID);
        if(!project.getTeam().contains(user)){
            project.getChat().getUsers().add(user);
            project.getTeam().add(user);
        }
        projectRepository.save(project);
    }

    @Override
    public void removeUserFromProject(Long projectID, Long userID) throws Exception {
        Project project = getProjectByID(projectID);
        User user = userService.findUserProfileByID(userID);
        if(project.getTeam().contains(user)){
            project.getChat().getUsers().remove(user);
            project.getTeam().remove(user);
        }
        projectRepository.save(project);
    }

    @Override
    public Chat getChatByProjectID(Long projectID) throws Exception {
        Project project = getProjectByID(projectID);
        return project.getChat();
    }

    @Override
    public List<Project> searchProjects(String keyword, User user) throws Exception {
//        String partialKeyword = "%" + keyword + "%";
        return projectRepository.findByNameContainingAndTeamContains(keyword, user);
    }
}
