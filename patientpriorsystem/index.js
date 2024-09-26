const express = require("express");
const axios = require("axios");
const { assignBeds, patientQueue } = require("./controllers/queueController");

const app = express();
const port = 3000;

// Middleware to parse JSON requests
app.use(express.json());

// Endpoint to assign patients and process the queue
app.get("/test", async (req, res) => {
  // Clear previous queue data for fresh testing
  patientQueue.length = 0;

  // Fetch hospital and patient data from the respective endpoints
  const hospitalsResponse = await axios.get("http://localhost:8080/hospital");
  const patientsResponse = await axios.get("http://localhost:8080/patients");

  const hospitals = hospitalsResponse.data;
  const patients = patientsResponse.data;

  // Assign patients and process the queue
  await assignBeds(patients, hospitals);

  // Prepare the response to show queue state
  const queueOrder = patientQueue.map((patient) => patient.name);
  // After bed assignments, update the backend
  for (const hospital of hospitals) {
    await axios.put(`http://localhost:8080/hospital/${hospital.id}`, hospital);
  }

  for (const patient of patients) {
    await axios.put(`http://localhost:8080/patients/${patient.id}`, patient);
  }

  const assignedPatients = hospitals.map((hospital) => {
    //axios.put('http://localhost:8080/patients', patients);
    //axios.put('http://localhost:8080/hospital', hospitals);
    return {
      hospitalName: hospital.name,
      admittedPatients: hospital.admittedPatients,
    };
  });

  res.json({
    queueOrder,
    assignedPatients,
  });  
});

// Home route
app.get("/", (req, res) => {
  res.send("Hospital Queue Management System is running!");
});

// Start the Express server
app.listen(port, () => {
  console.log(`Server running at http://localhost:${port}`);
});
