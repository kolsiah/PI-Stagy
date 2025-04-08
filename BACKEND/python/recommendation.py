import spacy
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
import mysql.connector

# Load NLP model
nlp = spacy.load("en_core_web_sm")

# Connect to MySQL database without a password
db = mysql.connector.connect(
    host="localhost",
    user="root",       # No password field required
    database="entreprise"
)

# Fetch entreprises
cursor = db.cursor(dictionary=True)
cursor.execute("SELECT id_entreprise, nom_entreprise, secteur_activite FROM entreprise")
entreprises = cursor.fetchall()

# Extract text data for recommendations
entreprise_texts = [f"{e['nom_entreprise']} {e['secteur_activite']}" for e in entreprises]

# Vectorize the text using TF-IDF
vectorizer = TfidfVectorizer().fit_transform(entreprise_texts)
similarity_matrix = cosine_similarity(vectorizer)

# Recommendation Function
def recommend_companies(target_id):
    target_index = next(i for i, e in enumerate(entreprises) if e['id_entreprise'] == target_id)
    similar_indices = similarity_matrix[target_index].argsort()[-6:-1][::-1]

    recommendations = [entreprises[i] for i in similar_indices]
    return recommendations

# Example Usage
if __name__ == "__main__":
    import sys
    try:
        target_id = int(sys.argv[1])  # Example usage for CLI call
        recommended_companies = recommend_companies(target_id)
        if not recommended_companies:
            print("No similar companies found.")
        else:
            for company in recommended_companies:
                print(f"Recommended: {company['nom_entreprise']} - {company['secteur_activite']}")
    except Exception as e:
        import traceback
        print("Error Details:")
        print(traceback.format_exc())  # Full error traceback for better debugging