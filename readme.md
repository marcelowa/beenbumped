Introduction:
---
Beenbumped is a Java application written as out Final project in the university course Advanced Java.  
It is a small web application that let the user store the details of an incident that happened to him as a driver on the road.

The project actually contains two application:

* An Independent Rest API
* A web application client that consumes and demonstrates the Rest API


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

High Level Flow:
---

    Client->Resource->Dao->Entity->Resource->Client

![high-level-flow](http://copy.com/gyuzG4kbfKE22rM6/Data-Flow-Model.png)

General Usage:
---
for the next examples we will assume that beenbumped is hosted locally in the following uri

    http://localhost/beenbumped

Client
---
navigate using your web browser to the following address:
    
    http://localhost/beenbumped/client
    

Rest API Reference:
---
as noted, this is a Rest API, being that way, the communication is done using the HTTP protocol,
the following examples will demonstrate the HTTP request and the HTTP response of each action of the API.

### User-Save: <a id="usersave">[usersave]</a> ###

#### Headers Parameters:
* Method: POST
* URI Path: /beenbumped/rest/user
* Accept: application/json
* content-type: application/x-www-form-urlencoded; charset=UTF-8

#### Arguments:
* personId (int) [required to edit the user]
* userId (int) [required to edit the user]
* authHash (int) [required to edit the user]
* username (string) [required to create the user]
* password (string) [required to create the user]
* email (string)
* firstName (string)
* lastName (string)
* idNumber (string)
* city (string)
* streetName (string)
* houseNumber (int)
* addressDetails (string)
* zipcode (int)
* phone1 (string)
* phone2 (string)
* insuranceCompany (string)
* insuranceAgentName (string)
* insurancePhone1 (string)
* insurancePhone2 (string)
* insuranceNumber (string)


#### New User example:

Request:

    POST /beenbumped/rest/user HTTP/1.1
    Host: localhost:8080
    Accept:application/json
    Content-Type:application/x-www-form-urlencoded; charset=UTF-8
    Content-Length: 389

    username=name%40example.com&email=name%40example.com&password=qwe123&passwordConfirm=qwe123&firstName=Marcelo&lastName=Waisman&idNumber=01554305&city=Ramat%20Gat&streetName=Hachula&houseNumber=16&addressDetails=Appartment%2012&zipcode=52255&phone1=0508789831&phone2=0505688687&insuranceCompany=icompany&insuranceAgentName=Avishai%20Mizrahi&insurancePhone1=0507330144&insuranceNumber=123123

Response:
The response after saving the user contains the location to the User resource currently saved

    HTTP/1.1 302 Moved Temporarily
    Location: http://localhost:8080/beenbumped/rest/user/4?authHash=*DCE2A329760E1C0F6343BEEFC68524B287F44CB6
    Content-Length: 0


if we redirect to the Location header received in the last example we will get in response the user object in the response

![Create a New User](http://www.websequencediagrams.com/cgi-bin/cdraw?lz=Q2xpZW50LT5Vc2VyUmVzb3VyY2U6IEh0dHAgUmVxdWVzdCBTYXZlIFVzZXIKABkMACMQQ29uc3RydWN0ACYFIChFbnRpdHkpACETRGFvIDoARw9EYW8AdBBTYXZlZABkFACBLQY6IEhUVFAgUmVzcG9uc2UgTGluayB0bwAsCyAAgUgICg)

### User-Get: <a id="userget">[userget]</a> ###
after user creation or after user is already logged in (explained later in user authenticate), a user resource can be retrieved using the following API:

#### Header Parameters:
* Method: GET
* URI Path /beenbumped/rest/user/[userId]?authHash=[authHash]
* Accept: application/json

#### Arguments (square brackets in the URI Path):
* userId (int) [required]
* authHash (string) [required]

#### Example:
Request:

    GET /beenbumped/rest/user/4?authHash=*DCE2A329760E1C0F6343BEEFC68524B287F44CB6 HTTP/1.1
    Accept: application/json

Response:

    HTTP/1.1 200 OK
    Content-Type: application/json

    {
        "personId":38,
        "email":"name@example.com",
        "firstName":"Marcelo",
        "lastName":"Waisman",
        "idNumber":"01554305",
        "city":"Hachula",
        "streetName":"Ramat Gat",
        "houseNumber":16,
        "addressDetails":"Appartment 12",
        "zipcode":52255,
        "phone1":"0508789831",
        "phone2":"0505688687",
        "insuranceCompany":"icompany",
        "insuranceAgentName":"Avishai Mizrahi",
        "insurancePhone1":"0507330144",
        "insurancePhone2":"",
        "insuranceNumber":"123123",
        "userId":4,
        "username":"name@example.com",
        "authHash":"*DCE2A329760E1C0F6343BEEFC68524B287F44CB6"
    }

![Get an Exisiting User Data](http://www.websequencediagrams.com/cgi-bin/cdraw?lz=Q2xpZW50LT5Vc2VyUmVzb3VyY2U6IEh0dHAgUmVxdWVzdCBVc2VyCgAUDAAoBkRhbzogQ2hlY2sgaWYAIAUgaXMgQXV0aG9yaXplZCB0byBnZQAjHCA6IGdldEJ5SWQAWgVEYW8AeBByZXR1cm4AbhQAgTIGOiBIVFRQIFJlc3BvbnNlAIEABkVudGl0eSAoWE1ML0pTT04p)

### User-Authenticate: <a id="userauth">[userauth]</a> ###
As noted above, an already existing user can receive the User object resource using the authenticate API.

#### Header Parameters:
* Method: GET
* URI Path /beenbumped/rest/user?username=[username]&password=[password]
* Accept: application/json

#### Arguments (square brackets in the URI Path):
* username(string) [required]
* password(string) [required]

#### example:

Request:

    GET /beenbumped/rest/user?username=name@example.com&password=qwe123 HTTP/1.1
    Accept: application/json

Response: see [User-Get](#userget)

![Authenticate (login) with an Existing User](http://www.websequencediagrams.com/cgi-bin/cdraw?lz=Q2xpZW50LT5Vc2VyUmVzb3VyY2U6IEhUVFAgUmVxdWVzdCBBdXRoZW50aWNhdGUKABwMADAGRGFvOgASEkRhbwBFEFVzZXIgRW50aXR5ADgPAIB_BgBuCXNwb25zZQAkDCAoWE1ML0pzb24p)

### Incident-Save: <a id="incidentsave">[incidentsave]</a> ###


#### Header Parameters:
* Method: POST
* URI Path /beenbumped/rest/incident
* Accept: application/json
* content-type: application/x-www-form-urlencoded; charset=UTF-8

#### Arguments:
* userId(int) [required]
* authHash(string) [required]
* incidentId(int) [if exist (and greater than 0), edit the existing one]
* date(string) [formatted as yyyy-mm-dd hh:mm]
* notes(string)
* location(string)
* vehicleLicensePlate(string)
* vehicleBrand(string)
* vehicleModel(string)
* driverIdNumber(string)
* driverFirstName(string)
* driverLastName(string)
* driverPhone1(string)
* driverPhone2(string)
* driverInsuranceCompany(string)
* driverInsuranceAgentName(string)
* driverInsurancePhone1(string)
* driverInsurancePhone2(string)
* driverInsuranceNumber(string)
* driverEmail(string)
* ownerIdNumber(string)
* ownerFirstName(string)
* ownerLastName(string)
* ownerPhone1(string)
* ownerPhone2(string)
* ownerInsuranceCompany(string)
* ownerInsuranceAgentName(string)
* ownerInsurancePhone1(string)
* ownerInsurancePhone2(string)
* ownerInsuranceNumber(string)
* ownerEmail(string)


#### example:

Request:

    POST /beenbumped/rest/incident HTTP/1.1
    Host: localhost:8080
    Accept: application/json
    Content-Type: application/x-www-form-urlencoded; charset=UTF-8
    Content-Length: 812
    
    incidentId=-1&userId=4&authHash=*DCE2A329760E1C0F6343BEEFC68524B287F44CB6&notes=a%20note&vehicleLicensePlate=123123&vehicleBrand=Toyota%20Corola&vehicleModel=2006&driverFirstName=Jacob&driverLastName=Pines&driverIdNumber=111222333&driverPhone1=049886075&driverPhone2=049980270&driverInsuranceCompany=Migdal&driverInsuranceAgentName=Eran%20Blueshtein&driverInsurancePhone1=0549771333&driverInsurancePhone2=0549771332&driverInsuranceNumber=999888999&driverEmail=eran%40example.com&ownerFirstName=Arnon&ownerLastName=Klineman&ownerIdNumber=555666777&ownerPhone1=0505050500&ownerPhone2=0505050505&ownerInsuranceCompany=OtherIns&ownerInsuranceAgentName=Agent%20Smith&ownerInsurancePhone1=03-5555555&ownerInsurancePhone2=03-9000000&ownerInsuranceNumber=77665544&ownerEmail=smith%40example.com&date=2013-08-17%2012%3A20

Response:

    HTTP/1.1 302 Moved Temporarily
    Location: http://localhost:8080/beenbumped/rest/incident/19?userId=4&authHash=*DCE2A329760E1C0F6343BEEFC68524B287F44CB6
    Content-Length: 0

![Create (or Edit an Existing) Incident](http://www.websequencediagrams.com/cgi-bin/cdraw?lz=Q2xpZW50LT5JbmNpZGVudFJlc291cmNlOiBIdHRwIFJlcXVlc3QgU2F2ZSAAHAgKAB0QLT5Vc2VyRGFvOiBDaGVjayBpZiBVc2VyIGlzIEF1dGhvcml6ZWQgdG8AJyEAdRJDb25zdHJ1Y3QAdgkoRW50aXR5KQAkG0RhbyA6AIEeF0RhbwCBWxRTYXZlZACBRxwAgiQGOiBIVFRQIFJlc3BvbnNlIExpbmsAgVEIADUKIACCPwg)

### Incident-Get: <a id="incidentget">[incidentget]</a> ###
To get an existing incident a GET request can be made in the following format

#### Header Parameters:
* Method: GET
* URI Path http://localhost:8080/beenbumped/rest/incident/[incidentId]?userId=[userId]&authHash=[authHash]
* Accept: application/json

#### Arguments (square brackets in the URI Path):
* incidentId(int) [required]
* userId(int) [required]
* authHash(int) [required]

#### example:

Request:

    GET /beenbumped/rest/incident/19?userId=4&authHash=*DCE2A329760E1C0F6343BEEFC68524B287F44CB6 HTTP/1.1
    Accept: application/json

Response:

    HTTP/1.1 200 OK
    Content-Type: application/json
    
    {
        "incidentId":19,
        "userId":4,
        "notes":null,
        "location":null,
        "vehicleLicensePlate":"123123",
        "vehicleBrand":"Toyota Corola",
        "vehicleModel":"2006",
        "driver":{"personId":39,
        "email":"eran@example.com",
        "firstName":"Jacob",
        "lastName":"Pines",
        "idNumber":"111222333",
        "city":null,
        "streetName":null,
        "houseNumber":-1,
        "addressDetails":null,
        "zipcode":-1,
        "phone1":"049886075",
        "phone2":"049980270",
        "insuranceCompany":"Migdal",
        "insuranceAgentName":"Eran Blueshtein",
        "insurancePhone1":"0549771333",
        "insurancePhone2":"0549771332",
        "insuranceNumber":"999888999"},
        "owner":{"personId":40,
        "email":"smith@example.com",
        "firstName":"Arnon",
        "lastName":"Klineman",
        "idNumber":"555666777",
        "city":null,
        "streetName":null,
        "houseNumber":-1,
        "addressDetails":null,
        "zipcode":-1,
        "phone1":"0505050500",
        "phone2":"0505050505",
        "insuranceCompany":"OtherIns",
        "insuranceAgentName":"Agent Smith",
        "insurancePhone1":"03-5555555",
        "insurancePhone2":"03-9000000",
        "insuranceNumber":"77665544"},
        "date":"2013-08-17 12:20"
    }

![Get an Exisiting Incident Data](http://www.websequencediagrams.com/cgi-bin/cdraw?lz=Q2xpZW50LT5JbmNpZGVudFJlc291cmNlOiBIdHRwIFJlcXVlc3QgABcICgAYEC0-VXNlckRhbzogQ2hlY2sgaWYgVXNlciBpcyBBdXRob3JpemVkIHRvIGdlACodAHkIRGFvIDogZ2V0QnlJZABmCURhbwCBEBRyZXR1cm4AgQIcAIFaBjogSFRUUCBSZXNwb25zZQCBRgkgRW50aXR5IChYTUwvSlNPTik)

### Incident-History: <a id="incidenthistory">[incidenthistory]</a> ###
an authenticated user can retrieve his existing incidents list with the following API

#### Header Parameters:
* Method: GET
* URI Path /beenbumped/rest/incident/history?userId=[userId]&authHash=[authHash]
* Accept: application/json

#### Arguments (square brackets in the URI Path):
* userId(int) [required]
* authHash(string) [required]
* linesInPage (int) [optional limit the number of incident results to that number]
* page (int) [optional if linesInPage is set page will retrieve {linesInPage} results starting from {linesInPage}*{page}]

#### example:

Request:

    GET /beenbumped/rest/incident/history?authHash=*DCE2A329760E1C0F6343BEEFC68524B287F44CB6&userId=4 HTTP/1.1
    Accept: application/json

Response:

    HTTP/1.1 200 OK
    Content-Type: application/json
    
    {
        incidents:[ /* an array of incident(s) same structure as in Incident-Get */ ],
        "totalLines":10 //total incidents
    }

![Get User Incidents History (Incident Collection)](http://www.websequencediagrams.com/cgi-bin/cdraw?lz=Q2xpZW50LT5JbmNpZGVudFJlc291cmNlOiBIdHRwIFJlcXVlc3QgSGlzdG9yeSAob2YgaQAkB3MpCgAmEC0-VXNlckRhbzogQ2hlY2sgaWYgVXNlciBpcyBBdXRob3JpemVkIHRvIGdldCAAbAhzADUTAIEICERhbyA6IGdldACBGQgAgQEJYnkgdXNlcklkAH0KRGFvAIE2FHJldHVybgBmCVBhZ2UgKENvbGxlY3Rpb24gb2YAgQIJIEVudGl0aWUAgUcVAIImBjogSFRUUCBSZXNwb25zZQBJDgA6BXkgKFhNTC9KU09OKQo)





