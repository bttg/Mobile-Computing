
import MySQLdb


def signup(acc, nickname, password, email):
    db = MySQLdb.connect(host="localhost", user="root", passwd="19970224lL@", db="mobile_db", charset='utf8')
    cursor = db.cursor()

    code = """INSERT INTO users(account,nickname,`password`,email) 
    VALUES ('%s','%s','%s','%s');"""%(acc, nickname, password, email)

    try:
        cursor.execute(code)
        db.commit()

    except:
        db.rollback()



    db.close()


