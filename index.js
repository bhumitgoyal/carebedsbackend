// index.js
const express = require("express");
const {
  assignBeds,
  findAvailableHospital,
  assignPatientToHospital,
  patientQueue, // Import the patient queue
} = require("./controllers/queueController");
const { hospitals } = require("./data/hardcodedData");

const app = express();
const port = 3000;

// Dummy patient to assign
const dummyPatient = {
  id: 99,
  name: "Patient1",
  priority: "High",
  location: { lat: 40.7128, lon: -74.006 }, 
};

// Endpoint to assign a hospital to a dummy patient
app.get("/test", (req, res) => {
  // Clear previous queue data for fresh testing
  patientQueue.length = 0;

  // Assign patients and process the queue
  assignBeds();

  // Prepare the response to show queue state
  const queueOrder = patientQueue.map((patient) => patient.name); // Get names of queued patients
  const assignedPatients = hospitals.map((hospital) => {
    return {
      hospitalName: hospital.name,
      assignedPatients: hospital.assignedPatients || [], // Track assigned patients
    };
  });

  res.json({
    queueOrder,
    assignedPatients,
  });
});

// Periodically run the queue system (for actual patient data)
const intervalTime = 300000; // 5 minutes
setInterval(assignBeds, intervalTime);

// Home route
app.get("/", (req, res) => {
  res.send("Hospital Queue Management System is running!");
});

// Start the Express server
app.listen(port, () => {
  console.log(`Server running at http://localhost:${port}`);
});
