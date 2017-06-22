//For every 3 seconds, it will check whether the given urls are idle for last 30 seconds.

// Interval (in seconds) to update timer
var UPDATE_INTERVAL = 3;
var prd_url='onecognizant.cognizant.com';
// Types to view data
var TYPE = {
  today: "today",
  average: "average",
  all: "all"
};
// Current viewing mode
var mode = TYPE.today;

setDefaults();
// Set default settings
function setDefaults() { 
 
  // Set number of days Web Timer has been used
  if (!localStorage["num_days"]) {
    localStorage["num_days"] = 1;
  }
  // Set date
  if (!localStorage["date"]) {
    localStorage["date"] = new Date().toLocaleDateString();
  }
  // Set domains seen before
  if (!localStorage["domains"]) {
    localStorage["domains"] = JSON.stringify({});
  }		  
	if (!localStorage["domainshist"]) {
    localStorage["domainshist"] = JSON.stringify({});
	localStorage["domainshist"] = JSON.stringify('');
  }
  if (!localStorage["IDLE"]) {
    localStorage["IDLE"] = JSON.stringify({});
	localStorage["IDLE"] = JSON.stringify('');
  }
  if (!localStorage["swipeout"]) {
    localStorage["swipeout"] ='';
 
  }
 
  if (!localStorage["swipein"]) {
     localStorage["swipein"] = '';	 
  }
    if (!localStorage["productive_hrs"]) {
     localStorage["productive_hrs"] = '';	 
  }
 
  if (!localStorage["total"]) {
    localStorage["total"] = JSON.stringify({
    today: 0,
    all: 0
    });
  }
  // Limit how many sites the chart shows
  if (!localStorage["chart_limit"]) {
    localStorage["chart_limit"] = 7;
  }
 
  if (!localStorage["other"]) {
    localStorage["other"] = JSON.stringify({
      today: 0,
      all: 0
    });
  }
}
 
function combineEntries(threshold) {
  var domains = JSON.parse(localStorage["domains"]);
  var other = JSON.parse(localStorage["other"]);
  // Don't do anything if there are less than threshold domains
  if (Object.keys(domains).length <= threshold) {
    return;
  }
  // Sort the domains by decreasing "all" time
  var data = [];
  for (var domain in domains) {
    var domain_data = JSON.parse(localStorage[domain]);
    data.push({
      domain: domain,
      all: domain_data.all
    });
  }
  data.sort(function (a, b) {
    return b.all - a.all;
  });
  // Delete data after top threshold and add it to other
  for (var i = threshold; i < data.length; i++) {
    other.all += data[i].all;
    var domain = data[i].domain;
    delete localStorage[domain];
    delete domains[domain];
  }
  localStorage["other"] = JSON.stringify(other);
  localStorage["domains"] = JSON.stringify(domains);
}
 

// Extract the domain from the url
// e.g. http://google.com/ -> google.com
function extractDomain(url) {
  var re = /:\/\/(www\.)?(.+?)\//;
  return url.match(re)[2];
}

function inBlacklist(url) {
  if (!url.match(/^http/)) {
    return true;
  }
 
 
  return false;
}

