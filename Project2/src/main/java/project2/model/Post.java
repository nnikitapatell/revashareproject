/**
 * 
 */
package project2.model;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Kienen
 *
 */
@Entity
@Table(name="posts")
public class Post {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="post_id")
	private int id;			
	
	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}


	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="post_author_id", nullable=false)
	private User author;
	
	@JsonIgnore
	@OneToMany(mappedBy = "post", fetch=FetchType.LAZY)
	private List<Comment> comments;
	
	@JsonIgnore		
	@OneToMany(mappedBy = "post", fetch=FetchType.LAZY)
	private List<Like> likes;
	
	public List<Like> getLikes() {
		return likes;
	}

	public void setLikes(List<Like> likes) {
		this.likes = likes;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}


	@Column(name="post_description",  nullable=false)
	private String description;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageUrl1() {
		return imageUrl1;
	}

	public void setImageUrl1(String imageUrl1) {
		this.imageUrl1 = imageUrl1;
	}

	public String getImageUrl2() {
		return imageUrl2;
	}

	public void setImageUrl2(String imageUrl2) {
		this.imageUrl2 = imageUrl2;
	}

	public String getImageUrl3() {
		return imageUrl3;
	}

	public void setImageUrl3(String imageUrl3) {
		this.imageUrl3 = imageUrl3;
	}

	public String getImageUrl4() {
		return imageUrl4;
	}

	public void setImageUrl4(String imageUrl4) {
		this.imageUrl4 = imageUrl4;
	}

	public String getYouTubeUrl() {
		return youTubeUrl;
	}

	public void setYouTubeUrl(String youTubeUrl) {
		this.youTubeUrl = youTubeUrl;
	}

	public Timestamp getSubmittedAt() {
		return submittedAt;
	}

	public void setSubmittedAt(Timestamp submittedAt) {
		if (submittedAt == null) {
			this.submittedAt = Timestamp.from(Instant.now());
		} else {
		this.submittedAt = submittedAt;
		}
	}


	@Column(name="post_imageUrl1")
	private String imageUrl1;
	
	@Column(name="post_imageUrl2")
	private String imageUrl2;


	@Column(name="post_imageUrl3")
	private String imageUrl3;


	@Column(name="post_imageUrl4")			
	private String imageUrl4;
	
	@Column(name="post_youTubeUrl")			
	private String youTubeUrl;
	
	@Column(name="post_submittedAt", nullable=false)			
	private Timestamp submittedAt;
	
	/**
	 * 
	 */
	public Post() {
		// TODO Auto-generated constructor stub
		this.submittedAt = Timestamp.from(Instant.now());
	}
	
	/**
	 * @param id
	 * @param author
	 * @param description
	 * @param imageUrl1
	 * @param imageUrl2
	 * @param imageUrl3
	 * @param imageUrl4
	 * @param youTubeUrl
	 * @param submittedAt
	 */
	public Post(int id, UUID author, String description, String imageUrl1, String imageUrl2, String imageUrl3,
			String imageUrl4, String youTubeUrl, Timestamp submittedAt) {
		super();
		this.id = id;
		//this.author = author;
		this.description = description;
		this.imageUrl1 = imageUrl1;
		this.imageUrl2 = imageUrl2;
		this.imageUrl3 = imageUrl3;
		this.imageUrl4 = imageUrl4;
		this.youTubeUrl = youTubeUrl;
		if (submittedAt == null) {
			this.submittedAt = Timestamp.from(Instant.now());
		} else {
		this.submittedAt = submittedAt;
		}
	}		


	/**
	 * @param id
	 * @param author
	 * @param description
	 * @param submittedAt
	 */
	public Post(UUID author, String description, Timestamp submittedAt) {
		super();
		
		//this.author = author;
		this.description = description;
		if (submittedAt == null) {
			this.submittedAt = Timestamp.from(Instant.now());
		} else {
			this.submittedAt = submittedAt;
		}
	}

	/**
	 * @param id
	 * @param author
	 * @param description
	 * @param imageUrl1
	 * @param imageUrl2
	 * @param imageUrl3
	 * @param imageUrl4
	 * @param youTubeUrl
	 * @param submittedAt
	 */
	public Post(int id, User author, String description, String imageUrl1, String imageUrl2, String imageUrl3,
			String imageUrl4, String youTubeUrl, Timestamp submittedAt) {
		super();
		this.id = id;
		this.author = author;
		this.description = description;
		this.imageUrl1 = imageUrl1;
		this.imageUrl2 = imageUrl2;
		this.imageUrl3 = imageUrl3;
		this.imageUrl4 = imageUrl4;
		this.youTubeUrl = youTubeUrl;
		if (submittedAt == null) {
			this.submittedAt = Timestamp.from(Instant.now());
		} else {
		this.submittedAt = submittedAt;
		}
	}

	/**
	 * @param author
	 * @param description
	 * @param imageUrl1
	 * @param imageUrl2
	 * @param imageUrl3
	 * @param imageUrl4
	 * @param youTubeUrl
	 */
	public Post(User author, String description, String imageUrl1, String imageUrl2, String imageUrl3, String imageUrl4,
			String youTubeUrl) {
		super();
		this.author = author;
		this.description = description;
		this.imageUrl1 = imageUrl1;
		this.imageUrl2 = imageUrl2;
		this.imageUrl3 = imageUrl3;
		this.imageUrl4 = imageUrl4;
		this.youTubeUrl = youTubeUrl;
	}

/*
	@Override
	public String toString() {
		return "Post [id=" + id + ", author=" + author + ", description=" + description + "]";
	}
*/



}
