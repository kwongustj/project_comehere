import requests
from bs4 import BeautifulSoup
import openpyxl
import firebase_admin
from firebase_admin import credentials
from firebase_admin import db

count = 0
floor = 0

#firebase database
cred = credentials.Certificate("mykey.json")
firebase_admin.initialize_app(cred, {
    'databaseURL': 'https://silbi-7becf-default-rtdb.asia-southeast1.firebasedatabase.app/'  # firebase연결
})
#Excel
wb = openpyxl.Workbook()
ws1 = wb.active
ws1.title = '현대 백화점 충청점'
ws1.append(["순번","점포 이름","전화번호","층"])

#크롤링
url = 'http://www.ehyundai.com/newPortal/DP/FG/FG000000_V.do?branchCd=B00147000'
response = requests.get(url)

if response.status_code == 200:
    html = response.text
    soup = BeautifulSoup(html, 'html.parser')
    soup2 = soup.prettify()
    result_list = soup2.split('\n')

    for link in soup.find_all(attrs={'class': ['item brand-item', "num"]}):
        string = link.get_text(" ", strip=True)
        if len(string)==2:
            floor = string
            continue
        elif len(string)==1: continue
        else:
            array = string.rsplit(" ",1)
            count = count+1
            path = '점포/'+str(count)
            print(path)
            db.reference(path).update({'점포이름': array[0]})
            db.reference(path).update({'전화번호': array[1]})
            db.reference(path).update({'층수': floor})
            print(count)
            print(array[0])
            print(array[1])
            ws1.append([count,array[0],array[1],floor])

wb.save("hyundai_crawl.xlsx")
