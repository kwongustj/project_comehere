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
            if i[2] == "평점 없음":
                continue
            else:
                df_list_.append(i)

        df_list_.sort(reverse=True, key=lambda x: x[2])

        df_list_result = []
        for i in range(0, 10):
            df_list_result.append(df_list_[i])
            shop = df_list_result[i][1].split(' ')
            df_list_result[i][1] = shop[0]
        Todos = {"todo" + str(i): {"task": string[1]} for i, string in enumerate(df_list_result)}
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
        for key, value in args_dict.items():
            if value == " ":
                count = count + 1
        if count > 2:
            df = pd.read_excel('result.xlsx')
            df_list_ = []
            df_list = df.values.tolist()
            for i in df_list:
                if i[2] == "평점 없음":
                    continue
                else:
                    df_list_.append(i)

            df_list_.sort(reverse=True, key=lambda x: x[2])

            df_list_result = []
            for i in range(0, 10):
                df_list_result.append(df_list_[i])
                shop = df_list_result[i][1].split(' ')
                df_list_result[i][1] = shop[0]
            Todos = {"todo" + str(i): {"task": string[1]} for i, string in enumerate(df_list_result)}
            print(Todos)
            print(json.dumps(Todos))
            print('')
            print(json.dumps(Todos, ensure_ascii=False, indent=4))
            return Todos
        else:
            print(args_dict)


api.add_resource(TodoList, '/todos/')
api.add_resource(hello, '/hello')

if __name__ == '__main__':
    app.run(host="172.30.1.13", port=5000, debug=True)

