import json
import pandas as pd
from flask import Flask, request, render_template
from flask_restful import Resource, Api, reqparse, abort
from openpyxl import load_workbook
import pandas as pd
from math import log

app = Flask(__name__)
api = Api(app)

class TodoList(Resource):
    def get(self):
        df = pd.read_excel('result.xlsx')
        df_list_ = []
        df_list = df.values.tolist()
        for i in df_list:
            if i[4] == 0:
                continue
            num = i[4]
            aa = int(num.replace(',', ''))  # 콤마제거 후 정수로 받음
            if aa > 100:  # 리뷰 100개 이상인 곳만
                df_list_.append(i)
        print(df_list_)

        df_list_.sort(reverse=True, key=lambda x: x[2])

        df_list_result = []
        for i in range(0, 10):
            df_list_result.append(df_list_[i])
            shop = df_list_result[i][1].split(' ')
            df_list_result[i][1] = shop[0]
        Todos = {"todo" + str(i): {"task": string[1], "url": string[3]} for i, string in enumerate(df_list_result)}
        print(Todos)
        print(json.dumps(Todos))
        print('')
        print(json.dumps(Todos, ensure_ascii=False, indent=4))
        return Todos

# Todos = {
#     'todo1': {"task": "exercise"}
# }

class hello(Resource):
    def get(self):
        count = 0
        args_dict = request.args.to_dict()
        print(args_dict)

        lst = list(args_dict.values())
        df2 = pd.read_excel('keyword_select.xlsx')
        df2_list = df2.values.tolist() # 오류 발생 첫번째 줄 안 읽힘
        print("df2)list: ",df2_list)
        keyword2_list = []
        dictdict = {}
        count = 1
        for i in df2_list:
            if i[0] in lst:
                print(keyword2_list)
                a= i[0]
                del i[0]
                string = "".join(i)
                dic1 = {"data":string}
                dictdict["todo"+str(count)] = dic1
                print("print_dictdict: ", dictdict)
                count = count + 1
        print(dictdict)
        print(json.dumps(dictdict))
        print('')
        print(json.dumps(dictdict, ensure_ascii=False, indent=4))
        return dictdict

class second_keyword(Resource):
    def get(self):
        args_dict = request.args.to_dict()
        print(args_dict)

        lst = list(args_dict.values())
        df2 = pd.read_excel('keyword_select.xlsx')
        df2_list = df2.values.tolist()
        print("df2)list: ",df2_list)
        keyword2_list = []
        dictdict = {}
        count = 1
        for i in df2_list:
            if i[0] in lst:
                print(keyword2_list)
                a= i[0]
                del i[0]
                string = "".join(i)
                dic1 = {"data":string}
                dictdict["todo"+str(count)] = dic1
                print("print_dictdict: ", dictdict)
                count = count + 1
        print(dictdict)
        print(json.dumps(dictdict))
        print('')
        print(json.dumps(dictdict, ensure_ascii=False, indent=4))
        print("dictdict: ",dictdict)
        return dictdict

class keyword2(Resource):
    def get(self):
        args_dict = request.args.to_dict()
        vocab = list(args_dict.values())
        read_xlsx = load_workbook(r'review.xlsx')
        read_sheet = read_xlsx.active

        name_col = read_sheet['C']
        docs = []
        for cell in name_col:
            docs.append(cell.value)

        name_col = read_sheet['B']
        doc = []
        for cell in name_col:
            doc.append(cell.value)

        vocab.sort()

        N = len(docs)  # 총 문서의 수

        def tf(t, d):
            return d.count(t)

        def idf(t):
            df = 0
            for doc in docs:
                df += t in doc
            return log(N / (df + 1))

        def tfidf(t, d):
            return tf(t, d) * idf(t)

        result = []
        for i in range(N):  # 각 문서에 대해 아래 명령 수행
            result.append([])
            d = docs[i]
            for j in range(len(vocab)):
                t = vocab[j]
                result[-1].append(tf(t, d))

        tf_ = pd.DataFrame(result, columns=vocab)
        tf_

        result = []
        for j in range(len(vocab)):
            t = vocab[j]
            result.append(idf(t))

        idf_ = pd.DataFrame(result, index=vocab, columns=['IDF'])
        idf_

        result = []
        for i in range(N):
            result.append([])
            d = docs[i]

            for j in range(len(vocab)):
                t = vocab[j]
                result[-1].append(tfidf(t, d))

        tfidf_ = pd.DataFrame(result, doc, columns=vocab)
        tfidf_['Total'] = tfidf_.sum(axis=1)
        list_tfidf = tfidf_.sort_values(by=['Total'], axis=0, ascending=False).head(5)
        list____ = list(list_tfidf.index)
        print("list____: ", list____)

        df = pd.read_excel('result.xlsx')
        df_dict = {}
        df_list = df.values.tolist()

        for i in list____:
            df_dict[i] = "_"

        for i in df_list:
            for j in list____:
                if i[1] == j:
                    df_dict[j] = i[3]


        print("df_list_: ",df_dict)

        # df_list_result = []
        # for i in range(0,len(df_dict)+1):
        #     df_list_result.append(list____[i])
        # print("df_list_result: ", df_list_result)

        Todos = {}
        i = 0
        for key, value in df_dict.items():
            Todos['todo'+str(i)]= {"task" : key ,"url": value}
            i = i + 1

        print("TODOS: ", Todos)
        return Todos



api.add_resource(TodoList, '/todos/')
api.add_resource(hello, '/hello')
api.add_resource(keyword2, '/keyword2')

if __name__ == '__main__':
    app.run(host="172.30.1.56", port=5000, debug=True)


