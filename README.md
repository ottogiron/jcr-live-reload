Welcome to the jcr-live-reload wiki!

# What is JCR Live Reload

JCRLiveReload.js connects to a JCR LiveReload server via web sockets and listens for incoming change notifications   on JCR nt:file nodes. When a resource  change it refreshes the browser page. The server is implemented using Sling Event listeners, to listen for incoming changes which then are notified via web sockets, to the connected browser pages. The implementation leverages the apache felix OSGI infrastructure. 

## How to use it
You have to install the server in your Apache Felix instance then include a reference to the jcr-livereload.js script.

* Clone the repository 
* Execute _**mvn clean install -P auto-deploy**_. That will install JCR Live Reload Server on apache felix via sling maven plugin.
* go to [http://localhost:9001/index.html](http://localhost:9001/index.html)
* Copy the code and paste it in a page (template, component) you wish to be reloaded when a resource change.


## Configuration

By default the server is configured to listen for changes on _**"/app" and "/etc"**_ paths on port _**9001**_. 
The installation registers 2 configurable components via _**Apache Felix configuration console**_ , (JumLabs) Live Reload Server(port configuration) and Live Reload Even Listener (paths configuration). 
The paths syntax follows the [LDAP Filter Syntax](http://www.ldapexplorer.com/en/manual/109010000-ldap-filter-syntax.htm).

