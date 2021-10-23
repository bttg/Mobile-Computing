import MySQLdb
db = MySQLdb.connect(host="localhost", user="root", passwd="19970224lL@", db="myemployees", charset='utf8')
cursor = db.cursor()
sql="""SELECT
	*
FROM
	employees
WHERE
	salary>12000;
"""

cursor.execute(sql)
results = cursor.fetchall()
print(results)