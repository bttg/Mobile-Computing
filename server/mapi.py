import json
import time
from datetime import datetime
import mobile_database as mdb

from flask import Flask, jsonify, request

app = Flask(__name__)

#注册页面
@app.route('/api/signup', methods=['POST'])
def signup():
    data = request.get_data()
    data = json.loads(data)

    #存入数据库
    acc = data['account']
    nickname = data['nickname']
    password = data['password']
    email = data['email']

    mdb.signup(acc,nickname,password,email)



    #数据库交互



    #判断
    status='True'
    result = json.dumps({'status':status})
    return result





@app.route('/api/login', methods=['POST'])
def login():
    data = request.get_data()
    data = json.loads(data)

    #存入数据库
    acc = data['account']
    password = data['password']


    #数据库交互

    #判断
    status='dlTrue'
    result = json.dumps({'status':status})
    return result




if __name__ == '__main__':
    app.run(host="0.0.0.0", port=8888, debug=True)