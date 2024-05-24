package tes.mosaik.service;

import tes.mosaik.modal.Comment;

import java.util.List;

public interface CommentService {

    Comment createComment(Long issueID, Long userID, String content) throws Exception;

    void deleteComment(Long commentID, Long userID) throws Exception;

    List<Comment> findCommentByIssueID(Long issueID) throws Exception;
}
