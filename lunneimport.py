from flask import Flask, jsonify, request
import json
import urllib.request
import random

app = Flask(__name__)

pedidos = [{"id": e, "pedido": "Pedido "+str(e), "cod-vendedor": e, "nome-vendedor": "Vendedor "+str(e), 
"produto": "Produto "+str(e), "valor": 120.99, "desconto": 50.89} for e in range(1,11)]   


@app.route("/pedidos", methods=['GET'])
def get():
    return jsonify(pedidos)

@app.route("/pedido/<int:id>", methods=['GET'])
def get_one(id):
    filtro = [e for e in pedidos if e["id"] == id]
    if filtro:
        return jsonify(filtro[0])
    else:
        return jsonify({})

@app.route("/pedidos", methods=['POST'])
def post():
    global pedidos
    try:
        content = request.get_json()

        # gerar id
        ids = [e["id"] for e in pedidos]
        if ids:
            nid = max(ids) + 1
        else:
            nid = 1
        content["id"] = nid
        pedidos.append(content)
        return jsonify({"status":"OK", "msg":"pedido adicionado com sucesso"})
    except Exception as ex:
        return jsonify({"status":"ERRO", "msg":str(ex)})

@app.route("/pedido/<int:id>", methods=['DELETE'])
def delete(id):
    global pedidos
    try:
        pedidos = [e for e in pedidos if e["id"] != id]
        return jsonify({"status":"OK", "msg":"pedido removido com sucesso"})
    except Exception as ex:
        return jsonify({"status":"ERRO", "msg":str(ex)})

@app.route("/push/<string:key>/<string:token>", methods=['GET'])
def push(key, token):
	d = random.choice(pedidos)
	data = {
		"to": token,
		"notification" : {
			"title":d["nome"],
			"body":"Você tem um novo pedido em "+d['nome']
		},
		"data" : {
			"pedidoId":d['id']
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
