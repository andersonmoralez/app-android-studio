from random import *
from flask import Flask, jsonify, request
import json
import urllib.request
import random

app = Flask(__name__)
codigoVendedor = random.randint(1111, 999999)
vendedores = [{"id": e , "nome": "nome "+str(e), "codigoVendedor": codigoVendedor, "email":"email@email.com "+str(e), "senha":codigoVendedor} for e in range(1,11)]   


@app.route("/vendedores", methods=['GET'])
def get():
    return jsonify(vendedores)

@app.route("/vendedor/<int:id>", methods=['GET'])
def get_one(id):
    filtro = [e for e in vendedores if e["id"] == id]
    if filtro:
        return jsonify(filtro[0])
    else:
        return jsonify({})

@app.route("/vendedores", methods=['POST'])
def post():
    global vendedores
    try:
        content = request.get_json()

        # gerar id
        ids = [e["id"] for e in vendedores]
        if ids:
            nid = max(ids) + 1
        else:
            nid = 1
        content["id"] = nid
        vendedores.append(content)
        return jsonify({"status":"OK", "msg":"vendedor adicionado com sucesso"})
    except Exception as ex:
        return jsonify({"status":"ERRO", "msg":str(ex)})

@app.route("/vendedor/<int:id>", methods=['DELETE'])
def delete(id):
    global vendedores
    try:
        vendedores = [e for e in vendedores if e["id"] != id]
        return jsonify({"status":"OK", "msg":"vendedor removido com sucesso"})
    except Exception as ex:
        return jsonify({"status":"ERRO", "msg":str(ex)})

@app.route("/push/<string:key>/<string:token>", methods=['GET'])
def push(key, token):
	d = random.choice(vendedores)
	data = {
		"to": token,
		"notification" : {
			"title":d["nome"],
			"body":"Você tem um novo vendedor em "+d['nome']
		},
		"data" : {
			"vendedorId":d['id']
		}
	}
	req = urllib.request.Request('http://fcm.googleapis.com/fcm/send')
	req.add_header('Content-Type', 'application/json')
	req.add_header('Authorization', 'key='+key)
	jsondata = json.dumps(data)
	jsondataasbytes = jsondata.encode('utf-8')   # needs to be bytes
	req.add_header('Content-Length', len(jsondataasbytes))
	response = urllib.request.urlopen(req, jsondataasbytes)
	print(response)
	return jsonify({"status":"OK", "msg":"Push enviado"})


if __name__ == "__main__":
    app.run(host='0.0.0.0')
