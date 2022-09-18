package dislinkt.accountservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowNotification implements Serializable {

    private long senderId;
    private long recipientId;
    private long timestamp;
}
