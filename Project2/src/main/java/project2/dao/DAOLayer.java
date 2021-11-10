/**
 * 
 */
package project2.dao;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import project2.model.Comment;
import project2.model.Like;
import project2.model.Post;
import project2.model.User;

/**
 * @author Kienen
 *
 */
@Transactional
@Repository("Dao")
public class DAOLayer {
	private static Logger log = Logger.getLogger(DAOLayer.class);
	SessionFactory sesFactory;

	/**
	 * 
	 */
	public DAOLayer() {
		log.setLevel(Level.ALL);
		log.trace("DAO CONSTRUCTED");
	}

	public DAOLayer(SessionFactory sesFactory) {
		super();
		log.setLevel(Level.ALL);
		this.sesFactory = sesFactory;
	}

	public SessionFactory getSesFactory() {
		return sesFactory;
	}

	@Autowired
	public void setSesFactory(SessionFactory sesFactory) {
		this.sesFactory = sesFactory;
		log.setLevel(Level.ALL);
	}

	/**
	 * @param args
	 * 
	 *             public static void main(String[] args) {
	 *             Logger.getRootLogger().setLevel(Level.WARN);
	 *             log.setLevel(Level.ALL); DAOLayer Database = new DAOLayer();
	 * 
	 * 
	 * 
	 * 
	 * 
	 *             //System.out.println(user); //User test = new User ("testuser3",
	 *             "test", "first", "last", "test3@email.com");
	 *             //Database.addUser(test);
	 * 
	 *             //Database.updateUser(test); User user1 =
	 *             Database.getUserbyUsername("testuser3"); //log.debug("get by
	 *             username" + user1);
	 * 
	 *             //User [] users = Database.searchUserbyUsername("test");
	 *             //Post(UUID author, String description, Timestamp submittedAt)
	 *             Post post = new Post (user1.getId(), "Lorem ipsum x",
	 *             Timestamp.from(Instant.now())); Database.createPost(post);
	 *             Database.getUserPosts(user1, 0, 100);
	 * 
	 * 
	 *             System.out.println("done"); }
	 * 
	 */
	public User addUser(User user) {
		System.out.println("addUser: " + user);
		if (user.getId() == null) {
			user.setId(UUID.randomUUID());
			System.out.println("1");
		}
		Session ses = sesFactory.getCurrentSession();
		// Transaction tx = ses.beginTransaction();

		// NOW, let's get to our CRUD logic
		try {
			System.out.println("dao" + user);
			ses.saveOrUpdate(user);
		} catch (Exception e) {
			System.out.println("Add User Error" + e);
			return null;
		}

		log.debug(user);
		// boilerplate session end, no specific CRUD logic
		// tx.commit();
		return user;
	}

	public Post createPost(Post post) {
		return addPost(post);
	}

	public Post addPost(Post post) {
		log.trace("Create Post" + post);
		// boilerplate session start
		Session session = sesFactory.getCurrentSession();
		// Transaction tx = session.beginTransaction();

		// CRUD logic
		session.saveOrUpdate(post);

		log.debug("Post created: " + post);
		// boilerplate session end
		// tx.commit();
		return post;
	}

	public Post[] getAllPosts(int offset, int limit) {
		return getFilteredPosts(offset, limit, null, null);
	}
	
	public Post[] getAllPosts(int offset, int limit, Timestamp mytime) {
		return getFilteredPosts(offset, limit, null, mytime);
	}

	private Post[] getFilteredPosts(int offset, int limit, User user, Timestamp myTime) {
		// boilerplate session start
		log.trace("getFilteredPosts");
		Session session = sesFactory.getCurrentSession();

		// CRUD logic
		// EntityManager
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Post> criteriaQuery = cb.createQuery(Post.class);
		Root<Post> root = criteriaQuery.from(Post.class);
		criteriaQuery.select(root);
		Predicate timePredicate = cb.lessThan(root.get("submittedAt"), Timestamp.from(Instant.now()));
		Predicate userPredicate  = cb.lessThan(root.get("submittedAt"), Timestamp.from(Instant.now()));
		
		if (user != null ) {
			userPredicate = cb.equal(root.get("author"), user);
		} 
		if (myTime != null ) {
			timePredicate = cb.greaterThan(root.get("submittedAt"), myTime);
		}
		Predicate finalPredicate = cb.and(timePredicate, userPredicate);
		criteriaQuery.where(finalPredicate);
		
		criteriaQuery.orderBy(cb.desc(root.get("submittedAt"))); 
		List<Post> results = session.createQuery(criteriaQuery).setMaxResults(limit).setFirstResult(offset)
				.getResultList();

		if (log.isDebugEnabled()) {
			log.debug(results.size() + " results");
			for (Post post : results) {
				log.debug("Retrieved: " + post.getSubmittedAt().toLocalDateTime());
			}
		}

		return results.toArray(new Post[0]);

	}

	public User getUserbyID(UUID ID) {
		// boilerplate session start
		Session ses = sesFactory.getCurrentSession();

		// CRUD logic
		User user = ses.get(User.class, ID);

		// boilerplate session end
		return user;
	}

	public User getUserbyUsername(String username) {
		log.trace("Get user by: " + username);
		// boilerplate session start
		Session session = sesFactory.getCurrentSession();

		// CRUD logic
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<User> cr = cb.createQuery(User.class);
		Root<User> root = cr.from(User.class);
		cr.select(root).where(cb.equal(root.get("username"), username));

		Query<User> query = session.createQuery(cr);
		
		User user = query.getSingleResult();
		return user;
	}
	
