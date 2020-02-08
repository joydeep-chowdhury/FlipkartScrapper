package com.wipro.joydeep.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.joydeep.exceptions.CustomExceptions;
import com.wipro.joydeep.model.RequestObject;
import com.wipro.joydeep.service.FlipkartService;

@RestController
public class FlipkartController {
	// Added to track changes
	@Autowired
	private FlipkartService fs;

	@RequestMapping(value = "/getitems", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<List<Map<String, String>>> getItems(@RequestBody RequestObject requestobj)
		 {
		String items[] = requestobj.getItemName();
		List<CompletableFuture<List<Map<String,String>>>> completable_future_list=new ArrayList<CompletableFuture<List<Map<String,String>>>>();
        for(String item:items)
        {
        	
        	completable_future_list.add(fs.performAction(item));
        	
        	
        }
        CompletableFuture.allOf(completable_future_list.toArray(new CompletableFuture[completable_future_list.size()]));
        List<List<Map<String,String>>> finalData=new ArrayList<List<Map<String,String>>>(); 
        
        completable_future_list.forEach(cfl->{
        	try {
				finalData.add(cfl.get());
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				List<Map<String,String>> errorList=new ArrayList<Map<String,String>>();
				Map<String,String> messageMap=new HashMap<String,String>();
				messageMap.put("Exception Message", "Interrupted Exception or Execution Exception has occured");
				errorList.add(messageMap);
				finalData.add(errorList);
				//throw new CustomExceptions("Interrupted Exception or Execution Exception has occured");
				
			}
        });
        
		
		
		return finalData;
	}
	
	
	/*
	 * @RequestMapping(value = "/getitems2", method = RequestMethod.POST, consumes =
	 * MediaType.APPLICATION_JSON_VALUE) public List<Map<String, String>>
	 * getItems2(@RequestBody RequestObject requestobj) {
	 * System.out.println(requestobj.getItemName()); return
	 * fs.performNewAction(requestobj.getItemName()); }
	 */
	
}
