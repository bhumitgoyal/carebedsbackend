const express = require("express");
const axios = require('axios');
const { assignBeds, patientQueue } = require("./controllers/queueController");

const app = express();
const port = 3000;

// Middleware to parse JSON requests
app.use(express.json());

// Endpoint to assign patients and process the queue
app.get("/test", async (req, res) => {
  // Clear previous queue data for fresh testing
  patientQueue.length = 0;
  const hospitalsResponse = await axios.get("http://localhost:8080/hospital");
  const hospitals = hospitalsResponse.data;
  // Assign patients and process the queue
  await assignBeds();

  // Prepare the response to show queue state
  const queueOrder = patientQueue.map((patient) => patient.name); // Get names of queued patients
  console.log(hospitals);
  const assignedPatients = hospitals.map((hospital) => {
    return {
      hospitalName: hospital.name,
      admittedPatients: hospital.admittedPatients
        ? hospital.admittedPatients.map((patient) => {
            return {
              name: patient.name,
              age: patient.age,
              priority: patient.priority,
              address: patient.address,
              email: patient.email,
              phoneNumber: patient.phoneNumber,
              medicalCondition: patient.medicalCondition,
              admissionType: patient.admissionType,
              medication: patient.medication,
              testResults: patient.testResults,
              registeredBed: patient.registeredBed,
            };
          })
        : [],
    };
  });

  console.log("Assigned Patients:", JSON.stringify(assignedPatients, null, 2)); // Log assigned patients

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
