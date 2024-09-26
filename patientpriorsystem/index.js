const express = require("express");
const axios = require("axios");
const { assignBeds, patientQueue } = require("./controllers/queueController");

const app = express();
const port = 3000;

// Middleware to parse JSON requests
app.use(express.json());

// Endpoint to assign patients and process the queue
app.get("/test", async (req, res) => {

  // Fetch hospital and patient data from the respective endpoints
  const hospitalsResponse = await axios.get("http://localhost:8080/hospital");
  const patientsResponse = await axios.get("http://localhost:8080/patients");

  const hospitals = hospitalsResponse.data;
  const patients = patientsResponse.data;

  // Assign patients and process the queue
  await assignBeds(patients, hospitals);

  // Prepare the response to show queue state
  const queueOrder = patientQueue.map((patient) => patient.name);
});

// Home route
app.get("/", (req, res) => {
  res.send("Hospital Queue Management System is running!");
});
// Function to repeatedly call /test every minute
const runTestEveryMinute = () => {
  setInterval(async () => {
    try {
      const response = await axios.get("http://localhost:3000/test");
      console.log("Response from /test:", response.data);
    } catch (error) {
      console.error("Error calling /test:", error);
    }
  }, 15000); // 60000 milliseconds = 1 minute
};

// Start the test function
runTestEveryMinute();
// Start the Express server
app.listen(port, () => {
  console.log(`Server running at http://localhost:${port}`);
});
