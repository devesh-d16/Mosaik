package tes.mosaik.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMessageRequest {
    private Long senderID;
    private Long projectID;
    private String content;
}
