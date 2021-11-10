package project2.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;




@Entity
@Table(name="Users")
public class User {
	
	@Id
	@Column(name="users_id")
	private UUID id;
	
	/**
	 * 
	 */
	public User() {
		super();
		//this.id = UUID.randomUUID();
	}

	

	@Column(name="users_username", unique= true, nullable=false)
	private String username;
	
	@Column(name="users_email",  unique= true, nullable=false)
	private String email;
	
	
	@Column(name="users_password", nullable=false)
	private String password;
	
	@JsonIgnore
	@Column(name="users_salt", nullable=false)
	private byte [] salt = null;
	
	@Column(name="users_firstName", nullable=false)
	private String firstName;
	
	@Column(name="users_lastName", nullable=false)
	private String lastName;
	
	@Column(name="users_aboutMe")
	private String aboutMe;
	
	@Column(name="users_imageURL")
	private String imageURL;
	
	@Column(name="users_lastChecked")
	private Timestamp lastChecked;
	
	@OneToMany(mappedBy = "author", fetch=FetchType.LAZY)
	private List<Post> posts;

	@OneToMany(mappedBy = "user", fetch=FetchType.LAZY)
	private List<Like> likes;
	
	public User(String username,  String password, String firstName, String lastName, String email) {
		super();
		this.username = username;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.id = UUID.randomUUID();
		this.salt = makeSalt();
		this.password = encrypt(password);
	}

	public User(UUID id, String username, String email, String password, byte[] salt, String firstName, String lastName,
			String aboutMe, String imageURL, Timestamp lastChecked) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.salt = salt;
		this.firstName = firstName;
		this.lastName = lastName;
		this.aboutMe = aboutMe;
		this.imageURL = imageURL;
		this.lastChecked = lastChecked;
	}

	/**
	 * @return the id
	 */
	
	public UUID getId() {
		if (this.id == null) {
			this.id = UUID.randomUUID();
			
		} 
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(UUID id) {
		this.id = id;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return "";
	}

	/**
	 * Encrypts a string and stores the hash.
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = encrypt(password);
	}

	/**
	 * @return the salt
	 */
	public byte[] getSalt() {
		if (this.salt == null) {
			this.salt = makeSalt();
		}
		return salt;
	}

	/**
	 * @param salt the salt to set
	 */
	public void setSalt(byte[] salt) {
		this.salt = salt;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the aboutMe
	 */
	public String getAboutMe() {
		return aboutMe;
	}

	/**
	 * @param aboutMe the aboutMe to set
	 */
	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}

	/**
	 * @return the imageURL
	 */
	public String getImageURL() {
		return imageURL;
	}

	/**
	 * @param imageURL the imageURL to set
	 */
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	/**
	 * @return the lastChecked
	 */
	public Timestamp getLastChecked() {
		return lastChecked;
	}

	/**
	 * @param lastChecked the lastChecked to set
	 */
	public void setLastChecked(Timestamp lastChecked) {
		this.lastChecked = lastChecked;
	}
	
	/*
	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public List<Like> getLikes() {
		return likes;
	}

	public void setLikes(List<Like> likes) {
		this.likes = likes;
	}
	
	*/

	/**
	 * Uses the stored Salt to encrypt the String parameter
	 * and stores the hash as this.password.
	 * @param passwordToHash
	 * @return
	 */
	public String encrypt(String passwordToHash) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(this.getSalt());
			byte[] bytes = md.digest(passwordToHash.getBytes());
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			return sb.toString();

		} catch (NoSuchAlgorithmException e) {
			System.out.println("Encryption disabled.");
			return passwordToHash;
		}

	}

	private static byte[] makeSalt() {
		SecureRandom sr = new SecureRandom();
		byte[] salt = new byte[16];
		sr.nextBytes(salt);
		return salt;
	}
	
	public boolean checkPassword (String str) {
		System.out.println(str);
		String cryptostr = encrypt (str);
		System.out.println("Encrypt: " + cryptostr);
		
		if (password.equals(cryptostr)) {
			return true;
		} 
		return false;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", firstName=" + firstName + ", lastName=" + lastName
				+ "]";
	}
	
	public void update(User newInfo) {
	        if (newInfo.aboutMe != null && !newInfo.username.isBlank()) {
	            setUsername(newInfo.username);
	        }
	        
	        
	        if (newInfo.aboutMe != null && !newInfo.email.isBlank()) {
	            setEmail(newInfo.email);
	        }
	        
	        if (newInfo.aboutMe != null && !newInfo.password.isBlank()) {
	            setPassword(newInfo.password);
	        }
	        
	        if (newInfo.aboutMe != null && !newInfo.firstName.isBlank()) {
	            setFirstName(newInfo.firstName);
	        }
	        
	        if (newInfo.aboutMe != null && !newInfo.lastName.isBlank()) {
	            setLastName(newInfo.lastName);
	        }
	        
	        if (newInfo.aboutMe != null && !newInfo.aboutMe.isBlank()) {
	            setAboutMe(newInfo.aboutMe);
	        }
	        
	        if (newInfo.imageURL != null && !newInfo.imageURL.isBlank()) {
	            setImageURL(newInfo.aboutMe);
	        }
	        
	    }
}
