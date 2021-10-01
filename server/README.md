# API usage guide V1.0 by zzh
## Details
* URL：http://139.180.180.99:8888/api
* All the post requests should be **json form** as following:
```json
{
    "operation":"", //if nedded
    "account":"Yifan Ma",
    "password":"oushishuaiguo",
    "...":""
}
```
* All the requests will receive a **json response** as following:
```json
{
    "code": 0,
    "something else":"......"
}
```
Where code means the status of the operations:
```
code = 0 : success
code = 1 : fail
code = 2 : unknown error
```

## /login
### post:
* account: account
* password: password

### get:
* code: code

## /signup
### post:
* account: account
* nickname: nickname
* password: password
* email: email

### get:
* code: code
