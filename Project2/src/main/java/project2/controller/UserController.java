package project2.controller;

import java.lang.reflect.Field;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import project2.model.ResponseMessage;
import project2.model.User;
import project2.service.DAOServiceImpl;

@CrossOrigin(origins="http://34.125.184.110:4200", allowCredentials = "true")
@Controller
@RequestMapping("/user")
public class UserController {

	private DAOServiceImpl daoService;
	

	public UserController() {
	}
	
	public UserController(DAOServiceImpl daoService) {
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
	 * Creates a user based on the body of the post request.
	 * 
	 * localhost:9002/Project2/api/user
	 * @param newUser
	 * @return JSON String
	 */
	@PostMapping(value="")
	@ResponseStatus(value = HttpStatus.CREATED)
	public @ResponseBody ResponseMessage createUser(@RequestBody User newUser, HttpSession session) {
		daoService.addUser(newUser);
		if(newUser != null) {
			session.setAttribute("currentUser", newUser);
			return new ResponseMessage("success", "");
		}
		else {
			return new ResponseMessage("failure", "");
		}
	}

	/**
	 * Returns the user if they exist in the session.
	 * 
	 * localhost:9002/Project2/api/user/current
	 * @param session
	 * @return JSON User
	 */
	@GetMapping(value="/current")
	public @ResponseBody User getCurrentUser(HttpSession session) {
		User currentUser = (User) session.getAttribute("currentUser");
		return currentUser;
	}

	/**
	 * Returns a user based on a username. Uses a query parameter
	 * 
	 * localhost:9002/Project2/api/user?username=testuser
	 * @param username
	 * @return JSON User
	 */
	@GetMapping(value="", params= {"username"})
	public @ResponseBody User getUserByUsername(String username) {
		User tempUser = daoService.getUserbyUsername(username);
		return tempUser;
	}
	
	
	/**
	 * Returns users based on part of a username. Uses a query parameter.
	 * 
	 * localhost:9002/Project2/api/user/search?username=testuser
	 * @param username
	 * @return JSON User
	 */
	@GetMapping(value="/search", params= {"username"})
	public @ResponseBody User[] getUsersByUsername(String username) {
		User[] userList = daoService.searchUserbyUsername(username);
		return userList;
	}
	
	
	/**
	 * Used to login a user with login credentials as a JSON array
	 * First value is username, second is password
	 * 
	 * localhost:9002/Project2/api/user/login 
	 * @param session
	 * @param loginForm
	 * @return JSON String
	 */
	
	@PostMapping(value="/login")
	public @ResponseBody ResponseMessage login(HttpSession session, @RequestBody String[] loginForm) {
		User currentUser = daoService.loginUser(loginForm[0], loginForm[1]);
		System.out.println(currentUser);
		
		if(currentUser != null) {
			session.setAttribute("currentUser", currentUser);
			daoService.addUser(currentUser);
			return new ResponseMessage("success", "");
		}
		else {
			return new ResponseMessage("failure", "");
		}
	}
	
	/**
	 * Used to logout a logged in user.
	 * 
	 * localhost:9002/Project2/api/user/logout
	 * @param myReq
	 * @return JSON String
	 */
	@PostMapping(value="/logout")
	public @ResponseBody ResponseMessage logout(HttpServletRequest myReq) {
		HttpSession session = myReq.getSession(false);
		
		if(session != null) {
			session.invalidate();
		}
		
		return new ResponseMessage("success", "");
	}
	
	/**
	 * Returns all the users.
	 * 
	 * localhost:9002/Project2/api/user
	 * @return JSON User Array
	 */
	/*@GetMapping(value = "")
	public @ResponseBody List<User> getAllUsers() {
		List<User> users = daoService.getAllUsers();
		return users;
	}*/
	
	
	/**
	 * Updates a user based on the body of the put request.
	 * 
	 * localhost:9002/Project2/api/user
	 * @param currentUser
	 * @return JSON String
	*/
	@PutMapping(value="")
	public @ResponseBody ResponseMessage updateUser(HttpSession session, @RequestBody User updateInfo) {
		User storedUser = (User) session.getAttribute("currentUser");
		System.out.println("info: " +updateInfo);
		if(storedUser.getId().equals(updateInfo.getId())) {
			storedUser.update(updateInfo);
			daoService.updateUser(storedUser);
			User updatedUser = daoService.getUserbyUsername(storedUser.getUsername());
			session.setAttribute("currentUser", updatedUser);
			return new ResponseMessage("success", "");
		}
		
		return new ResponseMessage("failure", "");
	}
	 
	
	/**
	 * Deletes a user based on the body of the delete request.
	 * 
	 * localhost:9002/Project2/api/user
	 * @param currentUser
	 * @return JSON String
	 */
	/*@DeleteMapping(value="")
	public @ResponseBody String deleteUser(@RequestBody User currentUser) {
		daoService.removeUser(currentUser);
		return "success";
	}*/
	
	/**
	 * Reset password and send new one through email.
	 * @param email
	 * @return
	 */
	   /**
     * Reset password and send new one through email.
     * @param email
     * @return JSON String
     */
    @PostMapping(value="/lostpwd")
    public @ResponseBody ResponseMessage lostPassword(@RequestBody String email) {
        daoService.resetPwd(email);
        return new ResponseMessage("success", "");
    }
	

}
