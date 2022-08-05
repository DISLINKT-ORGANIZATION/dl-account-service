package dislinkt.accountservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id", scope = Notification.class)
public class Notification implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long noificationId;

	private Long senderId;

	private Long recepientId;

	private String content;

	private NotificationType type;

	@Override
	public String toString() {
		return "Notification [noificationId=" + noificationId + ", senderId=" + senderId + ", recepientId="
				+ recepientId + ", content=" + content + ", type=" + type + "]";
	}

}