	public User getUserbyEmail(String email) {
		log.trace("Get user by: " + email);
		// boilerplate session start
		Session session = sesFactory.getCurrentSession();

		// CRUD logic
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<User> cr = cb.createQuery(User.class);
		Root<User> root = cr.from(User.class);
		cr.select(root).where(cb.equal(root.get("email"), email));

		Query<User> query = session.createQuery(cr);
		try {
			User user = query.getSingleResult();
			return user;
		} catch (Exception e) {
			log.error("Get user by email error", e);
		}
		return null;
		
	}

	public Post[] getUserPosts(User user, int offset, int limit) {
		return getFilteredPosts(offset, limit, user, null);
	}
	
	public Post[] getUserPosts(User user, int offset, int limit, Timestamp myTime) {
		return getFilteredPosts(offset, limit, user, myTime);
	}

	public boolean addLike(User user, Post post) {
		log.trace("Create Like");
		Like like = new Like(post, user, Timestamp.from(Instant.now()));
		System.out.println(like);

		Session session = sesFactory.getCurrentSession();

		//session.saveOrUpdate(post);
		session.saveOrUpdate(like);

		log.debug("Like created: ");

		return true;

	}
	
	public Like getLike(User user, Post post) {
		Session session = sesFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Like> criteriaQuery = cb.createQuery(Like.class);

		Root<Like> root = criteriaQuery.from(Like.class);
		criteriaQuery.where(cb.and(
				cb.equal(root.get("post"), post)),
				cb.equal(root.get("user"), user)
		);
		//criteriaQuery.select(cb.count(root));

		Query<Like> query = session.createQuery(criteriaQuery);
		Like like = query.getSingleResult();	
		return like;
	}

	public User[] searchUserbyUsername(String username) {
		log.trace("Search users: " + username);
		// boilerplate session start
		Session session = sesFactory.getCurrentSession();

		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<User> cr = cb.createQuery(User.class);
		Root<User> root = cr.from(User.class);
		cr.select(root).where(cb.like(root.get("username"), "%" + username + "%"));

		Query<User> query = session.createQuery(cr);
		List<User> results = query.getResultList();

		if (log.isDebugEnabled()) {
			for (User user : results) {
				System.out.println(user);
			}
		}

		return results.toArray(new User[0]);
	}

	public void setLoggerLevel(Level level) {
		log.setLevel(level);
	}

	public User updateUser(User user) {
		return addUser(user);
	}

	public boolean addComment(Post post, Comment comment) {
		log.trace("Create Comment" + comment);

		Session session = sesFactory.getCurrentSession();

		session.saveOrUpdate(comment);
		// session.saveOrUpdate(post);

		log.debug("Comment created: " + comment);

		return true;
	}

	public Comment[] getComments(Post post, int offset, int limit) {

		// boilerplate session start
		log.trace("getComments");
		Session session = sesFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Comment> criteriaQuery = cb.createQuery(Comment.class);
		Root<Comment> root = criteriaQuery.from(Comment.class);
		criteriaQuery.select(root).where(cb.equal(root.get("post"), post.getId()));
		criteriaQuery.orderBy(cb.asc(root.get("submittedAt"))); 

		List<Comment> results = session.createQuery(criteriaQuery).setMaxResults(limit).setFirstResult(offset)
				.getResultList();

		if (log.isDebugEnabled()) {
			log.debug(results.size() + " results");
			for (Comment comment : results) {
				log.debug("Retrieved: " + comment);
			}
		}

		return results.toArray(new Comment[0]);

	}

	public Long getLikeCount(Post post) {
		Session session = sesFactory.getCurrentSession();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);

		Root<Like> root = criteriaQuery.from(Like.class);
		criteriaQuery.where(criteriaBuilder.equal(root.get("post"), post));
		criteriaQuery.select(criteriaBuilder.count(root));

		Query<Long> query = session.createQuery(criteriaQuery);
		Long count = query.getSingleResult();
		return count;
	}
	
	public Long countPostsAfterTimeStamp(Timestamp myTime) {
		log.debug("Time: " + myTime);
		Session session = sesFactory.getCurrentSession();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);

		Root<Post> root = criteriaQuery.from(Post.class);
		if (myTime != null) {
			criteriaQuery.where(criteriaBuilder.greaterThan(root.get("submittedAt"), myTime));
		}
		criteriaQuery.select(criteriaBuilder.count(root));

		Query<Long> query = session.createQuery(criteriaQuery);
		Long count = query.getSingleResult();
		return count;
	}

	public Post getPostById(int postId) {
		Session ses = sesFactory.getCurrentSession();
		Post post = ses.get(Post.class, postId);
		return post;
	}

	public void delLike(Like like) {
		Session ses = sesFactory.getCurrentSession();
		ses.delete(like);
	}

	public Long getCommentCount(Post post) {
		Session session = sesFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Long> cr = cb.createQuery(Long.class);
		Root<Comment> root = cr.from(Comment.class);
		cr.where(cb.equal(root.get("post"), post));
		cr.select(cb.count(root));
		Query<Long> query = session.createQuery(cr);
		Long count = query.getSingleResult();
		return count;
	}

	public boolean isliked(User user, Post post) {

		Session session = sesFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);

		Root<Like> root = criteriaQuery.from(Like.class);
		criteriaQuery.where(cb.and(
				cb.equal(root.get("post"), post.getId())),
				cb.equal(root.get("user"), user)
		);
		criteriaQuery.select(cb.count(root));

		Query<Long> query = session.createQuery(criteriaQuery);
		Long count = query.getSingleResult();
		return  (count > 0) ? true : false;

	}
}
