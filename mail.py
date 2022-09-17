
import smtplib


MY_EMAIL = "milindneetabobade@gmail.com"
MY_PASSWORD = ""


connection = smtplib.SMTP("smtp.gmail.com")
connection.starttls()
connection.login(MY_EMAIL, MY_PASSWORD)
connection.sendmail(from_addr=MY_EMAIL,
                            to_addrs="email",
                            msg=f"Subject:\n\n")
connection.close()