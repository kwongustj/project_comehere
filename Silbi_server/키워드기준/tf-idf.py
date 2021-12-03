from openpyxl import load_workbook
import pandas as pd
from math import log


read_xlsx = load_workbook(r'../../project_Silbi/Silbi_server/키워드기준/review.xlsx')
read_sheet = read_xlsx.active


name_col = read_sheet['C']
docs = []
for cell in name_col:
    docs.append(cell.value)

name_col = read_sheet['B']
doc = []
for cell in name_col:
    doc.append(cell.value)

vocab = ['쌀국수', '태국', '달콤한']
vocab.sort()

N = len(docs) # 총 문서의 수

def tf(t, d):
    return d.count(t)

def idf(t):
    df = 0
    for doc in docs:
        df += t in doc
    return log(N/(df + 1))

def tfidf(t, d):
    return tf(t, d) * idf(t)

result = []
for i in range(N):  # 각 문서에 대해 아래 명령 수행
    result.append([])
    d = docs[i]
    for j in range(len(vocab)):
        t = vocab[j]
        result[-1].append(tf(t, d))
        
tf_ = pd.DataFrame(result, columns = vocab)
tf_

result = []
for j in range(len(vocab)):
    t = vocab[j]
    result.append(idf(t))
    
idf_ = pd.DataFrame(result, index = vocab, columns = ['IDF'])
idf_

result = []
for i in range(N):
    result.append([])
    d = docs[i]
    
    for j in range(len(vocab)):
        t = vocab[j]  
        result[-1].append(tfidf(t, d))   
        
tfidf_ = pd.DataFrame(result, doc, columns = vocab)
tfidf_['Total'] = tfidf_.sum(axis=1)
list_tfidf = tfidf_.sort_values(by=['Total'], axis =0, ascending = False).head(5)
print(list_tfidf, list_tfidf.index, sep='\n')