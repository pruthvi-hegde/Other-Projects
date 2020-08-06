package com.vmware.dm.controller;


import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.vmware.dm.model.DMConnectionResponse;
import com.vmware.dm.service.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/DMV")
public class DMController {
	
	private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	@SuppressWarnings({ "unused", "unchecked" })
	@RequestMapping(value="/connectionSetup", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String connectionSetup(@RequestBody String authDetails) throws JSONException 
    {
		String response = GenericDBSetup.DBSetup(authDetails);
		return response;
    }
	
	@SuppressWarnings({ "unused", "unchecked" })
	@RequestMapping(value="/updateHeadersToH2", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String headersMapping(@RequestBody String authDetails) throws JSONException
    {
		String status =  H2DBSetUp.UpdateColumnHeader(authDetails);
		return gson.toJson("Updated Headers");		
			
    }
	
	@SuppressWarnings({ "unused", "unchecked" })
	@RequestMapping(value="/updateRecordsToH2", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String RecordsMapping(@RequestBody String authDetails) throws JSONException
    {
		H2DBSetUp.UpdateRecords(authDetails);

		return gson.toJson("Updated Records into H2");		
			
    }
	
	@SuppressWarnings({ "unused", "unchecked" })
	@RequestMapping(value="/compareToTarget", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String compareToTarget(@RequestBody String authDetails) throws JSONException, ClassNotFoundException
    {
		String result = GenericDBSetup.TargetDBSetup(authDetails);

		return result;		
			
    }
	
}
