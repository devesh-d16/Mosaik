package tes.mosaik.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tes.mosaik.DTO.IssueDTO;
import tes.mosaik.modal.Issue;
import tes.mosaik.modal.User;
import tes.mosaik.request.IssueRequest;
import tes.mosaik.response.MessageResponse;
import tes.mosaik.service.IssueService;
import tes.mosaik.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/issues")
public class IssueController {
    private final IssueService issueService;
    private final UserService userService;

    public IssueController(IssueService issueService, UserService userService) {
        this.issueService = issueService;
        this.userService = userService;
    }

    @GetMapping("/{issueID}")
    public ResponseEntity<Issue> getIssueById(
            @PathVariable Long issueID
    ) throws Exception {
        return ResponseEntity.ok(issueService.getIssueByID(issueID));
    }

    @GetMapping("/project/{projectID}")
    public ResponseEntity<List<Issue>> getIssueByProjectID(
            @PathVariable Long projectID
    ) throws Exception{
        return ResponseEntity.ok(issueService.getIssueByProjectID(projectID));
    }

    @PostMapping
    public ResponseEntity<IssueDTO> createIssue(
            @RequestBody IssueRequest issue,
            @RequestHeader("Authorization") String token
    ) throws Exception {
        User tokenUser = userService.findUserProfileByJwt(token);
        User user = userService.findUserProfileByID(tokenUser.getId());

        Issue createdIssue = issueService.createIssue(issue, tokenUser);
        IssueDTO issueDTO = new IssueDTO();
        issueDTO.setDescription(createdIssue.getDescription());
        issueDTO.setDueDate(createdIssue.getDueDate());
        issueDTO.setId(createdIssue.getId());
        issueDTO.setProject(createdIssue.getProject());
        issueDTO.setProjectID(createdIssue.getProjectID());
        issueDTO.setPriority(createdIssue.getPriority());
        issueDTO.setStatus(createdIssue.getStatus());
        issueDTO.setTitle(createdIssue.getTitle());
        issueDTO.setTags(createdIssue.getTags());
        issueDTO.setAssignee(createdIssue.getAssignee());

        return ResponseEntity.ok(issueDTO);

    }

    @DeleteMapping("/{issueID}")
    public ResponseEntity<MessageResponse> deleteIssue(
            @PathVariable Long issueID,
            @RequestHeader("Authorization") String token
    ) throws Exception {
        User user = userService.findUserProfileByJwt(token);
        issueService.deleteIssue(issueID, user.getId());

        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Issue deleted");

        return ResponseEntity.ok(messageResponse);
    }

    @PutMapping("/{issueID}/assignee/{userID}")
    public ResponseEntity<Issue> assignIssueToUser(
            @PathVariable Long issueID,
            @PathVariable Long userID
    ) throws Exception {
        Issue issue = issueService.assignIssueToUser(issueID, userID);
        return ResponseEntity.ok(issue);
    }

    @PutMapping("/{issueID}/status/{status}")
    public ResponseEntity<Issue> updateIssueStatus(
            @PathVariable String status,
            @PathVariable Long issueID
    ) throws Exception {
        Issue issue = issueService.updateStatus(issueID, status);
        return ResponseEntity.ok(issue);
    }

}
