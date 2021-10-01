import json

import MySQLdb


def signup(acc, nickname, password, email):
    db = MySQLdb.connect(host="localhost", user="root", passwd="19970224lL@", db="mobile_db", charset='utf8')
    cursor = db.cursor()
    res = {'code': 2}

    #search
    code = """SELECT * FROM users
WHERE account='%s';"""%(acc)

    try:
        cursor.execute(code)
        result = cursor.fetchall()


        #check if the account exists
        if len(result) == 0:
            #if not then signup
            code = """INSERT INTO users(account,nickname,`password`,email) 
               VALUES ('%s','%s','%s','%s');""" % (acc, nickname, password, email)
            try:
                cursor.execute(code)
                db.commit()
                res['code']=0

            except:
                db.rollback()
                res['code']=2
        #error
        else:
            res['code']=1
    except:
        res['code']=2

    db.close()
    return json.dumps(res)

def login(acc, password):
    db = MySQLdb.connect(host="localhost", user="root", passwd="19970224lL@", db="mobile_db", charset='utf8')
    cursor = db.cursor()
    res = {'code': 2}

    # search
    code = """SELECT `password` FROM users WHERE account='%s';""" % (acc)
    try:
        cursor.execute(code)
        result = cursor.fetchone()
        real_password = result[0]

        if real_password == password:
            #login successes
            res['code'] = 0
        else:
            res['code'] = 1


    except:
        res['code'] = 2

    db.close()
    return json.dumps(res)
