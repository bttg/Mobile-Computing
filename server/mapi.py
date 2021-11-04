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


@app.route('/api/bookkeeping', methods=['POST'])
def bookkeeping():
    data = request.get_data()
    data = json.loads(data)

    id = data['id']
    expenditure = data['expenditure']
    EOI=data['EOI']
    imageID = data['imageID']
    comment = data['comment']
    tag = data['tag']
    address = data['address']
    lat = data['lat']
    lng = data['lng']
    result = mdb.bookkeeping(id, expenditure, EOI, imageID, comment, tag, address,lat,lng)

    return result

@app.route('/api/dataVisual', methods=['POST'])
def dataVisual():
    data = request.get_data()
    data = json.loads(data)
    id = data['id']
    result = mdb.dataVisual(id)

    return result


@app.route('/api/modifyUser', methods=['POST'])
def modifyUser():
    data = request.get_data()
    data = json.loads(data)

    id = data['id']
    nickname = data['newNickname']
    password = data['newPassword']
    eMail = data['newEmail']
    result = mdb.modifyUser(id,nickname,password,eMail)

    return result

@app.route('/api/fetchData', methods=['POST'])
def fetchData():
    data = request.get_data()
    data = json.loads(data)

    id = data['id']
    result = mdb.fetchData(id)

    return result

@app.route('/api/emailMe', methods=['POST'])
def emailMe():
    data = request.get_data()
    data = json.loads(data)

    id = data['code']
    mdb.emailMe(id)

    return "The email has been sent."












if __name__ == '__main__':
    app.run(host="0.0.0.0", port=8888, debug=True)