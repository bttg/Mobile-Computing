# API usage guide V1.5 by zzh
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
code = 201 : the user already exists
code = 202 : cannot find the user
code = 203 : wrong password
...
code = 400 : something wrong with server
```

## /login
### post:
* account: account
* password: password

### get:
* code: code (int)
* nickname: nickname
* email: email
* id: id


## /signup
### post:
* account: account
* nickname: nickname
* password: password
* email: email

### get:
* code: code

## /modifyUser
### post:
* id: id (int)
* newNickname: newNickname (String), if no new nickname, please submit empty string ""
* newPassword: newPassword (String), if no new password, please submit empty string ""
* newEmail: newEmail (String), if no new email, please submit empty string ""

### get:
* code: 200 or 400

## /bookkeeping
### post:
* id: id (int)
* expenditure: expenditure (unsigned float)
* EOI: EOI(int) {0: expenditure, 1: income}
* imageID: imageID(int)
* address: address(string)
* comment: comment(eg: I ate a burger) (string)
* tag: tag (eg: education, food and so on...) (string)
* lat: lat (float)
* lng: lng (float)

### get:
* code: code {200 or 400}

## /getMonthlyRecord
get a monthly report.
### post:
* id: id(int)

### get:
* expenditure: expenditure (unsigned float)
* EOI: EOI(int) {0: expenditure, 1: income}
* imageID: imageID(int)
* address: address(string)
* comment: comment(eg: I ate a burger) (string)
* tag: tag (eg: education, food and so on...) (string)

* {"data":[{e,EOI,imageID,add,c,tag},{e,EOI,imageID,add,c,tag},{e,EOI,imageID,add,c,tag}....],"code":200 or 400 (int)}

## /dataVisual
get monthly data.
### post:
* id: id

### get:
{"data":[{"tag":"edu","money":money(float)}, {"tag":"csgo","money":money(float)}], "code":200 or 400(int)}

## /editUserDate:
### post:
* id:id

### get:
* nickname: nickname (string)
* code:code (int)


## /heatMap
### post:
* id (int)

### get:
Note: only send the expenditure to the client (EOI=0)
* {"data":[{expenditure, tag, lat, lng},{expenditure, tag, lat, lng},{expenditure, tag, lat, lng}....],"code":200 or 400 (int)}


## /fetchData
fetch (max :30)
### post:
* id: id

### get:
* code: code
* income: income(Stirng .2f)
* expenditure : expenditure (String, .2f)
* data: [{money, date, comment, tag, imageID    },{},{}]
 



