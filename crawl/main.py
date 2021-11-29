from util import get_driver, drag_last, find_css
import time
import pandas as pd
from tqdm import tqdm

df = pd.DataFrame(columns=['점포명','평점'])

driver = get_driver()

driver.get('https://map.naver.com/v5/entry/place/17784854?placePath=%2Freview%2Fvisitor%3Fentry=plt&c=14184595.7485731,4389743.7865117,15,0,0,3,dh')

driver.switch_to.frame('entryIframe')

# while True:
#     drag_last(driver)
#     time.sleep(2)
#     driver.find_element_by_css_selector('div._2kAri > a._3iTUo'). click()

list_ = driver.find_elements_by_css_selector('ul.Boq3c > li')

href_list = []

for i in list_:
    href_list.append(find_css(i, 'a').get_attribute('href'))

driver.switch_to.window(driver.window_handles[-1])

for index, href in enumerate(tqdm(href_list)):
    temp = []
    driver.get(href)
    time.sleep(1)
    title = find_css(driver, 'span._3XamX').text
    try:
        rate = float(find_css(driver, 'span._1Y6hi._1A8_M > em').text)
    except:
        rate = '평점 없음'
    temp = [title, rate]

    df.loc[index] = temp

df.to_excel('result.xlsx', encoding='utf-8-sig')