from collections import Counter

import pandas as pd
import numpy as np
from konlpy.tag import Okt

okt = Okt()


df = pd.read_excel('blog_review.xlsx')
df_list_ = []
df_list_ = df.values.tolist()
result = []
count = 0
for id in df_list_:
    #필요 없는 단어 삭제
    remove_set = {"현대백화점","유플렉스", "충청점","가든현대백화점충청점","현대백화점충청점","현대충청점","카케어","현대백화점유플렉스충청점","현대","백화점","현대충청점","현대유플렉스충청점"}
    list = id[1].split(" ")
    result = [i for i in list if i not in remove_set]

    df_list_[count][1] = result[0]
    del df_list_[count][0]
    del df_list_[count][1]
    if count == 1167: break
    count = count + 1

a = []
for id in df_list_:
    a.append(id[0])
a = set(a)
str = ""
list = []
for s in a:
    str = ""
    for id in df_list_:
        if id[0] == s:
            str = str + " " + id[1]
    map_list = [s,str]
    list.append(map_list)
list2 = []
for id in list:
    sentences_tag = []
    noun_adj_list = []
    morph = okt.pos(id[1])
    sentences_tag.append(morph)
    print("--------------------------------------" + id[0] + "--------------------------------------")
    for sentences in sentences_tag:
        for word, tag in sentences:
            if len(word) < 2:
                continue
            if tag in ['Noun','Adjective']:
                noun_adj_list.append(word)
    counts = Counter(noun_adj_list)
    list2.append([id[0],counts.most_common(40)])
    print(counts.most_common(40))
    df = pd.DataFrame.from_records(list2)
    df.to_excel('text.xlsx')



# list1 = [[1, 10], 2, [3, 19]]
# list2 = [[4, 2], [5, 9], [6, 3]]
#
# list3 = list(map(list.__add__, list1, list2))
# print(list3)