Prerequisites:
---
* A working Java SE 1.7 installation
* A working Apache Tomcat 7 web server installation <http://tomcat.apache.org/download-70.cgi>
* A working MySQL server
* A working Ant installation <http://ant.apache.org/bindownload.cgi>

Installation:
---

1) edit the configuration to reflect your mysql and tomcat needs, the configuration is located at src/config.properties file

    ...
    db.user=username
    db.password=password
    db.host=host
    ...
    tomcat.dir=tomcat-location-in-the-file-system
    
2) execute ant db to build the database structure and procedures

    ant db    
    
3) execute ant to build the beenbumped.war file:

    ant

beenbumped.war file will be located in the following location:

    dist/beenbumped.war 

4) copy beenbumped.war file into your tomcat webapps folder



