## Symbio: Sponsorship Management for Non-Profits

**This project is currently under development for personal and organizational use.**

 Outside of software development, I'm heavily involved in non-profit management and one of my favourite portfolios in managing external relations for non-profit organizations like STEM Fellowship. 

I'm tired of using an assortment of excel sheets, documents and an email inbox to keep track of partnerships. I wanted something to condense all the information I needed into one place. Then I remembered, I'm a software developer, so I made one!

*Current Features*: 

* Store sponsor information, including sponsor name, contact person, contact email and a list of recent actions, including the type (establishing contact, neogtiating partnership, following up), and time of action taken in a PostgreSQL with a Spring MVC RESTful API.
* Store specified sponsor logos, or find them automatically using the Google Search API. 
* Search email inbox for specific keywords, currently sponsor names, to consolidate recent communications related to a sponsor using JavaMail.
* A front-end using Angular and Bootstrap.

*Demo*: 
![Demo Gif](demo.gif)

*Deployment*: 

Be sure to set your MongoDB URI in an application.properties file inside a "resources" folder in the "src/main/" directory.

To utilize the Google search API, you'll have to get a Google Search API Key, and create a search engine using Google's platform. Create a .env file in the root directory, and set API_KEY as your API key and CX_KEY as your search engine CX.