// Update the data
function updateData() {
  // Only count time if Chrome has focus
  chrome.windows.getLastFocused(function (window) {
    if (window === undefined) {
      return;
    }
    if (window.focused) {
      // Only count time if system has not been idle for 30 seconds
      chrome.idle.queryState(30, function (state) {
	  
	  
	  
	  
	  
	  
        if (state === "active") {
          chrome.tabs.getSelected(null, function (tab) {
            if (tab === undefined) {
              return;
            }
      
            if (!inBlacklist(tab.url)) {
              
                var currentdate = new Date();
    
		  var currentdate = new Date();
                var datetime = currentdate.getFullYear()  + "-"
                              + (currentdate.getMonth()+1)  + "-"
                              + currentdate.getDate() + " " 
                              + currentdate.getHours() + ":" 
                              + currentdate.getMinutes() + ":"
                              + currentdate.getSeconds();
              	
		var time=datetime.split(" ");
			var date1=time[0].split("-");
				var year=date1[0];
		var month=date1[1];
		var day=date1[2];
			if(month.length == 1)
		{
		month="0"+month;
		}
		if(day.length == 1)
		{
		day="0"+day;
		}
	 
		var time1=time[1].split(":");
		var hh=time1[0];
		var mm=time1[1];
		var ss=time1[2];
		if(hh.length == 1)
		{
		hh="0"+hh;
		}
		if(mm.length == 1)
		{
		mm="0"+mm;
		}
		if(ss.length == 1)
		{
		ss="0"+ss;
		}
		
 
		var currenttime_formatted=month+"/"+day+"/"+year+" "+hh+":"+mm+":"+ss;
		 
		var loggedouttime=localStorage["swipeout"];
	 
		var loggedouttime_formatted='';
			 
			if(loggedouttime.trim() != '')
{			
	 
		var time1=loggedouttime.split(" ");
		var date2=time1[0].split("-");
	
		var year1=date2[0];
		var month1=date2[1];
		var day1=date2[2];
			if(month1.length == 1)
		{
		month1="0"+month1;
		}
		if(day1.length == 1)
		{
		day1="0"+day1;
		}
				 

		var time11=time1[1].split(":");
		var hh1=time11[0];
		var mm1=time11[1];
		var ss1=time11[2];
		if(hh1.length == 1)
		{
		hh1="0"+hh1;
		}
		if(mm1.length == 1)
		{
		mm1="0"+mm1;
		}
		if(ss1.length == 1)
		{
		ss1="0"+ss1;
		}
		
	 loggedouttime_formatted=month1+"/"+day1+"/"+year1+" "+hh1+":"+mm1+":"+ss1;
		
		}
	 	 
		if(loggedouttime_formatted.trim() != '' && currenttime_formatted.trim() != '')
		{
						     	var date1 = new Date(loggedouttime_formatted);
var date2 = new Date(currenttime_formatted);

var diff = date2.getTime() - date1.getTime();

var msec = diff; 
 
var username =localStorage["loggedinuser"];
username='selva';
if(username.trim() != '')
{
if( msec > 14400000)
{

 

 localStorage["domainshist"] = JSON.stringify('');
localStorage["IDLE"] = JSON.stringify('');
  localStorage["swipeout"] ='';
    localStorage["swipein"] ='';

localStorage["productive_hrs"] ='';

var username='selvamani';
 
 var emailid='selvamani.t@cognizant.com';	
		var logintime=datetime;
var logouttime=datetime;
		var productivityhrs= '0'; 
 
	 
		 if(username == '' || username == 'undefined' || username == null || emailid == '' || emailid == 'undefined' || emailid == null || logintime ==''  || logintime == 'undefined' || logintime == null)
{

 
}
else
{
 
		 
		if(productivityhrs == '' || productivityhrs == 'undefined' || productivityhrs == null)
{
productivityhrs='0';
} 
 

	
	
var parameters='storeagentdata/'+username+'/'+emailid+'/'+productivityhrs+'/'+logintime+'/'+logouttime;
 
 var xmlhttp = new XMLHttpRequest();
 
 
            xmlhttp.onreadystatechange = function() {
                if (xmlhttp.readyState == XMLHttpRequest.DONE ) {
      
                   if (xmlhttp.status == 200) {
                    
     
                       var response=xmlhttp.responseText;
    
                       }
                  
                   else if (xmlhttp.status == 400) {
 
                   }
                   else {
              
                   }
                }
            };
        
            xmlhttp.open("POST", 'http://localhost:8080/TimeTrackerWeb/rest/agent/'+parameters, true);
        
            xmlhttp.send();
 

}




			 localStorage["productive_hrs"]='';


var domains = JSON.parse(localStorage["domains"]);
    for (var domain in domains) {
      var domain_data = JSON.parse(localStorage[domain]);
      domain_data.today = 0;
      localStorage[domain] = JSON.stringify(domain_data);
    }
    // Reset total for today
    var total = JSON.parse(localStorage["total"]);
    total.today = 0;
    localStorage["total"] = JSON.stringify(total);
    // Combine entries that are not part of top 500 sites
    combineEntries(500);
    // Keep track of number of days web timer has been used
    localStorage["num_days"] = parseInt(localStorage["num_days"]) + 1;
    // Update date
    localStorage["date"] = todayStr;

 

}
}
}
 
              			 localStorage["swipeout"] =datetime; 	
              				
              				
              				  var todayStr = new Date().toLocaleDateString();
                var saved_day = localStorage["swipein"];
 
                if (saved_day.trim() == '') {
                  localStorage["swipein"] = datetime;
                
                }
                        var domain = extractDomain(tab.url);
              			    var domainshist1 = JSON.parse(localStorage["domainshist"]);
              				var splt=domainshist1.split(",");
              				var exist='';              				
              				
              				for(var i=0;i<splt.length;i++)              					
              					{
              						if(domain == splt[i] )
              						{              							
              						exist='yes';	
              							
              						}              						
              					}
              			              					
              			if(exist != 'yes')
              			{				
              		  if(domainshist1 == '')
              		  {
              			 localStorage["domainshist"] = JSON.stringify(domain); 
              			  
              		  }
              		  else{
              			  
              			  localStorage["domainshist"] = JSON.stringify(domainshist1+","+domain);
              			  
              		  }
              			}
                            // Add domain to domain list if not already present
                            var domains = JSON.parse(localStorage["domains"]);
                            if (!(domain in domains)) {
                  
                              domains[domain] = 1;
                              localStorage["domains"] = JSON.stringify(domains);
                            }
                            var domain_data;
                            if (localStorage[domain]) {
                              domain_data = JSON.parse(localStorage[domain]);
                            } else {
                              domain_data = {
                                today: 0,
                                all: 0
                              };
                            }
						 
							if(domain == prd_url)
							{
					 
							 localStorage["productive_hrs"]=domain_data.today;
							
							}
							
                            domain_data.today += UPDATE_INTERVAL;
                            domain_data.all += UPDATE_INTERVAL;
                            localStorage[domain] = JSON.stringify(domain_data);
                            // Update total time
                            var total = JSON.parse(localStorage["total"]);
                            total.today += UPDATE_INTERVAL;
                            total.all += UPDATE_INTERVAL;
                            localStorage["total"] = JSON.stringify(total);
                            // Update badge with number of minutes spent on
                            // current site
                            var num_min = Math.floor(domain_data.today / 60).toString();
                            if (num_min.length < 4) {
                              num_min += "m";
                            }
                            chrome.browserAction.setBadgeText({
                              text: num_min
                            });
            } else {
              // Clear badge
              chrome.browserAction.setBadgeText({
                text: ""
              });
            }
          });
        }
      });
    }
  });
  

}
// Update timer data every UPDATE_INTERVAL seconds
setInterval(updateData, UPDATE_INTERVAL * 1000);

