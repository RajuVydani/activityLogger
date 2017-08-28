package com.automation.vo;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ActivityCodes {
	
	private String breaks;
	private String meals;
	private String huddle;
	private String wellness_support;
	private String coaching;
	private String team_meeting;
	private String fb_training;
	private String non_fb_training;
	private String total_shrinkage;
	
	public String getBreaks() {
		return breaks;
	}
	public void setBreaks(String breaks) {
		this.breaks = breaks;
	}
	public String getMeals() {
		return meals;
	}
	public void setMeals(String meals) {
		this.meals = meals;
	}
	public String getHuddle() {
		return huddle;
	}
	public void setHuddle(String huddle) {
		this.huddle = huddle;
	}
	public String getWellness_support() {
		return wellness_support;
	}
	public void setWellness_support(String wellness_support) {
		this.wellness_support = wellness_support;
	}
	public String getCoaching() {
		return coaching;
	}
	public void setCoaching(String coaching) {
		this.coaching = coaching;
	}
	public String getTeam_meeting() {
		return team_meeting;
	}
	public void setTeam_meeting(String team_meeting) {
		this.team_meeting = team_meeting;
	}
	public String getFb_training() {
		return fb_training;
	}
	public void setFb_training(String fb_training) {
		this.fb_training = fb_training;
	}
	public String getNon_fb_training() {
		return non_fb_training;
	}
	public void setNon_fb_training(String non_fb_training) {
		this.non_fb_training = non_fb_training;
	}
	public String getTotal_shrinkage() {
		return total_shrinkage;
	}
	public void setTotal_shrinkage(String total_shrinkage) {
		this.total_shrinkage = total_shrinkage;
	}

}
