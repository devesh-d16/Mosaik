package tes.mosaik.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tes.mosaik.modal.Comment;
import tes.mosaik.modal.User;
import tes.mosaik.request.CreateCommentRequest;
import tes.mosaik.response.MessageResponse;
import tes.mosaik.service.CommentService;
import tes.mosaik.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;

    public CommentController(CommentService commentService, UserService userService) {
        this.commentService = commentService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Comment> createComment(
            @RequestBody CreateCommentRequest request,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Comment createdComment = commentService.createComment(
                request.getIssueID(),
                user.getId(),
                request.getContent()
        );
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @DeleteMapping("/{commentID}")
    public ResponseEntity<MessageResponse> deleteComment(
            @PathVariable Long commentID,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        commentService.deleteComment(commentID, user.getId());
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Comment deleted");
        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }

    @GetMapping("/{issueID}")
    public ResponseEntity<List<Comment>> getCommentsByIssueID(
            @PathVariable Long issueID
    )throws Exception {
        List<Comment> comments = commentService.findCommentByIssueID(issueID);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
}
