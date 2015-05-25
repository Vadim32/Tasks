package app1.web.authentification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import app1.domain.App1User;
import app1.persistance.UserManageDAOIF;

public class UserDetailsServiceImpl implements UserDetailsService {

	
	
	UserManageDAOIF userManageDAOIF;
	
		
	public UserManageDAOIF getUserManageDAOIF() {
		return userManageDAOIF;
	}
	public void setUserManageDAOIF(UserManageDAOIF userManageDAOIF) {
		this.userManageDAOIF = userManageDAOIF;
	}

	

	public UserDetails loadUserByUsername(String userName) 
												throws UsernameNotFoundException {
	
		
		App1User listUser = userManageDAOIF.findUserByNameLoggining(userName);
		String password = null;
		Collection<GrantedAuthority> authorities = null;
		
		if(listUser != null) {
			
						
				password = (String)listUser.getPassword();
				
				authorities = new ArrayList<GrantedAuthority>();
				
					authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
			
			
				org.springframework.security.core.userdetails.User secureUser = new
						org.springframework.security.core.userdetails.User(userName, password, authorities);
			
				return secureUser;
			
		} else {
			
			throw new UsernameNotFoundException("No user exist in datbase");
		}
			
		
		
	}

}
