package com.wipro.joydeep.exceptions;

public class CustomExceptions extends RuntimeException
      {
	// Added to track changes
               private String exceptionMessage;
               
               public CustomExceptions(String exceptionMessage)
               {
            	   this.exceptionMessage=exceptionMessage;
            	   System.out.println("CustomExceptions"+this.exceptionMessage);
               }

			
               
               
      }
