
def extract_product_review(file):
    from selenium import webdriver
    from selenium.webdriver import ActionChains
    from selenium.webdriver.common.by import By
    import pandas as pd
    import time
    import json

    path = f'D:\data\융합프로젝트\data/{file}.json'
    with open(path, 'r', encoding='utf-8') as f:
        data = json.load(f)

    url = data['0']['url']

    dr = webdriver.Chrome()
    act = ActionChains(dr)

    dr.get(url)

    menu = dr.find_element('css selector', '.goods_reputation')
    act.click(menu).perform()
    time.sleep(2)

    filter_button = dr.find_element(By.XPATH, '/html/body/div[3]/div[8]/div/div[9]/div/div[4]/div[5]/button')
    act.click(filter_button).perform()
    time.sleep(2)

    checkbox_5point = dr.find_element(By.CSS_SELECTOR, '#searchPoint > li:nth-child(2)')
    checkbox_20 = dr.find_element(By.CSS_SELECTOR, '#filterDiv > div.skin_type_area > div > div:nth-child(4) > dl > dd > ul > li:nth-child(2)')
    checkbox_women = dr.find_element(By.CSS_SELECTOR, '#filterDiv > div.skin_type_area > div > div:nth-child(5) > dl > dd > ul > li:nth-child(1)')
    checkbox_confirm = dr.find_element(By.CSS_SELECTOR, '#btnFilterConfirm')

    checkbox_5point.click()
    checkbox_20.click()
    checkbox_women.click()
    checkbox_confirm.click()
    time.sleep(2)

    product_review = {
        '피부타입_건성에 좋아요' : 0, '피부타입_복합성에 좋아요' : 0, '피부타입_지성에 좋아요' : 0,
        '피부고민_보습에 좋아요' : 0, '피부고민_진정에 좋아요' : 0, '피부고민_주름/미백에 좋아요' : 0,
        '발림성_아주 만족해요' : 0, '발림성_보통이에요' : 0, '발림성_다소 아쉬워요' : 0,
        '세정력_아주 만족해요' : 0, '세정력_보통이에요' : 0, '세정력_다소 아쉬워요' : 0,
        '자극도_자극없이 순해요' : 0, '자극도_보통이에요' : 0, '자극도_자극이 느껴져요' : 0
        }

    user_statistic = {
        '지성' : 0,'건성' : 0,'복합성' : 0,'민감성' : 0,'약건성' : 0,'트러블성' : 0,'중성' : 0,'쿨톤' : 0,'웜톤' : 0,'봄웜톤' : 0,'여름쿨톤' : 0,'가을웜톤' : 0,'겨울쿨톤' : 0,
        '잡티' : 0,'미백' : 0,'주름' : 0,'각질' : 0,'트러블' : 0,'블랙헤드' : 0,'피지과다' : 0,'모공' : 0,'탄력' : 0,'홍조' : 0,'아토피' : 0,'다크서클' : 0
        }

    try:
        for i in range(1,4):
            for j in range(1,4):
                category = dr.find_element(By.CSS_SELECTOR, f'#gdasContentsArea > div > div.poll_all.clrfix > dl:nth-child({i}) > dt > span')
                category_content = dr.find_element(By.CSS_SELECTOR, f'#gdasContentsArea > div > div.poll_all.clrfix > dl:nth-child({i}) > dd > ul > li:nth-child({j}) > span')
                table_percent = dr.find_element(By.CSS_SELECTOR, f'#gdasContentsArea > div > div.poll_all.clrfix > dl:nth-child({i}) > dd > ul > li:nth-child({j}) > em')
                product_review[f'{category.text}_{category_content.text}'] = int(table_percent.text[:-1])
    except:
        pass

    for i in range(1, 11):    
        tags = dr.find_element(By.CSS_SELECTOR, f'#gdasList > li:nth-child({i}) > div.info > div > p.tag')
        tag_list = list(set(tags.text.split()))
        for tag in tag_list:
            user_statistic[tag] += 1

    page = 1
    while True:
        if page == 1:
            try:
                for i in range(2, 11):
                    next_page = dr.find_element(By.CSS_SELECTOR, f'#gdasContentsArea > div > div.pageing > a:nth-child({i})')
                    next_page.click()
                    time.sleep(3)

                    for i in range(1, 11):
                        try: 
                            tags = dr.find_element(By.CSS_SELECTOR, f'#gdasList > li:nth-child({i}) > div.info > div > p.tag')
                            tag_list = list(set(tags.text.split()))
                            for tag in tag_list:
                                user_statistic[tag] += 1
                        except:
                            pass

                next_10page = dr.find_element(By.CSS_SELECTOR, '#gdasContentsArea > div > div.pageing > a.next')
                next_10page.click()
                time.sleep(3)
            except:
                break
        else:
            try:
                for i in range(3, 12):
                    next_page = dr.find_element(By.CSS_SELECTOR, f'#gdasContentsArea > div > div.pageing > a:nth-child({i})')
                    next_page.click()
                    time.sleep(3)

                    for i in range(1, 11):
                        try: 
                            tags = dr.find_element(By.CSS_SELECTOR, f'#gdasList > li:nth-child({i}) > div.info > div > p.tag')
                            tag_list = list(set(tags.text.split()))
                            for tag in tag_list:
                                user_statistic[tag] += 1
                        except:
                            pass
                            
                next_10page = dr.find_element(By.CSS_SELECTOR, '#gdasContentsArea > div > div.pageing > a.next')
                next_10page.click()
                time.sleep(3)
            except:
                break
        page += 1

    dict_ = {
        '0' : {
            'brand' : data['0']['brand'],
            'name' : data['0']['name'],
        }
    }

    for k, v in product_review.items():
        dict_['0'][k] = v
    for k, v in user_statistic.items():
        dict_['0'][k] = v

    df = pd.DataFrame.from_dict(dict_, orient='index')
    return df



def load_data(data, file, extention):
    import json
    import os

    path = 'D:/data/융합프로젝트/data/'

    if extention == 'json':
        if os.path.isfile(path + f'{file}.json') == True:
            with open(path + f'{file}.json', 'r') as f:
                try:
                    dict_ = json.load(f)
                except json.decoder.JSONDecodeError:
                    dict_ = {}
            dict_.update(data)
            with open(path + f'{file}.json', 'w', encoding='utf-8') as f:
                json.dump(dict_, f, ensure_ascii=False, indent=4)
        else:
            with open(path + f'{file}.json', 'w', encoding='utf-8') as f:
                json.dump(data, f, ensure_ascii=False, indent=4)
    elif extention == 'csv':
        data.to_csv(path + f'{file}.csv')

data = extract_product_review('스킨케어', 0, 1, 'json')