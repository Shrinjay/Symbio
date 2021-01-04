# Defining API:
#  GET /avg?{sponsor object}=x&value=y
#       Ping C# endpoint to get data with LINQ
#       Read response from data endpoint into
#       Get average for given value
#       Send avg as HTTP response

import flask
from flask import request, jsonify
import requests
import pandas as pd

app = flask.Flask(__name__)


@app.route('/api/avg/', methods=['POST'])
def average():
    res = (requests.get('http://localhost:8084/', json = request.get_json()))
    df = pd.DataFrame(res.json())
    df.index = df['_id']
    df = df.drop('_id', 1)
    print(request.get_json())

    return jsonify(res.json())


app.run()
