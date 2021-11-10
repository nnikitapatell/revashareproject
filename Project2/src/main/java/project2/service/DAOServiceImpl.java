package project2.service;

import java.sql.Timestamp;
import java.util.UUID;

import org.apache.commons.text.RandomStringGenerator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project2.dao.DAOLayer;
import project2.email.Email;
import project2.model.Comment;
import project2.model.Like;
import project2.model.Post;
import project2.model.User;

@Service("DaoService")
public class DAOServiceImpl implements DAOService {

	final static Logger logger = Logger.getLogger(DAOServiceImpl.class);

	DAOLayer myDao;
	

	public DAOServiceImpl() {
		logger.setLevel(Level.TRACE);
		logger.trace("Service Layer Activated");
	}

	
	public DAOServiceImpl(DAOLayer myDao) {
		super();
		this.myDao = myDao;
		
	}

	public DAOLayer getMyDao() {
		return myDao;
	}

	@Autowired
	public void setMyDao(DAOLayer mydao) {
		this.myDao = mydao;
	}

	private User myUser; // local object copy
	private Post myPost; // local object copy

	@Override
	public User addUser(User user) {
		// System.out.println("DAOServiceImpl: addUser");
		User aUser = myDao.addUser(user);
		if (aUser == null) {
			logger.info(aUser + " is null");
			return null;
		} else {
			myUser = aUser;
		}
		return myUser;
	}

	@Override
	public User getUserbyUsername(String username) {
		// System.out.println("DAOServiceImpl: findUserbyUsername");
		User aUser = myDao.getUserbyUsername(username);
		if (aUser == null) {
			logger.info(aUser + " is null");
			return null;
		} else {
			myUser = aUser;
		}
		return myUser;
	}

	@Override
	public User updateUser(User user) {
		// System.out.println("DAOServiceImpl: updateUser");
		User aUser = myDao.updateUser(user);
		if (aUser == null) {
			logger.info(aUser + " is null");
			return null;
		} else {
			myUser = aUser;
		}
		return myUser;
	}

	@Override
	public User[] searchUserbyUsername(String username) {
		// System.out.println("DAOServiceImpl: searchUserbyUsername");
		User[] user = myDao.searchUserbyUsername(username);
		if (user.length == 0) {
			logger.info("User array is empty");
			return null;
		} else {
			return user;
		}
	}

	@Override
	public Post createPost(Post post) {
		// System.out.println("DAOServiceImpl: createPost");
		Post aPost = myDao.createPost(post);
		if (aPost == null) {
			return null;
		} else {
			myPost = aPost;
		}
		return myPost;
	}

	@Override
	public Post[] getUserPosts(User user, int offset, int limit) {
		// System.out.println("DAOServiceImpl: getUserPosts");
		Post[] userPosts = myDao.getUserPosts(user, offset, limit);
		return userPosts;
	}

	public Post[] getUserPosts(User user) {
		// System.out.println("DAOServiceImpl: getUserPosts");
		Post[] userPosts = myDao.getUserPosts(user, 0, 10);
		if (userPosts.length == 0) {
			return null;
		} else {
			return userPosts;
		}
	}
	
	public Post[] getUserPosts(Timestamp myTime, User user, int offset, int limit) {
		// System.out.println("DAOServiceImpl: getUserPosts");
		Post[] userPosts = myDao.getUserPosts(user, offset, limit, myTime);
		return userPosts;
	}

	@Override
	@Deprecated
	public Post[] getRecentPosts(int postCount) {
		// System.out.println("DAOServiceImpl: getRecentPosts");
		Post[] recentPosts = myDao.getAllPosts(0, postCount);
		if (recentPosts.length == 0) {
			return null;
		} else {
			return recentPosts;
		}
	}

	@Override
	public boolean likePost(User user, Post post) {
		// System.out.println("DAOServiceImpl: likePost");
		boolean likePost = myDao.addLike(user, post);
		if (likePost) {
			System.out.println("like");
			return true;
		} else {
			System.out.println("fail");
			return false;
		}
	}

	@Override
	public Post[] getAllPosts(int offset, int limit) {
		// System.out.println("DAOServiceImpl: getRecentPosts");
		Post[] recentPosts = myDao.getAllPosts(offset, limit);
		if (recentPosts.length == 0) {
			return null;
		} else {
			return recentPosts;
		}
	}
	
	public Post[] getAllPosts(int offset, int limit, Timestamp myTime) {
		// System.out.println("DAOServiceImpl: getRecentPosts");
		Post[] recentPosts = myDao.getAllPosts(offset, limit, myTime);
		if (recentPosts.length == 0) {
			return null;
		} else {
			return recentPosts;
		}
	}

	@Override
	public User loginUser(String username, String password) {
		User aUser = myDao.getUserbyUsername(username);

		if (aUser == null) {
			logger.info("User is null");
			return null;
		}

		// check if password matches already encrypted value
		boolean check = aUser.checkPassword(password);

		if (check) {
			myUser = aUser;
			return aUser;
		} else {
			return null;
		}

	}

	@Override
	public boolean addComment(Post post, Comment comment) {
		// System.out.println("DAOServiceImpl: addComment");
		boolean addComment = myDao.addComment(post, comment);
		if (addComment) {
			logger.info("Comment added");
			return true;
		} else {
			logger.info("Unable to add comment");
			return false;
		}
	}

	@Override
	public Comment[] getComments(Post post, int offset, int limit) {
		Comment[] aComment = myDao.getComments(post, offset, limit);
		if (aComment.length == 0) {
			logger.info("Comment array is empty");
			return null;
		} else {
			return aComment;
		}
	}

	@Override
	public Long getLikeCount(Post post) {
		// System.out.println("DAOServiceImpl: getLikeCount");
		Long likeCount = myDao.getLikeCount(post);
		return likeCount;
	}

	public Post getPostById(int post) {
		return myDao.getPostById(post);
	}

	public void unlike(Like like) {
		myDao.delLike(like);
	}

	public void unlike(User user, Post post) {
		Like like = myDao.getLike(user, post);
		myDao.delLike(like);
	}

	public Long getCommentCount(Post post) {
		return myDao.getCommentCount(post);
	}

	public boolean isliked(User user, Post post) {
		return myDao.isliked(user, post);
	}

	public Like getLike(User user, Post post) {
		return myDao.getLike(user, post);
	}

	public User getUserbyId(UUID id) {
		return myDao.getUserbyID(id);
	}

	public void resetPwd(String email) {
		logger.trace("reset");
		User user = myDao.getUserbyEmail(email);
		if (user == null) { 
			System.out.println("Not found");
			return; 
		}
		
		String newPwd = new RandomStringGenerator.Builder().withinRange(33, 122).build().generate(10);
		user.setPassword(newPwd);
		
		user = updateUser(user);
		Email.sendMail(newPwd, email);
	}
	
	public Long countNotifications(User user) {
		return myDao.countPostsAfterTimeStamp(user.getLastChecked());
	}

	
}
