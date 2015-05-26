# Cinema city crawler
Crawler for Cinema city competition at pluska hockey site.
This robot find special banner, parse unique URL and try auto-register defined users to get free tickets to the cinema. :) 
Cinema city formular is protected with token key, we are dealing with it. We parse token and use cookies at HTTP client.
After users are registered then program alert user with custom sound.

* No special requirements

# Program configuration
- it is defined throught static variables
- it needs URL of competition and list of users (name, email)

