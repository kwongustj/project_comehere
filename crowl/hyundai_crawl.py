import requests
from bs4 import BeautifulSoup

url = 'http://www.ehyundai.com/newPortal/DP/FG/FG000000_V.do?branchCd=B00147000'

response = requests.get(url)

if response.status_code == 200:
    html = response.text
    soup = BeautifulSoup(html, 'html.parser')

    for link in soup.find_all(attrs={'class': 'item brand-item'}):
        str = link.get_text(" ", strip=True)
        print(str)

