package app1.persistance;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import app1.domain.App1User;


public class UserManageDAOImpl implements UserManageDAOIF {

	
	public SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
	public int saveUserToDB(App1User app1User) 
	{
		
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		System.out.println("USER TRIED TO ADD TO DB SAVEUSERTODB()");
		try {
			
			session.save(app1User);
			session.flush();	
			session.clear();
			transaction.commit();
			
		} catch(HibernateException e) {
			
			System.err.println(e);
			
			if(transaction != null)
				transaction.rollback();
				
			return -1;
			
		} finally {
			if(session != null)
				session.close();
		}
		
		return 1;
				
	}

	
	
	public int deleteUserFromDB(String args) 
	{
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
        System.out.println("INSERTED INTO DELETEUSERFROMDB IN USERMANAGERDAOIMPL");
        int i = 0;
        
		try {
			System.out.println("THIS IS ARG IS USERNAME TO DELETE:" + args);
			String stringSQL = "delete from App1User au where au.username= :args";
			Query query = session.createQuery(stringSQL);
			query.setParameter("args", args);
			i = query.executeUpdate();	
			
			session.flush();	
			session.clear();
			transaction.commit();
			
		} catch(HibernateException e) {
			
			System.err.println(e);
			
			if(transaction != null)
				transaction.rollback();
						
		} finally {
			if(session != null)
				session.close();
		}
	
		return i;
	}

	
	public List<App1User> fetchUsersFromDB() 
	{
		
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		List<App1User>  listUser = null;
		
		
		try {
			
			String stringSQL = "select au from App1User au";
			Query query = session.createQuery(stringSQL);
			
			listUser = (List<App1User>)query.list();
					
			session.flush();	
			session.clear();
			transaction.commit();
			
		} catch(HibernateException e) {
			
			System.err.println(e);
			
			if(transaction != null)
				transaction.rollback();
						
		} finally {
			if(session != null)
				session.close();
		}
	
		return listUser;
	}

	

	public App1User findUserByNameLoggining(String userName) {
		
		
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		List<App1User>  listUser = null;
		
		
		try {
			
			String stringSQL = "select au from App1User au where au.username = :userNameArg";
			Query query = session.createQuery(stringSQL);
			query.setParameter("userNameArg", userName);
			
			listUser = (List<App1User>)query.list();
					
			session.flush();	
			session.clear();
			transaction.commit();
			
		} catch(HibernateException e) {
			
			System.err.println(e);
			
			if(transaction != null)
				transaction.rollback();
						
		} finally {
			if(session != null)
				session.close();
		}
		
		return (App1User)listUser.get(0);
	}
	
	
}
