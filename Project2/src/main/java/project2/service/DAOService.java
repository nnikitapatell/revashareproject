package project2.service;


import project2.model.Comment;
import project2.model.Post;
import project2.model.User;

public interface DAOService {
	/**
	 * calls myDao to add user User Object
	 * @param user
	 * @return
	 */
	public User addUser(User user);

	/**
	 * calls myDao to find user by username
	 * @param username
	 * @return
	 */
	public User getUserbyUsername(String username);

	/**
	 * calls mydao to update user 
	 * @param user
	 * @return
	 */
	public User updateUser(User user);

	/**
	 * calls mydao to search user by username and password 
	 * @param username
	 * @return
	 */
	public User[] searchUserbyUsername(String username);

	
	/**
	 * calls mydao to create post 
	 * @param post
	 * @return
	 */
	public Post createPost(Post post); 

	
	/**
	 * calls mydao to get user posts 
	 * @param user
	 * @return
	 */
	public Post[] getUserPosts(User user, int offset, int limit);

	/**
	 * calls mydao to get recent posts 
	 * @param postCount
	 * @return
	 */
	public Post[] getAllPosts(int offset, int limit);
	
	@Deprecated
	public Post[] getRecentPosts(int postCount);
	
	/**
	 * calls mydao to like posts 
	 * @param user
	 * @param post
	 * @return
	 */
	public boolean likePost(User user, Post post) ;


	/**
	 * @param post
	 * @return
	 */
	public Post getPostById(int post);

	/**
	 * @param username
	 * @param password
	 * @return
	 */
	public User loginUser(String username, String password);
	
	

	/**
	 * @param post
	 * @param comment
	 * @return
	 */
	public boolean addComment(Post post, Comment comment);
	
	/**
	 * @param post
	 * @param offset
	 * @param limit
	 * @return
	 */
	public Comment [] getComments(Post post, int offset, int limit);
	
	/**
	 * @param post
	 * @return
	 */
	public Long getLikeCount(Post post);
	
	
}
