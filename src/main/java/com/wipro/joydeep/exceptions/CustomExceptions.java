package com.wipro.joydeep.exceptions;

public class CustomExceptions extends RuntimeException
      {
               private String exceptionMessage;
               
               public CustomExceptions(String exceptionMessage)
               {
            	   this.exceptionMessage=exceptionMessage;
            	   System.out.println("CustomExceptions"+this.exceptionMessage);
               }

			
               
               
      }
