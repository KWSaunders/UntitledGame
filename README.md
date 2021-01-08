README

**Untitled Game**

**Developers:**  
Kyle Saunders (https://github.com/KWSaunders)  
Tyler Hayden (https://github.com/tkHayden)  

**Updates:**  

**1/7/2021**  
AccountCreation.java - Added name/password restrictions for account creation.  
(user already exists, username filter, valid username chars, username length and password length)  
LoginManager.java - Handled logging into account already logged in.  
(kicks other session and logins via new session)  

**1/8/2021**  
Fixed JSON data storing during account creation.  
(may require deleting any accounts in database with null currently assigned to data field)   
Added periodic account saving. (currently every 5 minutes)   
Added account saving on disconnect. (saves when browser is closed or loss of connection to server)  
Added logout function.   
Added Player processing and saving within PlayerHandler.java  
Note: *Player from server currently sends information for client to read every second  
(will need to change in future to only send data that has changed, just using during development)*  
