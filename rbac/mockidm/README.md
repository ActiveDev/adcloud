# Mock IDM
This project mimics an identity management system in that it will authenticate a user against an embedded ldap server and create a JWT that can then be used by the gateway.

## IMPORTANT
This is just a mock project and should NOT be used in production. It does not adhere to any security standards whatsoever.

## Create a Token
POST
http://localhost:8090/mockauth/oauth/token?grant_type=password&username=ben&password=benspassword
Basic Auth: mock/secret

### Example with Curl
curl mock:secret@localhost:8080/mockauth/oauth/token -d grant_type=password -d username=ben -d password=benspassword 

This will create a JWT with a signing key of "123"
