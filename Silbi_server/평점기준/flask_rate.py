import json
import pandas as pd
from flask import Flask, request, render_template
from flask_restful import Resource, Api, reqparse, abort

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



api.add_resource(TodoList, '/todos/')
api.add_resource(hello, '/hello')

if __name__ == '__main__':
    app.run(host="172.30.1.47", port=5000, debug=True)
