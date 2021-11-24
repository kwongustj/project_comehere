import chromedriver_autoinstaller
from selenium import webdriver
import pandas as pd
import re
import time
import os, sys
import random



def open_chromedriver():
    chrome = chromedriver_autoinstaller.install(os.getcwd())

    options = webdriver.ChromeOptions()
    # options.headless = True

    driver = webdriver.Chrome(options=options)
    return (driver)


def get_blog(value):
    iframe = driver.find_element_by_xpath('//*[@id="mainFrame"]')
    driver.switch_to_frame(iframe)
    title = driver.find_element_by_class_name('pcol1').text
    try:
        body = driver.find_element_by_class_name('se-main-container').text
    except:
        number = re.split('/', value)[len(re.split('/', value)) - 1]
        temp = driver.find_element_by_xpath('//*[@id="post-view%s"]' % number).text
        try:
            body = re.split('이웃추가', temp)[1]
        except:
            body = temp

    return (pd.DataFrame({'shop': shop, 'title': title, 'body': body}, index=[0]))


def get_cafe():
    iframe = driver.find_element_by_xpath('//*[@id="cafe_main"]')
    driver.switch_to_frame(iframe)
    title = driver.find_element_by_class_name('title_text').text

    body = driver.find_element_by_class_name('article_viewer').text

    return (pd.DataFrame({'shop': shop, 'title': title, 'body': body}, index=[0]))


def get_view_more():
    while True:
        check = len(
            driver.find_elements_by_xpath('//*[@id="app-root"]/div/div/div[2]/div[5]/div[4]/div[2]/div[1]/ul/li[*]'))
        try:
            driver.find_element_by_xpath('//*[@id="app-root"]/div/div/div[2]/div[5]/div[4]/div[2]/div[2]/a').click()
            time.sleep(random.random() + 0.5)
        except:
            print(len(driver.find_elements_by_xpath(
                '//*[@id="app-root"]/div/div/div[2]/div[5]/div[4]/div[2]/div[1]/ul/li[*]')))
            break
        print(len(driver.find_elements_by_xpath(
            '//*[@id="app-root"]/div/div/div[2]/div[5]/div[4]/div[2]/div[1]/ul/li[*]')))
        if check == len(driver.find_elements_by_xpath(
                '//*[@id="app-root"]/div/div/div[2]/div[5]/div[4]/div[2]/div[1]/ul/li[*]')):
            break


def more_shop():
    while True:
        check = len(driver.find_elements_by_class_name('_2kPto'))
        try:
            driver.find_element_by_xpath('//*[@id="app-root"]/div/div/div[2]/div[5]/div/div/div[3]/a').click()
            time.sleep(random.random() + 0.5)
        except:
            print(len(driver.find_elements_by_class_name('_2kPto')))
            break
        print(len(driver.find_elements_by_class_name('_2kPto')))
        if check == len(driver.find_elements_by_class_name('_2kPto')):
            break


def get_link():
    link = []
    for i in driver.find_elements_by_xpath('//*[@id="app-root"]/div/div/div[2]/div[5]/div[4]/div[2]/div[1]/ul/li[*]'):
        link.append(i.find_element_by_class_name('_2HzSL').get_attribute('href'))
    return (link)


def get_data():
    data_all = pd.DataFrame()
    num = 0
    driver.implicitly_wait(3)
    for i in link:
        driver.get(i)
        print(num, '/', len(link))
        num = num + 1
        if 'blog' in i:
            data_all = pd.concat([data_all, get_blog(i)], axis=0)
        else:
            try:
                data_all = pd.concat([data_all, get_cafe()], axis=0)
            except:
                driver.switch_to_window(driver.window_handles[1])
                driver.close()
                driver.switch_to_window(driver.window_handles[0])

                continue
    return (data_all.reset_index(drop=True))


def clk_inner():
    for i in driver.find_elements_by_xpath('//*[@id="app-root"]/div/div/div[2]/div[3]/div/div/div/div/a[*]'):
        if i.text == '내부시설':
            i.click()
        else:
            continue


def clk_review1():
    for i in driver.find_elements_by_class_name('_3aXen'):
        if i.text == '리뷰':
            i.click()
        else:
            continue


def clk_review2():
    for i in driver.find_elements_by_xpath('//*[@id="app-root"]/div/div/div[2]/div[5]/div[2]/div/a[*]/span'):
        if i.text == '블로그 리뷰':
            i.click()
        else:
            continue


driver = open_chromedriver()

url = [
    'https://map.naver.com/v5/entry/place/17784854?c=14185061.5348803,4389743.7894362,15,0,0,0,dh&placePath=%3Fentry=plt%26from=nx']

driver.get(url[0])

time.sleep(2)

iframe = driver.find_element_by_xpath('//*[@id="entryIframe"]')

driver.switch_to_frame(iframe)

clk_inner()

more_shop()

for i in driver.find_elements_by_class_name('_2kPto'):
    temp_num = 'https://map.naver.com/v5/entry/place/{}'.format(
        re.sub('[^0-9]', '', i.find_element_by_tag_name('a').get_attribute('href')))
    url.append(temp_num)

data_all = pd.DataFrame()
num = 0
for i in url:
    try:
        driver.get(i)

        time.sleep(2)

        try:
            iframe = driver.find_element_by_xpath('//*[@id="entryIframe"]')
            driver.switch_to_frame(iframe)
        except:
            time.sleep(30)
            driver.refresh()
            iframe = driver.find_element_by_xpath('//*[@id="entryIframe"]')
            driver.switch_to_frame(iframe)
        time.sleep(2)
        clk_review1()
        time.sleep(2)
        clk_review2()
        time.sleep(2)
        get_view_more()
        link = get_link()
    except:
        driver.get(i)

        time.sleep(2)

        try:
            iframe = driver.find_element_by_xpath('//*[@id="entryIframe"]')
            driver.switch_to_frame(iframe)
        except:
            time.sleep(30)
            driver.refresh()
            iframe = driver.find_element_by_xpath('//*[@id="entryIframe"]')
            driver.switch_to_frame(iframe)
        time.sleep(2)
        clk_review1()
        time.sleep(2)
        clk_review2()
        time.sleep(2)
        get_view_more()
        link = get_link()
    shop = driver.find_element_by_xpath('//*[@id="app-root"]/div/header/h1').text
    data = get_data()
    data_all = pd.concat([data_all, data], axis=0)
    data_all = data_all.reset_index(drop=True)
    num = num + 1
    print(num, '/', len(url))
    driver.delete_all_cookies()

writer = pd.ExcelWriter(r'네이버 블로그 리뷰.xlsx', engine='xlsxwriter', options={'strings_to_urls': True})
data_all.to_excel(writer)
writer.close()