import requests
from bs4 import BeautifulSoup
import openpyxl

count = 0
floor = 0
wb = openpyxl.Workbook()
ws1 = wb.active

ws1.title = '현대 백화점 충청점'
ws1.append(["순번","점포 이름","전화번호","층"])

url = 'http://www.ehyundai.com/newPortal/DP/FG/FG000000_V.do?branchCd=B00147000'

response = requests.get(url)

if response.status_code == 200:
    html = response.text
    soup = BeautifulSoup(html, 'html.parser')
    soup2 = soup.prettify()
    result_list = soup2.split('\n')

    for link in soup.find_all(attrs={'class': ['item brand-item', "num"]}):
        str = link.get_text(" ", strip=True)
        if len(str)==2:
            floor = str
            continue
        elif len(str)==1: continue
        else:
            array = str.rsplit(" ",1)
            count = count+1
            # print(count)
            # print(array[0])
            # print(array[1])
            ws1.append([count,array[0],array[1],floor])

wb.save("hyundai_crawl.xlsx")