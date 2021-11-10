package project2.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="likes",
uniqueConstraints = {@UniqueConstraint(columnNames = {"likes_post_id", "likes_user_id"})}
)
public class Like {
	
	
	/**
	 * 
	 */
	public Like() {
		super();
	}


	/**
	 * @param post
	 * @param user
	 * @param submittedAt
	 */
	public Like(Post post, User user, Timestamp submittedAt) {
		super();
		this.post = post;
		this.user = user;
		this.submittedAt = submittedAt;
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="likes_id")
	private int id;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="likes_post_id")
	private Post post;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="likes_user_id")
	private User user;
	
	@Column(name="likes_submittedAt")
	private Timestamp submittedAt;
	
	
	
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}



	public Timestamp getSubmittedAt() {
		return submittedAt;
	}


	public void setSubmittedAt(Timestamp submittedAt) {
		this.submittedAt = submittedAt;
	}


	@Override
	public String toString() {
		return "Like [id=" + id + ", post=" + post + ", user=" + user + ", submittedAt=" + submittedAt + "]";
	}


	public Post getPost() {
		return post;
	}


	public void setPost(Post post) {
		this.post = post;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}



}
