import json
import MySQLdb
import sendEmail


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

def bookkeeping(id, expenditure, EOI, imageID, comment, tag, address,lat,lng):
    db = MySQLdb.connect(host="localhost", user="root", passwd="19970224lL@", db="mobile_db", charset='utf8')
    cursor = db.cursor()
    res = {'code': 400}
    code = """INSERT INTO bookkeeping(id, expenditure, EOI, imageID, comment, tag, address,lat,lng)
                   VALUES ('%d','%.2f','%d','%d','%s','%s','%s','%.3f','%.3f');""" % (id, expenditure, EOI, imageID, comment, tag, address,lat,lng)
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
    code = 'SELECT tag, SUM(expenditure) FROM bookkeeping WHERE EOI=0 AND `id`=%d GROUP BY tag ;'%(id)
    try:
        cursor.execute(code)
        result = cursor.fetchall()
        resarr=[]


        for i in result:
            resdict = {}
            resdict["tag"] = i[0]
            resdict["money"] = "%.2f"%(i[1])
            resarr.append(resdict)

        res = {'code': 200, 'data':resarr}
    except:
        res = {'code': 400}

    db.close()
    return json.dumps(res)

def modifyUser(id,nickname,password,eMail):
    db = MySQLdb.connect(host="localhost", user="root", passwd="19970224lL@", db="mobile_db", charset='utf8')
    cursor = db.cursor()
    code = 'SELECT `nickname`, `password`, `email` FROM `users` WHERE id=%d'%(id)

    try:
        cursor.execute(code)
        result = cursor.fetchone()

        oldNickname = result[0]
        oldPassword = result[1]
        oldEmail = result[2]




        if nickname!="":
            oldNickname = nickname

        if password!="":
            oldPassword = password

        if eMail!="":
            oldEmail = eMail


        code = """UPDATE `users` SET `nickname`='%s', `password`='%s', `email`='%s' WHERE `id` = %d;""" %(oldNickname,oldPassword,oldEmail,id)
        cursor.execute(code)
        db.commit()


        res = {'code': 200}
    except:
        db.rollback()
        res = {'code': 400}

    db.close()
    return json.dumps(res)

def fetchData(id):
    db = MySQLdb.connect(host="localhost", user="root", passwd="19970224lL@", db="mobile_db", charset='utf8')
    cursor = db.cursor()
    code = """SELECT EOI,SUM(expenditure) FROM bookkeeping WHERE`id`=%d GROUP BY EOI ;"""%(id)



    try:
        res = {'code': 200}
        cursor.execute(code)
        result = cursor.fetchall()
        expenditure = 0
        income = 0

        if len(result)==2:
            income = result[0][1]
            expenditure = result[1][1]
        elif len(result)==1:
            if result[0][0]==0:
                expenditure = result[0][1]
            else:
                income = result[0][1]








        res["expenditure"] = expenditure
        res["income"] = income

        dataarr=[]


        code = """SELECT `expenditure`, `create_time`, `comment`, `tag`, `imageID`, `lat`, `lng` FROM bookkeeping WHERE `id` = %d LIMIT 30;"""%(id)
        cursor.execute(code)
        result = cursor.fetchall()

        for i in result:
            temp={}
            temp["money"] = i[0]
            temp["date"] = str(i[1])
            temp["comment"] = i[2]
            temp["tag"] = i[3]
            temp["imageID"] = i[4]
            temp["lat"] = i[5]
            temp["lng"] = i[6]
            dataarr.append(temp)

        res["data"] = dataarr[::-1]
    except:
        res = {'code': 400}

    db.close()
    return json.dumps(res)

def emailMe(id):
    db = MySQLdb.connect(host="localhost", user="root", passwd="19970224lL@", db="mobile_db", charset='utf8')
    cursor = db.cursor()

    try:
        code = """SELECT nickname, email FROM `users` WHERE`id`=%d;""" % (id)
        cursor.execute(code)
        result = cursor.fetchone()

        nickname = result[0]
        email = result[1]

        code = """SELECT EOI,SUM(expenditure) FROM bookkeeping WHERE`id`=%d GROUP BY EOI ;""" % (id)
        cursor.execute(code)
        result = cursor.fetchall()
        expenditure = 0
        income = 0

        if len(result) == 2:
            income = result[0][1]
            expenditure = result[1][1]
        elif len(result) == 1:
            if result[0][0] == 0:
                expenditure = result[0][1]
            else:
                income = result[0][1]


        expen=[]
        inc=[]

        code = """SELECT expenditure, EOI, `comment`, `tag`, `address` FROM `bookkeeping` WHERE`id`=%d;"""%(id)
        cursor.execute(code)
        result = cursor.fetchall()
        for i in result:
            if i[1]==0:
                expen.append(i)
            else:
                inc.append(i)

        expenstr="\nExpenditure:\n"
        for i in expen:
            expenstr+="$%.2f, %s, %s, %s\n"%(i[0],i[3],i[2],i[4])

        incstr="\nIncome:\n"
        for i in inc:
            incstr+="$%.2f, %s, %s, %s\n"%(i[0],i[3],i[2],i[4])


        msg="""Hello, %s!
You have spent $%.2f and earned $%.2f in total.
Here are the details:
        
%s
%s
        
Thank you for using the money app.
Best regards.
Team COMP90018."""%(nickname,expenditure,income,expenstr,incstr)
        sendEmail.sendEmailFun(email,msg)

        db.close()
        return


    except:
        return
