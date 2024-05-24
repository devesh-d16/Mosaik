package tes.mosaik.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tes.mosaik.modal.Project;
import tes.mosaik.modal.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueDTO {
    private Long id;
    private String title;
    private String description;
    private String status;
    private Long projectID;
    private String priority;
    private LocalDateTime dueDate;
    private List<String> tags = new ArrayList<>();
    private Project project;

//    Exclude assignee during Serialization
    private User assignee;
}
