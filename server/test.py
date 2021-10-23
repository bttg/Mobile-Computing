
import requests
import json


url = "http://10.0.0.159:8888/api/dataVisual"
signup = {"account":"测试3", 'nickname':'小马', 'password':'123', 'email':'123@gmail.com'}
login = {"account":"偶是帅锅",'password':'123'}
bookkeeping={"id": 5,'expenditure': 17, 'EOI': 0, 'imageID': 3, 'address':'unimelb', 'comment': 'food', 'tag':'csgo'}
dataVisual={'id':5}
res = requests.post(url=url,data=json.dumps(dataVisual))
print(res.text)


