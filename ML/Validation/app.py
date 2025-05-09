from flask import Flask, request, jsonify
import joblib

app = Flask(__name__)

# Charger le modèle et le vectorizer
model = joblib.load("logistic_regression.pkl")
vectorizer = joblib.load("vectorizer.pkl")
@app.route("/analyze-multiple", methods=["POST"])
def analyze_multiple_comments():
    data = request.get_json()
    comments = data.get("comments", [])

    if not comments:
        return jsonify({"error": "Liste vide"}), 400

    vect = vectorizer.transform(comments)
    predictions = model.predict(vect)

    count_positive = int((predictions == 1).sum())
    count_negative = int((predictions == 0).sum())

    return jsonify({
        "positif": count_positive,
        "negatif": count_negative
    })

@app.route("/analyze", methods=["POST"])
def analyze_comment():
    data = request.get_json()
    comment = data.get("comment")

    if not comment:
        return jsonify({"error": "Commentaire manquant"}), 400

    vect = vectorizer.transform([comment])
    prediction = model.predict(vect)[0]

    result = "positif" if prediction == 1 else "négatif"
    return jsonify({"result": result})

if __name__ == "__main__":
    app.run(debug=True, port=5000)
