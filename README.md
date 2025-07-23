**IssueCommentingAPI**

**About :**
This API has below three endpoints for adding and retrieving issue comments.
1. POST /api/comments - This is to add new issue comments with message & author name
2. GET /api/comments?issueID={issueID} = This is to retreive the existing issue comment of given issueID
3. GET /api/comments?author={authorName} = This is to retreive the existing issue comment of given authorName

**Build, Deploy and Run Test Service :**

**Prerequisite :**
1. Windows / Linux machine
2. Git version control to download the code
2. Java 8+ Version (with Environment variable setup)
2. Appache maven installed with running setup (Environment variable setup)
3. Postman for testing

**Steps to Build Code :**
1. Clone the code using Git tool
2. Open the command prompt 
2. Run "**mvn clean install**" in the folder path **\IssueCommentingAPI-SpringBoot**

**Deploy the Code :**
1. After "**mvn clean install**" command, the **\IssueCommentingAPI-SpringBoot\target** folder will be generated with library jars and war files.
2. The generated war is a springboot war and self start application war.
3. Now run this command "**java -jar target/IssueCommentingAPI-0.0.1-SNAPSHOT.war**"
4. The application will be started and listening to the port 8080

**Test the service in Postman :**
1. Open the postman
2. Download the postman collections "**IssueCommentingAPI.postman_collection.json**" provided in the git
3. Import the collections into postman
4. Test all the endpoints