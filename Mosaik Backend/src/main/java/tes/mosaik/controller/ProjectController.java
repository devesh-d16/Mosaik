package tes.mosaik.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tes.mosaik.modal.Chat;
import tes.mosaik.modal.Project;
import tes.mosaik.modal.User;
import tes.mosaik.response.MessageResponse;
import tes.mosaik.service.ProjectService;
import tes.mosaik.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final UserService userService;

    public ProjectController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String tag,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        List<Project> projects = projectService.getProjectByTeam(user, category, tag);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/{projectID}")
    public ResponseEntity<Project> getProjectByID(
            @PathVariable Long projectID,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Project project = projectService.getProjectByID(projectID);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Project> createProject(
            @RequestHeader("Authorization") String jwt,
            @RequestBody Project project
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Project createdProject = projectService.createProject(project, user);
        return new ResponseEntity<>(createdProject, HttpStatus.OK);
    }

    @PatchMapping("/{projectID}")
    public ResponseEntity<Project> updateProject(
            @PathVariable Long projectID,
            @RequestHeader("Authorization") String jwt,
            @RequestBody Project project
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Project updatedProject = projectService.updateProject(project, projectID);
        return new ResponseEntity<>(updatedProject, HttpStatus.OK);
    }

    @DeleteMapping("/{projectID}")
    public ResponseEntity<MessageResponse> deleteProject(
            @PathVariable Long projectID,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        projectService.deleteProject(projectID, user.getId());
        MessageResponse response = new MessageResponse("project deleted successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Project>> searchProjects(
            @RequestParam(required = false) String keyword,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        List<Project> projects = projectService.searchProjects(keyword, user);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }


    @GetMapping("/{projectID}/chat")
    public ResponseEntity<Chat> getChatByProjectID(
            @PathVariable Long projectID,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Chat chat = projectService.getChatByProjectID(projectID);
        return new ResponseEntity<>(chat, HttpStatus.OK);
    }
}
