package tes.mosaik.service;

import org.springframework.stereotype.Service;
import tes.mosaik.modal.Issue;
import tes.mosaik.modal.Project;
import tes.mosaik.modal.User;
import tes.mosaik.repository.IssueRepository;
import tes.mosaik.request.IssueRequest;

import java.util.List;
import java.util.Optional;

@Service
public class IssueServiceImpl implements IssueService{

    private final IssueRepository issueRepository;
    private final ProjectService projectService;
    private final UserService userService;

    public IssueServiceImpl(IssueRepository issueRepository, ProjectService projectService, UserService userService) {
        this.issueRepository = issueRepository;
        this.projectService = projectService;
        this.userService = userService;
    }

    @Override
    public Issue getIssueByID(Long issueID) throws Exception {
        Optional<Issue> issue = issueRepository.findById(issueID);
        if(issue.isPresent()){
            return issue.get();
        }
        throw new Exception("No such issue found with ID " + issueID);
    }

    @Override
    public List<Issue> getIssueByProjectID(Long projectID) throws Exception {
        return issueRepository.findByProjectID(projectID);
    }

    @Override
    public Issue createIssue(IssueRequest issueRequest, User user) throws Exception {
        Project project = projectService.getProjectByID(issueRequest.getProjectID());

        Issue issue = new Issue();
        issue.setTitle(issueRequest.getTitle());
        issue.setDescription(issueRequest.getDescription());
        issue.setStatus(issueRequest.getStatus());
        issue.setProjectID(issueRequest.getProjectID());
        issue.setPriority(issueRequest.getPriority());
        issue.setDueDate(issueRequest.getDueDate());

        issue.setProject(project);
        return issueRepository.save(issue);
    }

    @Override
    public void deleteIssue(Long issueID, Long userID) throws Exception {
        getIssueByID(issueID);
        issueRepository.deleteById(issueID);
    }

    @Override
    public Issue assignIssueToUser(Long issueID, Long userID) throws Exception {
        User user = userService.findUserProfileByID(userID);
        Issue issue = getIssueByID(issueID);

        issue.setAssignee(user);
        return issueRepository.save(issue);
    }

    @Override
    public Issue updateStatus(Long issueID, String status) throws Exception {
        Issue issue = getIssueByID(issueID);
        issue.setStatus(status);
        return issueRepository.save(issue);
    }


}
