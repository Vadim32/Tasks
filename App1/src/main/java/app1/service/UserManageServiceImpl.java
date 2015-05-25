package app1.service;

import java.util.List;

import app1.domain.App1User;
import app1.persistance.UserManageDAOIF;

public class UserManageServiceImpl implements UserManageServiceIF {

	
	private UserManageDAOIF userManageDAOIF;
		
	public UserManageDAOIF getUserManageDAOIF() {
		return userManageDAOIF;
	}
	public void setUserManageDAOIF(UserManageDAOIF userManageDAOIF) {
		this.userManageDAOIF = userManageDAOIF;
	}

	
	
	public int saveUserToDBService(App1User app1User) {
		return userManageDAOIF.saveUserToDB(app1User);
	}

	
	public int deleteUserFromDBService(String args) {
		return userManageDAOIF.deleteUserFromDB(args);
	}

	
	public List<App1User> fetchUsersFromDBService() {
		return userManageDAOIF.fetchUsersFromDB();
	}
	public App1User findUserByNameLoggining(String userName) {
		return userManageDAOIF.findUserByNameLoggining(userName);
	}

	
	
}
