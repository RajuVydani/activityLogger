package com.automation.idao;

import java.util.List;

import com.automation.model.Admin;
import com.automation.vo.Agent;

public interface IAdminDAO {

	/**
	 * @param admin
	 * @return
	 */
	public List<Admin> getPreviledgeId(Admin admin); 
	
	/**
	 * @param admin
	 * @return
	 */
	public List<Admin> getMntDataSubProLvl(Admin admin);
	
	/**
	 * @param admin
	 * @return
	 */
	public List<Admin> getMntCountSubProLvl(Admin admin);
	
	/**
	 * @param admin
	 * @return
	 */
	public List<Admin> getSupervisor(Admin admin) ;
	
	/**
	 * @param admin
	 * @return
	 */
	public List<Admin> getMntDataSpecificSubProLvl(Admin admin) ;
	
	
	/**
	 * @param admin
	 * @return
	 */
	public List<Admin> getMntCountSpecificSubProLvl(Admin admin);
	
	
	/**
	 * @param admin
	 * @return
	 */
	public List<Admin> getMntDataSpecificProLvl(Admin admin) ;
	
	
	/**
	 * @param admin
	 * @return
	 */
	public List<Admin> getMntCountSpecificProLvl(Admin admin);

	
	/**
	 * @param admin
	 * @return
	 */
	public List<Admin> getMntDataLoc(Admin admin) ;
	
	
	/**
	 * @param admin
	 * @return
	 */
	public List<Admin> getMntCountLoc(Admin admin);
	
	/**
	 * @param admin
	 * @return
	 */
	public List<Admin> getProjectId(Admin admin);


}
