package project2.aspect;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import project2.controller.PostController;
import project2.controller.UserController;
import project2.dao.DAOLayer;

@Component("loggerAspect")
@Aspect
public class LoggerAspect {
	
	private static Logger logger = Logger.getLogger(LoggerAspect.class);
	
	{
		logger.setLevel(Level.ALL);
	}
	
	//logging transactions
	@After("execution(* project2.dao.DAOLayer.*(..)) && "
		 + "!execution(* project2.dao.DAOLayer.*Factory*(..)) && "
		 + "!execution(* project2.dao.DAOLayer.*DAO*(..))") 
	public void afterTransactionTask(JoinPoint jp) {
		String newLog = getMethodString(jp);
		logger.info(newLog);
	}
	
	//logging User endpoint calls
	@After("execution(* project2.controller.UserController.*(..)) && "
		+ "!execution(* project2.controller.UserController.*Controller*(..)) && "
		+ "!execution(* project2.controller.UserController.*Service*(..))")
	public void afterUserEndpointTask(JoinPoint jp) {
		String newLog = getMethodString(jp);
		logger.info(newLog);
	}
	
	//logging Post endpoint calls
	@After("execution(* project2.controller.PostController.*(..)) && "
		+ "!execution(* project2.controller.PostController.*Controller*(..)) && "
		+ "!execution(* project2.controller.PostController.*Service*(..))")
	public void afterPostEndpointTask(JoinPoint jp) {
		String newLog = getMethodString(jp);
		logger.info(newLog);
	}
	
	private String getMethodString(JoinPoint jp) {
		String log = "";
		log += jp.getSignature().getName() + ": ";
		
		Object[] args = jp.getArgs();
		for(int i = 0; i < args.length; i++) {
			log += args[i];
			if(i != args.length-1)
				log += ", ";
		}
		
		return log;
	}
}