chrome.runtime.onMessage.addListener(function(request, sender, sendResponse) {
 
  if (request.user == "UserName")
  {
   
    localStorage["loggedinuser"] =  request.name;
    localStorage["emailId"] =  request.email;

    sendResponse({acknowledge: "userName & EmailId received"});
	}
	
 
});
chrome.windows.onRemoved.addListener(function(windowid) {
 
   
     	var username='selvamani';
 
 var emailid='selvamani.t@cognizant.com';	
		var logintime=localStorage["swipein"];
var logouttime=localStorage["swipeout"];
		var productivityhrs= localStorage["productive_hrs"]; 
 var IDLE = JSON.parse(localStorage["IDLE"]);	 
 
 
		 if(username == '' || username == 'undefined' || username == null || emailid == '' || emailid == 'undefined' || emailid == null || logintime ==''  || logintime == 'undefined' || logintime == null)
{

 
}
else
{
 
		 
		if(productivityhrs == '' || productivityhrs == 'undefined' || productivityhrs == null)
{
productivityhrs='0';
} 
 
 		if(IDLE == '' || IDLE == 'undefined' || IDLE == null)
{
IDLE='';
} 
var parameters='storeagentdata/'+username+'/'+emailid+'/'+productivityhrs+'/'+logintime+'/'+logouttime+'/'+IDLE;
 
 var xmlhttp = new XMLHttpRequest();
 
 
            xmlhttp.onreadystatechange = function() {
                if (xmlhttp.readyState == XMLHttpRequest.DONE ) {
      
                   if (xmlhttp.status == 200) {
                     localStorage["IDLE"] = JSON.stringify('');	
                 
                       var response=xmlhttp.responseText;
              
                       }
                  
                   else if (xmlhttp.status == 400) {
 
                   }
                   else {
      
                   }
                }
            };
        
            xmlhttp.open("POST", 'http://localhost:8080/TimeTrackerWeb/rest/agent/'+parameters, true);
        
            xmlhttp.send();
         }
   
});