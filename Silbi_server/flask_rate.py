
import json
import pandas as pd

from flask import Flask

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

app = Flask(__name__)

@app.route('/')
def home():
    return 'Hello, World!'

if __name__ == '__main__':
    app.run(debug=True)

