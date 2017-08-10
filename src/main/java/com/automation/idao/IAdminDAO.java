package com.automation.idao;

import java.util.List;

import com.automation.model.Admin;
import com.automation.vo.Agent;

public interface IAdminDAO {

 

	public List<Admin> fetchUserTypelist();
	public List<Admin> fetchLocationlist();
	public List<Admin> fetchProgramlist();
	public List<Admin> fetchProjectlist();
	public List<Admin> fetchSubProjectlist();
	public int userInsertion(Admin admin);
	public List<Admin> fetchPrevilegeId(Admin admin);
	public List<Admin> getuserDetails();
	public List<Admin> getuserDetails(String emailId);
	public List<Admin> getUserType(Admin admin);
	public int userUpdation(Admin admin);
	public int userDeletion(Admin admin);
	public List<Admin> FetchEmailIds(String criteria);
	public List<Admin> getProjectList(String programName);
	public int programInsertion(Admin admin);
	public List<Admin> getProjectDetails(String projectName);
	public int projectInsertion(Admin admin);
	public int subProjectInsertion(Admin admin);
	public List<Admin> getSubProjectDetails(String subProjectName);
	public int programTransactionDeletion(Admin admin);
	public int projectTransactionDeletion(Admin admin);
	public int subProjectTransactionDeletion(Admin admin);
	public int checkUserIdExsist(Admin admin);
	public int checkProgramExsist(Admin admin);
	public int checkProjectExsist(Admin admin);
	

}
