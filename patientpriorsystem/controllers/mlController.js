const axios = require("axios");

// Function to fetch priority for a patient
async function getPatientPriority(patient) {
  try {
    getprior = {
      Age: patient.age,
      Gender: patient.gender,
      "Blood Type": patient.bloodType,
      "Medical Condition": patient.medicalCondition,
      "Admission Type": patient.admissionType,
      Medication: patient.medication,
      "Test Results": patient.testResults,
    };
    const response = await axios.post(
      "http://127.0.0.1:8000/predict",
      getprior
    );
    await axios.post(
      `http://127.0.0.1:8080/patients/${patient.id}/priority/${response.data.prediction}`
    );
    return response.data;
  } catch (error) {
    console.error("Error fetching priority:", error);
    return "Low Priority";
  }
}

module.exports = { getPatientPriority };
