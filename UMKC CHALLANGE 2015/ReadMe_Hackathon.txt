Summary

We developed an android app which has a home page with most popular Youtube videos and the page has links to login option, analytics and search option. 

1)The login page was developed by using OAuth 2.0 authentication protocol to access each user’s data. When the client app (our app) login via google, 
it returns authorization code to client. The client app send client id, client secret to the google server and the google returns access token to client 
who is able to login and can request and get responses from the API.

2)The second page we developed has Youtube analytics which display search results based on the analytical queries which we have written on our own. 
 
3)The third page has variable search ability which can give results for text search, speech and categorical search. We were excited the way our application produced
reliable and correct results when we input our own voice as a search query which siri cannot give impressive results for our voice.
The video plays with in the application with the Youtube API v3.

Here is how we worked out OAuth 2.0 protocol in our implementation.
When the user clicks on login button, user is redirected to the authenticating application(say google) and the user is asked if he/she wants to grant access 
to the client application. The user accepts the request.Authentication happens when the client application and authenticating application respond each other.
Now the user access the page from client application.



