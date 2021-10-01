import requests
import json


url = "http://10.0.0.248:8888/api/signup"
data = {"account":"偶是帅锅", 'nickname':'小马', 'password':'123', 'email':'123@gmail.com'}

res = requests.post(url=url,data=json.dumps(data))
print(res.text)
