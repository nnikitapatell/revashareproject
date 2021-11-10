package project2.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryContainer {

	private static Session ses;
	
	private static SessionFactory sf=
			new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
	
	public static Session getCurrentSession() {
		if(ses==null)
			ses=sf.openSession();
		
		return ses;

	}
	
	

	
	public static SessionFactory getSf() {
		return sf;
	}




	public static void closeSessionAndSessionFactory() {
		ses.close(); 
		ses=null;
		
		sf.close(); 
		
		
	}

}
