const express = require("express");
const { assignBeds, patientQueue } = require("./controllers/queueController");
const { hospitals } = require("./data/hardcodedData");

const app = express();
const port = 3000;

// Middleware to parse JSON requests
app.use(express.json());

// Endpoint to assign patients and process the queue
app.get("/test", async (req, res) => {
  // Clear previous queue data for fresh testing
  patientQueue.length = 0;

  // Assign patients and process the queue
  await assignBeds(); // Ensure this is awaited

  // Prepare the response to show queue state
  const queueOrder = patientQueue.map((patient) => patient.name); // Get names of queued patients
  const assignedPatients = hospitals.map((hospital) => {
    return {
      hospitalName: hospital.name,
      admittedPatients: hospital.admittedPatients || [], // Updated here
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
