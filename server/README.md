# API usage guide V1.1 by zzh
## Details
* URL：http://139.180.180.99:8888/api
* All the post requests should be **json form** as following:
```json
{
    "operation":"", //if needed
    "account":"Yifan Ma",
    "password":"oushishuaiguo",
    "...":""
}
```
* All the requests will receive a **json response** as following:
```json
{
    "code": 200,
    "something else":"......"
}
```
Where code means the status of the operations:
**NOTE: All the codes are INTEGERS**
```
code = 200 : success
code = 201 : cannot find the user
code = 202 : wrong password
...
code = 400 : something wrong with server
```

## /login
### post:
* account: account
* password: password

### get:
* code: code
* nickname: nickname
* email: email


## /signup
### post:
* account: account
* nickname: nickname
* password: password
* email: email

### get:
* code: code
