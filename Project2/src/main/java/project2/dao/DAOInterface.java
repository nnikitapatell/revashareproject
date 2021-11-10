package project2.dao;

import java.util.UUID;

import project2.model.Comment;
import project2.model.Post;
import project2.model.User;

public interface DAOInterface {

	
	/**
	 * Register a new account
	 * Returns null if no account created
	 * 
	 * @param user
	 * @return
	 */
	public User addUser(User user);
	
	/**
	 *  Returns the user account 
	 *  Returns null if none found
	 * @param username
	 * @return
	 */
	public User getUserbyUsername (String username);
	
	/**
	 *  Returns the user account 
	 *  Returns null if none found 
	 * @param ID
	 * @return
	 */
	public User getUserbyID (UUID ID);
	
	/* Update User information
	 * Changes information to what is contained in the new model
	 * returns the updated User
	 * @param user
	 * @return
	 */
	public User updateUser (User user);
	

	/**
	 * Search for Users with usernames containing this string.
	 * @param username
	 * @return
	 */
	public User [] searchUserbyUsername (String username);
	

	/*
	 * Persists a post in the data base and returns it
	 * Returns null if post is not created.
	 * Deprecated: Use "AddPost"
	 * @param post
	 * @return
	 */
	public Post addPost (Post post);
	
	/**
	 * 
	 * @param user 
	 * @param offset The first post to return.
	 * @param limit The number of posts to return.
	 * @return 
	 */
	public Post[] getUserPosts (User user, int offset, int limit);
	
	/**
	 * Return posts 
	 * @param offset The first post to return.
	 * @param limit The number of posts to return.
	 * @return
	 */
	public Post [] getAllPosts (int offset, int limit);
	
	
	/*
	 * Like Post
	 * @param user
	 * @param post
	 * @return
	 */
	public boolean addLike (User user, Post post);	
	
	/*
	 * Comment on a post
	 * @param user
	 * @param post
	 * @return
	 */
	public boolean addComment (Post post, Comment comment);	
	
	/*
	 * Get comments on a post
	 * @param user
	 * @param post
	 * @return
	 */
	public Comment [] getComments (Post post, int offset, int limit);	
	
	/**
	 * 
	 * @param post
	 * @return
	 */
	public Long getLikeCount (Post post);
	
}
