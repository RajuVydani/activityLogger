///////////////////////////////////////////////////
//HEADER
///////////////////////////////////////////////////

package com.automation.controllers;

import com.automation.idao.IAdminDAO;
import com.automation.idao.IAgentDAO;
import com.automation.model.Admin;
import com.automation.model.Login;
import com.automation.util.AppConstants;
import com.automation.util.cryptograghy;
import com.automation.vo.Agent;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminController {

 
	
	  @Value("${secret.key}")
	    private String secretkey;
 
 
 
     
	public String getSecretkey() {
		return secretkey;
	}
	
	 

	@Autowired
	private IAdminDAO adminDAO;

	private static final Logger LOGGER = Logger.getLogger(AdminController.class);


 
	/**
	 * @return
	 * @throws Exception
	 * Admin Page
	 */
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public ModelAndView admin() throws Exception {
		LOGGER.info("In admin()....1"+ getSecretkey());

	 
		ModelAndView modelAndView = new ModelAndView("admin");
 

		return modelAndView;
	}

	/**
	 * @return
	 * @throws Exception
	 * Admin Page
	 */
	@RequestMapping(value = "/adduser", method = RequestMethod.GET)
	public ModelAndView adduser(Model model) throws Exception {
		LOGGER.info("In adduser()....GET");

	 
		ModelAndView modelAndView = new ModelAndView("adduser");
	
	 Admin admin =new Admin();
	 List<String> userTypeList=getPrevilegesList();
		LOGGER.info("previlegesList "+userTypeList);
		 modelAndView.addObject("userTypeList",userTypeList) ;
		 List<String> locationList=getLocationList();
	 modelAndView.addObject("locationList",locationList) ;
	 List<String> programList=getProgramList();
	 modelAndView.addObject("programList",programList) ;
	 List<String> projectList=getProjectList();
	 modelAndView.addObject("projectList",projectList) ;
	 List<String> subProjectList=getSubProjectList();
	 modelAndView.addObject("subProjectList",subProjectList) ;
		modelAndView.addObject("subProjectList", subProjectList);
		// Login Page
 
		model.addAttribute("adminAtt", admin);
		return modelAndView;
	 
	}
	
 
	
    /* It provides list of employees in model object */  
    @RequestMapping("/viewuser")  
    public ModelAndView viewemp(){  
        List<Admin> list=adminDAO.getuserDetails();  
  
        return new ModelAndView("viewuser","list",list);  
    }  
    
    /* It displays object data into form for the given id.  
     * The @PathVariable puts URL data into variable.*/  
 
	@RequestMapping(value = "/edituser", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam("id") String userId){  
   
    	LOGGER.info("INSIDE edit "+userId);
        List<Admin> userDetails = adminDAO.getuserDetails(userId.trim());

		 String userType="";
		 String LocationId="";
		 String userName="";

				for (Admin userlist : userDetails) {
					
					userType=userlist.getUserType().trim();
					  LocationId=userlist.getLocationId().trim();
				  userName=userlist.getUsername().trim();
					
				}
		    	LOGGER.info("userType "+userType);
		    	LOGGER.info("LocationId "+LocationId);
		    	LOGGER.info("userName "+userName);
				ModelAndView modelAndView = new ModelAndView("usereditform");
				 List<String> userTypeList=getPrevilegesList(userType);
					LOGGER.info("previlegesList "+userTypeList);
					 modelAndView.addObject("userTypeList",userTypeList) ;
					 List<String> locationList=getLocationList(LocationId);
					 
						LOGGER.info("locationList "+locationList);
				 modelAndView.addObject("locationList",locationList) ;
					Map<String, Object> model = new HashMap<String, Object>();

			 
					model.put("currentUserName",userName);
				 
					model.put("currentUserId",userId.trim());
					modelAndView.addObject("displayList", model);
					modelAndView.addObject("adminAtt", new Admin());
					return modelAndView;
					
					
    } 
	
	@RequestMapping(value = "/deleteuser", method = RequestMethod.GET)
    public ModelAndView deleteuser(@RequestParam("id") String userId){  
   
    	LOGGER.info("INSIDE deleteuser "+userId);
    	Admin deleteuser=new Admin();
    	deleteuser.setUserId(userId.trim());
        int deleteStatus = adminDAO.userDeletion(deleteuser);
  	  ModelAndView modelAndView = new ModelAndView("updateUserStatus");
  		Map<String, Object> model = new HashMap<String, Object>();
 
  	
  		if(deleteStatus == 1)
  		{
  			model.put("status", "User deleted Successfully !!!!!!!");
  	 		 int programTransactionDeleteStatus = adminDAO.programTransactionDeletion(deleteuser);
  	 		 if(programTransactionDeleteStatus>=0)
  	 		 {
  	 			LOGGER.info("Deleted from Program Transaction="+programTransactionDeleteStatus); 
  	 		 }
  	 		 
  	 		 
  	 		int projectTransactionDeleteStatus = adminDAO.projectTransactionDeletion(deleteuser);
	 			 if(projectTransactionDeleteStatus>=0)
	  	 		 {
	 				LOGGER.info("Deleted from Project Transaction="+projectTransactionDeleteStatus); 
	 				 
	  	 		 }
	 			 
	 			 int subProjectTransactionDeleteStatus = adminDAO.subProjectTransactionDeletion(deleteuser);
 				 
 				 if(subProjectTransactionDeleteStatus>=0)
  	  	 		 {
 					LOGGER.info("Deleted from Sub Projected Transaction="+subProjectTransactionDeleteStatus); 
 					 
  	  	 		 }
 				 
  		}
  		else
  		{
  			model.put("status", "User deleting Fails!!!!!!!");
  		}
  		
  		modelAndView.addObject("displayList", model);
  		return modelAndView;
					
					
    } 
	
 
	
 
	
	@RequestMapping(value = "/userupdate", method = RequestMethod.POST)
	public ModelAndView userupdate(@ModelAttribute("adminAtt") Admin admin) throws Exception {
		
		LOGGER.info("Inside userupdate()");
     
		Admin adduser=new Admin();
		adduser.setUserId(admin.getUserId().trim());
		adduser.setUsername(admin.getUsername().trim());
		
 
		adduser.setLocationId(admin.getLocationId().trim());
 
		
		List<Admin> previligesDetails = adminDAO.fetchPrevilegeId(admin);

 String previligeId="";

		for (Admin previligelist : previligesDetails) {
			
			previligeId=previligelist.getPriviledgeId();
		}
		
		adduser.setPriviledgeId(previligeId);
		int insertStatus=adminDAO.userUpdation(adduser);
		ModelAndView 
		  modelAndView = new ModelAndView("updateUserStatus");
		Map<String, Object> model = new HashMap<String, Object>();

	
		if(insertStatus == 1)
		{
			model.put("status", "User Updated Successfully !!!!!!!");
		}
		else
		{
			model.put("status", "User Updating Fails!!!!!!!");
		}
		
		modelAndView.addObject("displayList", model);
		return modelAndView;
		
	}
	@RequestMapping(value = "/adduserstatus", method = RequestMethod.POST)
	public ModelAndView adduserstatus(@ModelAttribute("adminAtt") Admin admin) throws Exception {
		LOGGER.info("Inside adduserstatus()");
		int count=adminDAO.checkUserIdExsist(admin);
		ModelAndView 
		  modelAndView = new ModelAndView("adduserstatus");
		if(count == 0)
		{
			LOGGER.info("User Not Exsist");
	 
				cryptograghy encrypt=new cryptograghy();
				LOGGER.info("secretkeyt"+secretkey);
		Admin adduser=new Admin();
		adduser.setUserId(admin.getUserId().trim());
		adduser.setUsername(admin.getUsername().trim());
		
		adduser.setPassword(encrypt.encrypt(admin.getPassword().trim(), secretkey));
		adduser.setLocationId(admin.getLocationId().trim());
		adduser.setAccessLevel(admin.getAccessLevel().trim());
		
		List<Admin> priviledgeDetails =adminDAO.fetchPrevilegeId(admin);
		String priviledgeId="";
		for (Admin priviledgeList : priviledgeDetails) {
			priviledgeId=priviledgeList.getPriviledgeId();
			
		}
		adduser.setPriviledgeId(priviledgeId);
		String accessLevel=admin.getAccessLevel().trim();
		   String status="";
		if(accessLevel.trim().equalsIgnoreCase("all"))
		{
			adduser.setProgramId("");
			adduser.setProjectId("");
			adduser.setSubProjectId("");
			
	        int insertStatus=adminDAO.userInsertion(adduser);
		 
			if(insertStatus == 1)
			{
				LOGGER.info("Data Inserted in User Details");
				
			}
			else
			{
				status="failure";
			}
	 
			
		}
		LOGGER.info("accessLevel="+accessLevel);
	 
		if(accessLevel.trim().equalsIgnoreCase("program"))
		{
			int programCount=adminDAO.checkProgramExsist(admin);
			LOGGER.info("programCount=="+programCount);
			if(programCount == 0)
			{
			List<Admin> projectDetails = adminDAO.getProjectList(admin.getProgramName());

			 String programId="";
         
					for (Admin projectlist : projectDetails) {
						
						programId=projectlist.getProgramId();
					Admin programInsertion=new Admin();
					programInsertion.setProgramId(projectlist.getProgramId());
					programInsertion.setProgramName(admin.getProgramName());
					programInsertion.setProgramDesc(projectlist.getProgramDesc());
					programInsertion.setProgramManagerId(admin.getUserId());
					programInsertion.setProgramManagerName(admin.getUsername());
					programInsertion.setProjectId(projectlist.getProjectId());
					int insertStatus=adminDAO.programInsertion(programInsertion);
					if(insertStatus == 1)
					{
						LOGGER.info("Data Inserted in Program Transaction");
						
					}
					else
					{
						LOGGER.info("Data Insertion in Program Transaction Fails!!!");
						status="failure";
					}
						
					}
			
			adduser.setProgramId(programId);
			adduser.setProjectId("");
			adduser.setSubProjectId("");
			
			int insertStatus=adminDAO.userInsertion(adduser);
			if(!status.trim().equalsIgnoreCase("failure"))
			{
			if(insertStatus == 1)
			{
				LOGGER.info("Data Inserted in User Details");
				
			}
			else
			{
				LOGGER.info("Data Insertion in User Details Fails!!!");
				status="failure";
			}
			
			}
			
		}
			else
			{
				
				Map<String, Object> model = new HashMap<String, Object>();
				 
				model.put("status", "Program is already tagged");
		 
		 modelAndView.addObject("displayList", model);
			return modelAndView;
				
				
			}
		}
		if(accessLevel.trim().equalsIgnoreCase("project"))
		{
			int projectCount=adminDAO.checkProjectExsist(admin);
			LOGGER.info("projectCount=="+projectCount);
			if(projectCount == 0)
			{
			List<Admin> projectDetails = adminDAO.getProjectDetails(admin.getProjectName());

			 String projectId="";
         
					for (Admin projectlist : projectDetails) {
						
					 
					Admin projectInsertion=new Admin();
					projectId=projectlist.getProjectId();
					projectInsertion.setProjectId(projectlist.getProjectId());
					projectInsertion.setProjectName(admin.getProjectName());
					projectInsertion.setProjectDesc(projectlist.getProjectDesc());
					projectInsertion.setProjectManagerId(admin.getUserId());
					projectInsertion.setProjectManagerName(admin.getUsername());
					projectInsertion.setLocationId(projectlist.getLocationId());
					int insertStatus=adminDAO.projectInsertion(projectInsertion);
					if(insertStatus == 1)
					{
						LOGGER.info("Data Inserted in Project Transaction");
						
					}
					else
					{
						LOGGER.info("Data Insertion in Project Transaction Fails!!!");
						status="failure";
					}
						
					}
			
			adduser.setProgramId("");
			adduser.setProjectId(projectId);
			adduser.setSubProjectId("");
			
			int insertStatus=adminDAO.userInsertion(adduser);
			if(!status.trim().equalsIgnoreCase("failure"))
			{
			if(insertStatus == 1)
			{
				LOGGER.info("Data Inserted in User Details");
				
			}
			else
			{
				LOGGER.info("Data Insertion in User Details Fails!!!");
				status="failure";
			}
			
			}
			
		}
			else
			{
				
				Map<String, Object> model = new HashMap<String, Object>();
				 
				model.put("status", "Project is already tagged");
		 
		 modelAndView.addObject("displayList", model);
			return modelAndView;
					
			}
			
		}		
		
		if(accessLevel.trim().equalsIgnoreCase("subproject"))
		{
			List<Admin> subProjectDetails = adminDAO.getSubProjectDetails(admin.getSubProjectName());

			 String subProjectId="";
         
					for (Admin subProjectlist : subProjectDetails) {
						
					 
					Admin subProjectInsertion=new Admin();
					subProjectId=subProjectlist.getSubProjectId();
					subProjectInsertion.setProjectId(subProjectlist.getProjectId());
					subProjectInsertion.setSubProjectId(subProjectlist.getSubProjectId());
					subProjectInsertion.setSubProjectName(admin.getSubProjectName());
					subProjectInsertion.setSubProjectDesc(subProjectlist.getSubProjectDesc());
					subProjectInsertion.setSupervisorId(admin.getUserId());
					subProjectInsertion.setSupervisorName(admin.getUsername());
					subProjectInsertion.setLocationId(subProjectlist.getLocationId());
					int insertStatus=adminDAO.subProjectInsertion(subProjectInsertion);
					if(insertStatus == 1)
					{
						LOGGER.info("Data Inserted in Sub Project Transaction");
						
					}
					else
					{
						LOGGER.info("Data Insertion in Sub Project Transaction Fails!!!");
						status="failure";
					}
						
					}
			
			adduser.setProgramId("");
			adduser.setProjectId("");
			adduser.setSubProjectId(subProjectId);
			
			int insertStatus=adminDAO.userInsertion(adduser);
			if(!status.trim().equalsIgnoreCase("failure"))
			{
			if(insertStatus == 1)
			{
				LOGGER.info("Data Inserted in User Details");
				
			}
			else
			{
				LOGGER.info("Data Insertion in User Details Fails!!!");
				status="failure";
			}
			
			}
			
		}
		
		
 
		
		Map<String, Object> model = new HashMap<String, Object>();

	
		if(!status.trim().equalsIgnoreCase("failure"))
		{
			model.put("status", "User Added Successfully !!!!!!!");
		}
		else
		{
			model.put("status", "User Adding Fails!!!!!!!");
		}
		
		modelAndView.addObject("displayList", model);
		return modelAndView;
		}
		else
		{
			Map<String, Object> model = new HashMap<String, Object>();
 
				model.put("status", "User Id Already Exist");
		 
		 modelAndView.addObject("displayList", model);
			
			return modelAndView;
		}
	 
		
	}
	public List<String> getPrevilegesList()
	{
		
		List<Admin> previligesDetails = adminDAO.fetchUserTypelist();

		List<String> previlegeslist = new ArrayList<String>();

		for (Admin admin : previligesDetails) {

			if (!admin.getUserType().trim().equalsIgnoreCase("")) {
				previlegeslist.add(admin.getUserType().trim());
			}
		}
		return previlegeslist;
	}
 
	public List<String> getPrevilegesList(String currentPriviledge)
	{
		
		List<Admin> previligesDetails = adminDAO.fetchUserTypelist();

		List<String> previlegeslist = new ArrayList<String>();
		previlegeslist.add(currentPriviledge);
		for (Admin admin : previligesDetails) {

			if (!admin.getUserType().trim().equalsIgnoreCase("") && !admin.getUserType().trim().equalsIgnoreCase(currentPriviledge)) {
				previlegeslist.add(admin.getUserType().trim());
			}
		}
		return previlegeslist;
	}
	public List<String> getLocationList()
	{
		
		List<Admin> locationDetails = adminDAO.fetchLocationlist();

		List<String> locationList = new ArrayList<String>();

		for (Admin admin : locationDetails) {

			if (!admin.getLocationName().trim().equalsIgnoreCase("")) {
				locationList.add(admin.getLocationName().trim());
			}
		}
		return locationList;
	}
	
	public List<String> getLocationList(String currentLocation)
	{
		
		List<Admin> locationDetails = adminDAO.fetchLocationlist();

		List<String> locationList = new ArrayList<String>();
		locationList.add(currentLocation.trim());
		for (Admin admin : locationDetails) {

			if (!admin.getLocationName().trim().equalsIgnoreCase("") && !admin.getLocationName().trim().equalsIgnoreCase(currentLocation)) {
				locationList.add(admin.getLocationName().trim());
			}
		}
		return locationList;
	}
	
	
	public List<String> getProgramList()
	{
		
		List<Admin> programDetails = adminDAO.fetchProgramlist();

		List<String> programList = new ArrayList<String>();

		for (Admin admin : programDetails) {

			if (!admin.getProgramName().trim().equalsIgnoreCase("")) {
				programList.add(admin.getProgramName().trim());
			}
		}
		return programList;
	}
	
	
	public List<String> getProjectList()
	{
		
		List<Admin> projectDetails = adminDAO.fetchProjectlist();

		List<String> projectList = new ArrayList<String>();

		for (Admin admin : projectDetails) {

			if (!admin.getProjectName().trim().equalsIgnoreCase("")) {
				projectList.add(admin.getProjectName().trim());
			}
		}
		return projectList;
	}
	
	
	public List<String> getSubProjectList()
	{
		
		List<Admin> subProjectDetails = adminDAO.fetchSubProjectlist();

		List<String> subProjectList = new ArrayList<String>();

		for (Admin admin : subProjectDetails) {

			if (!admin.getSubProjectName().trim().equalsIgnoreCase("")) {
				subProjectList.add(admin.getSubProjectName().trim());
			}
		}
		return subProjectList;
	}
	 /*It saves object into database. The @ModelAttribute puts request data 
     *  into model object. You need to mention RequestMethod.POST method  
     *  because default request is GET*/  
    @RequestMapping(value="/programAllocationStatus",method = RequestMethod.POST)  
    public ModelAndView save(@ModelAttribute("admin") Admin admin){  
        //dao.save(emp);  
        return new ModelAndView("redirect:/viewemp");//will redirect to viewemp request mapping  
    }  
	
    @RequestMapping("/alloaction")  
    public ModelAndView showform(){  
        return new ModelAndView("alloaction","command",new Admin());  
    }  
    
    
	@RequestMapping(value = "/modifyuser", method = RequestMethod.POST)
	public ModelAndView modifyuser() throws Exception {
		LOGGER.info("In admin()....");

	 
		ModelAndView modelAndView = new ModelAndView("adduser");
 

		return modelAndView;
	}
	
	
	@RequestMapping(value = "/deleteuser", method = RequestMethod.POST)
	public ModelAndView deleteuser() throws Exception {
		LOGGER.info("In admin()....");

	 
		ModelAndView modelAndView = new ModelAndView("adduser");
 

		return modelAndView;
	}
}
