package app1.persistance;

import java.util.List;

import app1.domain.App1User;

public interface UserManageDAOIF {

	public abstract int saveUserToDB(App1User app1User);
	public abstract int deleteUserFromDB(String args);
	public abstract List<App1User> fetchUsersFromDB ();
	public abstract App1User findUserByNameLoggining(String userName); 
	
}
