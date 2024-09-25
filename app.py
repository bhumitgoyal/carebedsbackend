from flask import Flask, request, jsonify
import joblib
import pandas as pd

app = Flask(__name__)

# Load your model
model = joblib.load('data/patient_priority_model.pkl')

# Define categorical columns
categorical_columns = ['Gender', 'Blood Type', 'Medical Condition', 'Admission Type', 'Medication', 'Test Results']

# Load the label encoders for each categorical column
label_encoders = {
    'Gender': joblib.load('./data/label_encoder_Gender.pkl'),
    'Blood Type': joblib.load('./data/label_encoder_Blood Type.pkl'),
    'Test Results': joblib.load('./data/label_encoder_Test Results.pkl'),
    'Medication': joblib.load('./data/label_encoder_Medication.pkl'),
    'Medical Condition': joblib.load('./data/label_encoder_Medical Condition.pkl'),
    'Admission Type': joblib.load('./data/label_encoder_Admission Type.pkl')
}

# Endpoint to get predictions
@app.route('/predict', methods=['POST'])
def predict():
    data = request.json  # Expecting JSON input
    
    # Convert input into a DataFrame
    df = pd.DataFrame([data])

    # Preprocess data (apply label encoding)
    for column in categorical_columns:
        if column in df.columns and column in label_encoders:
            df[column] = label_encoders[column].transform(df[column])

    # Use the model to predict
    prediction = model.predict(df)
    return jsonify({'prediction': prediction[0]})

if __name__ == '__main__':
    app.run(debug=True, port=8000)
