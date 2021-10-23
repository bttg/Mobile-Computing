import json
import MySQLdb


def signup(acc, nickname, password, email):
    db = MySQLdb.connect(host="localhost", user="root", passwd="19970224lL@", db="mobile_db", charset='utf8')
    cursor = db.cursor()
    res = {'code': 400}

    # search
    code = """SELECT * FROM users
WHERE account='%s';"""%(acc)

    try:
        cursor.execute(code)
        result = cursor.fetchall()
        # check if the account exists
        if len(result) == 0:
            # if not then signup
            code = """INSERT INTO users(account,nickname,`password`,email) 
               VALUES ('%s','%s','%s','%s');""" % (acc, nickname, password, email)
            try:
                cursor.execute(code)
                db.commit()
                res['code'] = 200

            except:
                # something wrong with server
                db.rollback()
                res['code'] = 400
        #error
        else:
            # the user already exists
            res['code'] = 201
    except:
        res['code'] = 400

    db.close()
    return json.dumps(res)

def login(acc, password):
    db = MySQLdb.connect(host="localhost", user="root", passwd="19970224lL@", db="mobile_db", charset='utf8')
    cursor = db.cursor()
    res = {'code': 400}

    # search
    code = """SELECT `password` FROM users WHERE account='%s';""" % (acc)
    try:
        cursor.execute(code)
        result = cursor.fetchone()

        # the user does not exist
        if result == None:
            res['code'] = 202
        else:
            real_password = result[0]
            if real_password == password:
                # login successes, return the user info
                res['code'] = 200

                code = """SELECT nickname, email, id FROM users WHERE account='%s';""" % (acc)
                cursor.execute(code)
                result = cursor.fetchone()
                res['nickname'] = result[0]
                res['email'] = result[1]
                res['id'] = result[2]

            else:
                # wrong password
                res['code'] = 203
    except:
        res['code'] = 400

    db.close()
    return json.dumps(res)

def bookkeeping(id, expenditure, EOI, imageID, comment, tag, address):
    db = MySQLdb.connect(host="localhost", user="root", passwd="19970224lL@", db="mobile_db", charset='utf8')
    cursor = db.cursor()
    res = {'code': 400}
    code = """INSERT INTO bookkeeping(id, expenditure, EOI, imageID, comment, tag, address)
                   VALUES ('%d','%.2f','%d','%d','%s','%s','%s');""" % (id, expenditure, EOI, imageID, comment, tag, address)
    try:
        cursor.execute(code)
        db.commit()
        res['code'] = 200

    except:
        # something wrong with server
        db.rollback()
        res['code'] = 400

    db.close()
    return json.dumps(res)

def dataVisual(id):
    db = MySQLdb.connect(host="localhost", user="root", passwd="19970224lL@", db="mobile_db", charset='utf8')
    cursor = db.cursor()
    code = 'SELECT `expenditure`, `tag` FROM `bookkeeping` WHERE id=%d'%(id)
    try:
        cursor.execute(code)
        result = cursor.fetchall()
        resdict={}

        for i in result:
            if i[1] not in resdict.keys():
                resdict[i[1]] = 0
            else:
                resdict[i[1]] += i[0]


        res = {'code': 200, 'data' : resdict}
    except:
        res = {'code': 400}

    return res