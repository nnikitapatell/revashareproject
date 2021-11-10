package project2.controller;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import project2.model.Comment;
import project2.model.Post;
import project2.model.ResponseMessage;
import project2.model.User;
import project2.service.DAOServiceImpl;

@CrossOrigin(origins="http://34.125.184.110:4200", allowCredentials = "true")
@Controller
@RequestMapping("/post")
public class PostController {
	
	private DAOServiceImpl daoService;
	

	public PostController() {
	}
	
	public PostController(DAOServiceImpl daoService) {
		super();
		this.daoService = daoService;
	}
	
	public DAOServiceImpl getDaoService() {
		return daoService;
	}
	
	@Autowired
	public void setDaoService(DAOServiceImpl daoService) {
		this.daoService = daoService;
	}
	
	
	/**
	 * Creates a post based on the body of the post request.
	 * 
	 * localhost:9002/Project2/api/post
	 * @param context
	 */
	@PostMapping(value="")
	@ResponseStatus(value = HttpStatus.CREATED)
	public @ResponseBody ResponseMessage createPost(@RequestBody Post newPost, HttpSession session) {
		
		User currentUser = (User) session.getAttribute("currentUser");
		newPost.setAuthor(currentUser);
		
		daoService.createPost(newPost);
		return new ResponseMessage("success", "");
	}
	
	
	/**
	 * Returns all posts from offset and limit. Optional query parameters offset and limit.
	 * 
	 * localhost:9002/Project2/api/post?offset=0&limit=5
	 * @return JSON Post Array
	 */
	@GetMapping(value="")
	public @ResponseBody Post[] getAllPosts(
			@RequestParam("offset") Optional<Integer> offset,
			@RequestParam("limit") Optional<Integer> limit,
			@RequestParam("user") Optional<String> id) {
		
		Post[] posts;
		int[] offsetAndLimit = getOptionalValues(offset, limit);
		
		if (id.isPresent()) {
			UUID userId = UUID.fromString(id.get());
			User user = daoService.getUserbyId(userId);
			posts = daoService.getUserPosts(user, offsetAndLimit[0], offsetAndLimit[1]);
		} else {
			posts = daoService.getAllPosts(offsetAndLimit[0], offsetAndLimit[1]);
		}
		
		return posts;
	}
	
	
	/**
	 * Returns current user posts. Optional query parameters offset and limit.
	 * It's a Post request because it needs a user as input.
	 * 
	 * localhost:9002/Project2/api/post/user?offset=0&limit=5
	 * @return JSON Post Array
	 */
	@GetMapping(value="/user")
	public @ResponseBody Post[] getCurrentUserPosts(
				@RequestParam("offset") Optional<Integer> offset, 
				@RequestParam("limit") Optional<Integer> limit,
				HttpSession session) {
		
		User currentUser = (User) session.getAttribute("currentUser");
		if(currentUser == null) {
			return null;
		}
		
		int[] offsetAndLimit = getOptionalValues(offset, limit);
		Post[] posts = daoService.getUserPosts(currentUser, offsetAndLimit[0], offsetAndLimit[1]);
		return posts;
	}
	
	
	/**
	 * Returns a post based on an id. Uses a query parameter.
	 *
	 * localhost:9002/Project2/api/post?id=0
	 * @return JSON Post
	 */
	@GetMapping(value="", params= {"postId"})
	public @ResponseBody Post getPostById(int postId) {
		Post currentPost = daoService.getPostById(postId);
		return currentPost;
	}
	
	
	/**
	 * Creates a like based on query parameter of post id
	 * 
	 * localhost:9002/Project2/api/post/like?postId=0
	 * @param newLike
	 * @return JSON String
	 */
	@PostMapping(value="/like", params= {"postId"})
	@ResponseStatus(value = HttpStatus.CREATED)
	public @ResponseBody ResponseMessage likePost(HttpSession session, int postId) {
		Post currentPost = daoService.getPostById(postId);
		daoService.likePost((User)session.getAttribute("currentUser"), currentPost);
		return new ResponseMessage("success", "");
	}
	
	
	/**
	 * Gets the number of likes on a post.
	 * Expects a Post id as a query parameter
	 * 
	 * localhost:9002/Project2/api/post/like/count?postId=0
	 * @param newLike
	 * @return JSON String
	 */
	@GetMapping(value="/like/count", params= {"postId"})
	public @ResponseBody long getLikesOnPost(int postId) {
		Post currentPost = daoService.getPostById(postId);
		long likeCount = daoService.getLikeCount(currentPost);
		return likeCount;
	}
	
