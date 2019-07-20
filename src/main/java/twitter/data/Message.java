package twitter.data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name="messages")
public class Message {

    @ApiModelProperty(value = "id", hidden = true)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private int id;
	
	@Column(name="message")
	private String text;
	
	@ManyToOne
	@JoinColumn(name="author_id")
	private User author;
	
    @ApiModelProperty(value = "creationTime", hidden = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name="creation_time", nullable = false, updatable = false)
	@CreationTimestamp
	private Date creationTime;
	
	public int getId() {
		return id;
	}
	
	public String getText() {
		return text;
	}
	
	public User getAuthor() {
		return author;
	}
	
	public Date getCreationTime() {
		return creationTime;
	}
	
	public void setAuthor(User author) {
		this.author = author;
	}
	
	public void setText(String text) {
		this.text = text;
	}
}
