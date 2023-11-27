def extract_olive_product_list():
    import requests
    from bs4 import BeautifulSoup
    from selenium import webdriver
    from selenium.webdriver import ActionChains
    from selenium.webdriver.common.by import By
    from selenium.webdriver.common.keys import Keys
    import time
    import json

    # category = {'01' : '스킨케어', '09' : '마스크팩', '10' : '클렌징', '11' : '선케어', '08' : '더모+코스메틱'}
    category = {'01' : '스킨케어'}
    dict1 = {}
    for num, category_name in category.items():
        main_url = f'https://www.oliveyoung.co.kr/store/main/getBestList.do?dispCatNo=900000100100001&fltDispCatNo=100000100{num}&pageIdx=1&rowsPerPage=8&t_page=랭킹&t_click=판매랭킹_{category_name}'

        response = requests.get(main_url)

        if response.status_code == 200:
            html = response.text
            soup = BeautifulSoup(html, 'html.parser')
            dr = webdriver.Chrome()
            act = ActionChains(dr)

            dict2 = {} 
            brand = soup.find_all('span', 'tx_brand')
            name = soup.find_all('p', 'tx_name')
            url = soup.find_all('a', 'prd_thumb goodsList')

            for idx, info in enumerate(zip(brand, name)):
                product_url = url[idx]['href']
                dr.get(product_url)

                menu = dr.find_element('css selector', '.goods_buyinfo')
                act.click(menu).perform()
                time.sleep(2)
                
                component = dr.find_element(By.XPATH, '/html/body/div[3]/div[8]/div/div[9]/div[2]/dl[8]/dd')
                component_list = component.text.split(', ')

                dict2[idx] = {'brand' : info[0].string,
                              'name' : info[1].string,
                              'url' : url[idx]['href'],
                              'elements' : component_list}
            dict1.update(dict2)
            
            path = 'D:/data/융합프로젝트/data/' + f'{category_name}_raw.json'
            with open(path, 'w', encoding='utf-8') as f:
                json.dump(dict1, f, ensure_ascii=False, indent=4)
        else : 
            return response.status_code

def transform_product_info(file, extention):
    import json
    import pandas as pd
    import re

    path = f'D:\data\융합프로젝트\data/{file}_raw.json'
    with open(path, 'r', encoding='utf-8') as f:
        data = json.load(f)

    dict_ = {}
    for idx in data.keys():
        text = data[idx]['name']
        text = re.sub(r'\[.*?\]|\(.*?\)|\d+[ml](.+?)$|\d+[g](.+?)$|\d+[g]$|\d+[+](.+?)$| 10년간, 1등| 기획(.+?)$| \w+기획$| 기획| [+] (.+?)$', '', text).lstrip().rstrip()
        text = re.sub(r'[^가-힣0-9a-zA-z ]', ' ', text)

        elements = data[idx]['elements']
        list1 = []
        for i in range(len(elements)):
            elements[i] = re.sub(r'.+?\)|Napiers|\+표시|aqua|formula|1\.', '', elements[i])
            elements[i] = re.sub(r'\[.*?\]|\(.*?\)|\"|\n|000|\(.+?$| \*(.+)$|(.+)ml|시카플라스트 밤 B5\+|\:|\*|\■', '', elements[i]).lstrip().rstrip()
            elements[i] = elements[i].replace('병풀꽃', '병풀꽃추출물').replace('황해쑥', '황해쑥잎추출물').replace('/', ' ').replace('\'', '')

            if ' ' in elements[i]:
                element_list = elements[i].split()
                for component in element_list:
                    list1.append(component)
            elif elements[i] != '':
                list1.append(elements[i])
        
        for j in range(1, len(list1)-1):
            if list1[j] == '1':
                list1[j] = '1' + list1[j+1][1:]
            elif list1[j] == '2':
                list1[j] = '2' + list1[j+1][1:]

        for k in range(2, len(list1)):
            if list1[k] == '잎':
                list1[k] = list1[k-1][:2] + '잎추출물'
            elif list1[k] == '줄기추출물':
                list1[k] = list1[k-2][:2] + '줄기추출물'
            elif list1[k] == '뿌리':
                list1[k] = list1[k-1][:2] + '뿌리추출물'

        dict_[idx] = {
            'brand' : data[idx]['brand'],
            'name' : text,
            'url' : data[idx]['url'],
            'elements' : list(dict.fromkeys(list1))
        }
    
    if extention == 'json':
        return dict_
    elif extention == 'csv':
        df = pd.DataFrame.from_dict(dict_, orient='index')
        return df

def load_data(data, file, extention):
    import json
    import os

    path = 'D:/data/융합프로젝트/data/'

    if extention == 'json':
        if os.path.isfile(path + f'{file}.json') == True:
            with open(path + f'{file}.json', 'r') as f:
                dict_ = json.load(f)
            dict_.update(data)
            with open(path + f'{file}.json', 'w', encoding='utf-8') as f:
                json.dump(dict_, f, ensure_ascii=False, indent=4)
        else:
            with open(path + f'{file}.json', 'w', encoding='utf-8') as f:
                json.dump(data, f, ensure_ascii=False, indent=4)
    elif extention == 'csv':
        data.to_csv(path + f'{file}.csv')

# extract_olive_product_list()
data = transform_product_info('스킨케어', 'json')
load_data(data, '스킨케어', 'json')