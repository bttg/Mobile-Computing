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
    result = mdb.bookkeeping(id, expenditure, EOI, imageID, comment, tag, address)

    return result

@app.route('/api/dataVisual', methods=['POST'])
def dataVisual():
    data = request.get_data()
    data = json.loads(data)
    id = data['id']
    result = mdb.dataVisual(id)

    return result

if __name__ == '__main__':
    app.run(host="0.0.0.0", port=8888, debug=True)