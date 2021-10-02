import json
import mobile_database as mdb
from flask import Flask, jsonify, request

app = Flask(__name__)

#signup function
@app.route('/api/signup', methods=['POST'])
def signup():
    data = request.get_data()
    data = json.loads(data)

    #store in the db
    acc = data['account']
    nickname = data['nickname']
    password = data['password']
    email = data['email']
    result = mdb.signup(acc,nickname,password,email)
    return result




@app.route('/api/login', methods=['POST'])
def login():
    data = request.get_data()
    data = json.loads(data)

    acc = data['account']
    password = data['password']
    result = mdb.login(acc,password)
    return result




if __name__ == '__main__':
    app.run(host="0.0.0.0", port=8888, debug=True)