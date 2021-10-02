import requests
import json


url = "http://10.0.1.86:8888/api/login"
signup = {"account":"测试3", 'nickname':'小马', 'password':'123', 'email':'123@gmail.com'}
login = {"account":"偶是帅锅",'password':'123'}
res = requests.post(url=url,data=json.dumps(login))
print(res.text)
