CrawleDemo
===============
This is a simple Spring MVC application of the standard [Spring MVC Crawl](Assignment https://github.com/patelbibhuti/Assignment/master) application by the LBG team. 

With this capsule it is possible to run the **CrawlingDemo** as a **web ** application after deploying it to a servlet container like tomcat.

This can be changed:
 	
Feel free to use it. The current version of **CrawlingDemo** can be modified in the pom.xml.

CONFIGURATION
=============
1. Make sure maven is installed and class path is in environment    
MAVEN_HOME=<Maven path installed location>
2. Should have java 8.0 or else change the pom.xml based on Java version

Steps to execute
================
1. Change directory to application folder 
Ex: cd \Workspaces\STS\mvn-app\CrawlingDemo

2. Execute below command 
mvn clean install  
java -jar target\dependency\jetty-runner.jar --port 9090 target\crawl-webapp.war

3.Invoke the URL in browser
http://localhost:9090/CrawlingDemo/

Reasoning and describe any trade offs
=========================================
Included a condition to list out only 100 URL for test  
In production/higher ENV,  The logic for it has to be delete  

what could be done with more time
=================================
1. Performance testing of the app.
2. UI can be design for more interactive.
3. More dynamic approach can be implement for hard coding value & parameter 
4. Utility class can be introduce for common approach
5. java doc can implement in prescribed manner.
6. More fine tune/quality can be done in code base especially in discoverLinks() 
