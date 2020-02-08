package com.wipro.joydeep.model;

public class RequestObject 
      {
	// Added to track changes
         private String itemName[];
         
         public RequestObject()
         {
        	 
         }

		public RequestObject(String itemName[]) {
			super();
			this.itemName = itemName;
		}

		public String[] getItemName() {
			return itemName;
		}

		public void setItemName(String itemName[]) {
			this.itemName = itemName;
		}

		@Override
		public String toString() {
			String output="";
			for(String i:itemName)
			{
				output=output+i+"\n";
			}
			return output;
		}
         
      }