	@GetMapping(value="/isliked", params= {"postId"})
	public @ResponseBody boolean isLiked (int postId, HttpSession session) {
		User currentUser = (User) session.getAttribute("currentUser");
		Post currentPost = daoService.getPostById(postId);
		boolean isLiked = (currentUser != null ? daoService.isliked(currentUser, currentPost) : false);
		return isLiked;
	}	
	
	//unlike(Like like)

	@PostMapping(value="/unlike", params= {"postId"})
	public @ResponseBody  ResponseMessage unlike (int postId, HttpSession session) {
		User currentUser = (User) session.getAttribute("currentUser");
		Post currentPost = daoService.getPostById(postId);
		daoService.unlike(currentUser, currentPost);
		return new ResponseMessage("success","");
	}	
	/**
	 * 
	 * @param postId
	 * @return
	 */
	@GetMapping(value="/comments/count", params= {"postId"})
	public @ResponseBody long getCommentsOnPost(int postId) {
		Post currentPost = daoService.getPostById(postId);
		long likeCount = daoService.getCommentCount(currentPost);
		return likeCount;
	}
	
	/**
	 * Creates a comment based on the body of the post request.
	 * Expects a Post id as a query parameter
	 * 
	 * localhost:9002/Project2/api/post/comment?postId=0
	 * @param newComment
	 * @return JSON String
	 */
	@PostMapping(value = "/comment", params= {"postId"})
	@ResponseStatus(value = HttpStatus.CREATED)
	public @ResponseBody ResponseMessage commentOnPost(int postId, @RequestBody Comment newComment, HttpSession session) {
		Post currentPost = daoService.getPostById(postId);
		User user = (User)session.getAttribute("currentUser");
		newComment.setAuthor(user);
		newComment.setPost(currentPost);
		newComment.setSubmittedAt(Timestamp.from(Instant.now()));
		daoService.addComment(currentPost, newComment);
		return new ResponseMessage("success","");				 
	}
	
	
	/**
	 * Gets the comments of a post.
	 * Expects a Post id as a query parameter. Optional query parameters offset and limit.
	 * 
	 * localhost:9002/Project2/api/post/comments?postId=0&offset=0&limit=5
	 * @param newComment
	 * @return JSON String
	 */
	@GetMapping(value = "/comments")
	public @ResponseBody Comment[] getCommentsOnPost(
			@RequestParam("postId") int postId, 
			@RequestParam("offset") Optional<Integer> offset, 
			@RequestParam("limit") Optional<Integer> limit) {
		
		Post currentPost = daoService.getPostById(postId);
		int[] offsetAndLimit = getOptionalValues(offset, limit);
		return daoService.getComments(currentPost, offsetAndLimit[0], offsetAndLimit[1]);
	}
	
	/**
	 * Returns hardcodded offset=0 and limit=5 unless both optional values are present
	 * 
	 * @param offset
	 * @param limit
	 * @return Int Array
	 */
	private int[] getOptionalValues(Optional<Integer> offset, Optional<Integer> limit) {
		int[] result = {0, 5};
		
		if(offset.isPresent() && limit.isPresent()) {
			result[0] = offset.get();
			result[1] = limit.get();
		}
		
		return result;
	}
	
	@GetMapping(value="/notifications")
	public @ResponseBody Post[] getNotifications(
				@RequestParam("offset") Optional<Integer> offset, 
				@RequestParam("limit") Optional<Integer> limit,
				HttpSession session) {
		
		User currentUser = (User) session.getAttribute("currentUser");
		if(currentUser == null) {
			return null;
		}
		
		int[] offsetAndLimit = getOptionalValues(offset, limit);
		Post[] posts = daoService.getAllPosts(offsetAndLimit[0], offsetAndLimit[1], currentUser.getLastChecked());

		return posts;
	}
	
	@GetMapping(value="/notifications/reset")
	public @ResponseBody ResponseMessage resetNotifications(HttpSession session) {
		
		User currentUser = (User) session.getAttribute("currentUser");
		if(currentUser == null) {
			return null;
		}
		
		currentUser.setLastChecked(Timestamp.from(Instant.now()));
		daoService.updateUser(currentUser);
		return new ResponseMessage("success","");	
	}
	
	@GetMapping(value="/notifications/count")
	public Long countNotifications(HttpSession session) {
		
		User currentUser = (User) session.getAttribute("currentUser");
		if(currentUser == null) {
			return null;
		}
		
		Long posts = daoService.countNotifications(currentUser);
		return posts;
	}
}
