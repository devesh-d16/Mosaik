package tes.mosaik.service;

import org.springframework.stereotype.Service;
import tes.mosaik.modal.Comment;
import tes.mosaik.modal.Issue;
import tes.mosaik.modal.User;
import tes.mosaik.repository.CommentRepository;
import tes.mosaik.repository.IssueRepository;
import tes.mosaik.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final IssueRepository issueRepository;

    public CommentServiceImpl(CommentRepository commentRepository, UserRepository userRepository, IssueRepository issueRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.issueRepository = issueRepository;
    }

    @Override
    public Comment createComment(Long issueID, Long userID, String content) throws Exception {
        Optional<Issue> issueOptional = issueRepository.findById(issueID);
        Optional<User> userOptional = userRepository.findById(userID);

        if(issueOptional.isEmpty()){
            throw new Exception("Error: Issue with ID " + issueID + " not found");
        }
        if(userOptional.isEmpty()){
            throw new Exception("Error: User with ID " + userID + " not found");
        }

        Issue issue = issueOptional.get();
        User user = userOptional.get();

        Comment comment = new Comment();
        comment.setIssue(issue);
        comment.setUser(user);
        comment.setCreationDateTime(LocalDateTime.now());
        comment.setContent(content);

        Comment savedComment = commentRepository.save(comment);
        issue.getComments().add(savedComment);

        return savedComment;
    }

    @Override
    public void deleteComment(Long commentID, Long userID) throws Exception {
        Optional<Comment> commentOptional = commentRepository.findById(commentID);
        Optional<User> userOptional = userRepository.findById(userID);

        if(commentOptional.isEmpty()){
            throw new Exception("Error: Comment with ID " + commentID + " not found");
        }
        if(userOptional.isEmpty()){
            throw new Exception("Error: User with ID " + userID + " not found");
        }

        Comment comment = commentOptional.get();
        User user = userOptional.get();

        if(comment.getUser().equals(user)){
            commentRepository.delete(comment);
        }
        else{
            throw new Exception("Error: Comment with ID " + commentID + " not owned by user");
        }
    }

    @Override
    public List<Comment> findCommentByIssueID(Long issueID) throws Exception {
        return commentRepository.findByIssueId(issueID);
    }
}
