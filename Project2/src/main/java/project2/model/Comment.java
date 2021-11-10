package project2.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="comments")
public class Comment {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="comment_id")
	private int id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="comment_author_id", unique=false, nullable=false)
	private User author;
	
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="comment_post_id", nullable=false)
	private Post post;
	
	@Column(name="comment_description",  nullable=false)
	private String description;		
	
	@Column(name="comment_submittedAt",  nullable=false)
	private Timestamp submittedAt;
	

	

	public Comment() {
		
	}
	
	public int getId() {
		return id;
	}




	public void setId(int id) {
		this.id = id;
	}




	public User getAuthor() {
		return author;
	}




	public void setAuthor(User author) {
		this.author = author;
	}




	public Post getPost() {
		return post;
	}




	public void setPost(Post post) {
		this.post = post;
	}




	public String getDescription() {
		return description;
	}




	public void setDescription(String description) {
		this.description = description;
	}




	public Timestamp getSubmittedAt() {
		return submittedAt;
	}




	public void setSubmittedAt(Timestamp submittedAt) {
		this.submittedAt = submittedAt;
	}




	@Override
	public String toString() {
		return "Comment [id=" + id + ", author=" + author.getFirstName() + ", description=" + description + "]";
	}




}
