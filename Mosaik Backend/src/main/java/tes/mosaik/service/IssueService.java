package tes.mosaik.service;

import tes.mosaik.modal.Issue;
import tes.mosaik.modal.User;
import tes.mosaik.request.IssueRequest;

import java.util.List;
import java.util.Optional;

public interface IssueService {

    Issue getIssueByID(Long issueID) throws Exception;

    List<Issue> getIssueByProjectID(Long projectID) throws Exception;

    Issue createIssue(IssueRequest issue, User user) throws Exception;

    void deleteIssue(Long issueID, Long userID) throws Exception;

    Issue addUserToIssue(Long issueID, Long userID) throws Exception;

    Issue updateStatus(Long issueID, String status) throws Exception;

}
