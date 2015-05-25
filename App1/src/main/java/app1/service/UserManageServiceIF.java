package app1.service;

import java.util.List;

import app1.domain.App1User;

public interface UserManageServiceIF {

	
	public abstract int saveUserToDBService(App1User app1User);
	public abstract int deleteUserFromDBService(String args);
	public abstract List<App1User> fetchUsersFromDBService();
	public abstract App1User findUserByNameLoggining(String userName); 
	
}
