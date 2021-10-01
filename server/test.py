import requests
import json


url = "http://192.168.124.12:8888/api/login"
signup = {"account":"测试3", 'nickname':'小马', 'password':'123', 'email':'123@gmail.com'}
login = {"account":"测试3",'password':'123'}
res = requests.post(url=url,data=json.dumps(login))
print(res.text)
