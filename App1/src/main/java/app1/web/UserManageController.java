package app1.web;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import app1.domain.App1User;
import app1.service.UserManageServiceIF;



@Controller
@RequestMapping(value="/restrictedAccess.htm")
public class UserManageController {

	
	@Autowired
	UserManageServiceIF userManagerServiceImpl;
	
	public UserManageServiceIF getUserManagerServiceIF() {
		return userManagerServiceImpl;
	}
	public void setUserManagerServiceIF(UserManageServiceIF userManagerServiceIF) {
		this.userManagerServiceImpl = userManagerServiceIF;
	}
	
	
	/*@Autowired
	private UserFormValidator userFormValidator;
	public UserFormValidator getUserFormValidator() {
		return userFormValidator;
	}
	public void setUserFormValidator(UserFormValidator userFormValidator) {
		this.userFormValidator = userFormValidator;
	}
	
	
	@InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(userFormValidator);
    }*/
	
	
	
	/*This method load data from database on start up to create model for jsp page*/
	 @ModelAttribute("usersModel")
	 public List<App1User> getUserModel() 
	 {
		List<App1User> usersList = userManagerServiceImpl.fetchUsersFromDBService();
		return usersList;
	 }	
	 
	 
	 
	/*Render view(Hello.jsp page) and inject empty object to user session 
	  * for registration form*/
	 @RequestMapping(method = RequestMethod.GET)
	 public ModelAndView loadAllUsers() 
	 {
			System.out.println("MVC instrted to loadAllUsers()");
			return new ModelAndView("hello", "command", new App1User());
	 }
	  
	 
	 
	 /*This method will be triggered when user has to be deleted
	  * User deletion perform by username in database*/
	@RequestMapping(params="delete", method = RequestMethod.POST)
	public String deleteUser(@ModelAttribute("usernameVal")String userName, BindingResult result, HttpServletRequest request) 
	{
		System.out.println("MVC instrted to printHello-delete()");
		int i = userManagerServiceImpl.deleteUserFromDBService(userName);
	    return "redirect:/restrictedAccess.htm";
	}
	
	
	
	/*This  method save new user to database 
	 * This method use new App1User() object with mark 'command' to transfer data */
	@RequestMapping(params="save", method = RequestMethod.POST)
	public String saveUser(@ModelAttribute("command")  App1User app1User, BindingResult result, HttpServletRequest request) 
	{ //@Valid
		
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		app1User.setPassword(encoder.encodePassword(app1User.getPassword(), null));
		
				
		/*if(result.hasErrors() || userExistiOrNot != null) {
			System.out.println("VALIDATION IS CHEAKED");
			return "redirect:/restrictedAccess.htm";
		} else {*/
		System.out.println("MVC instrted to printHello-delete()");
		int i = userManagerServiceImpl.saveUserToDBService(app1User);
	    return "redirect:/restrictedAccess.htm";
		//}
	}
		
}
