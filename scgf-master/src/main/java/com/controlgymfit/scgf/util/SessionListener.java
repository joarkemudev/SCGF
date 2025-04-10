package com.controlgymfit.scgf.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {
  private static SessionListener instance;
  private static final Map<String, HttpSession> sessions = new ConcurrentHashMap<String, HttpSession>();


  
  public static SessionListener getInstance(){
	  if(instance == null){
		  instance = new SessionListener();
	  }
	  return instance;
  }
  
	
  @Override
  public void sessionCreated(HttpSessionEvent event) {
	 System.out.println(sessions);
	 HttpSession session = event.getSession();
     sessions.put(session.getId(), session);
     //printSessionTable();
     System.out.println("sessionCreated...");
  }

  @Override
  public void sessionDestroyed(HttpSessionEvent event) {
	sessions.remove(event.getSession().getId());
	System.out.println("sessionDestroyed...");
  }	
 
  public synchronized void invalidaSessiones(){
	  System.out.println(sessions);
	  
	  
	  if(sessions != null){
		  for(HttpSession session : sessions.values()){
			 session.invalidate();
		  } 
	  }
	  System.out.println("Todas las sessiones han sido cerradas.");
  }
}