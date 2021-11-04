# -*- coding: utf-8 -*-
import json
from email.mime.text import MIMEText
import smtplib


mail_host = 'smtp.163.com'
mail_user = '15133669724@163.com'
mail_auth = 'pw'
send_name = mail_user
recv_name = mail_user

def sendEmailFun(to, content):
    msg = MIMEText(content, 'plain', 'utf-8')
    msg['From'] = send_name
    msg['To'] = recv_name
    msg['Subject'] = "Billing details"
    server = smtplib.SMTP_SSL(mail_host, 465)
    server.login(mail_user,mail_auth)
    server.sendmail(mail_user,to,msg.as_string())
    server.quit()

