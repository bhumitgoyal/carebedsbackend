const { calculateDistance } = require("../utils/distanceUtils");
const { getPatientPriority } = require("./mlController");

// Queue for patients who can't be assigned immediately
let patientQueue = [];

// Main function to assign patients to hospitals or add them to the queue
// Assign patients and process the queue
const assignBeds = async (patients, hospitals) => {
  await assignPrioritiesToPatients(patients); // Fetch and assign priorities to ALL patients

  const sortedPatients = sortPatientsByPriority(patients); // Now sort them by priority
  for (let patient of sortedPatients) {
    if (!patient.assigned) {
      const hospital = findAvailableHospital(patient, hospitals);
      if (hospital) {
        assignPatientToHospital(patient, hospital);
      } else {
        queuePatient(patient);
      }
    }
  }

  processPatientQueue(hospitals); // Process queued patients if beds are available
};

// Fetch and assign priorities to each patient
const assignPrioritiesToPatients = async (patients) => {
  for (let patient of patients) {
    const priority = await getPatientPriority(patient);
    if (priority && priority.prediction) {
      patient.priority = priority.prediction;
    } else {
      console.error(`Error: No priority assigned for patient ${patient.name}`);
    }
  }
};

// Sort patients based on priority (High > Medium > Low)
const sortPatientsByPriority = (patients) => {
  return patients.sort(
    (a, b) => priorityLevel(b.priority) - priorityLevel(a.priority)
  );
};

// Convert priority string to numeric data
const priorityLevel = (priority) => {
  if (priority === "High Priority") return 3;
  if (priority === "Medium Priority") return 2;
  return 1;
};

// Assign the patient to a hospital
const assignPatientToHospital = (patient, hospital) => {
  hospital.availableBeds--;
  if (!hospital.admittedPatients) {
    hospital.admittedPatients = []; // Ensure admittedPatients is initialized
  }

  // Log patient details to ensure priority is set before adding to hospital
  console.log("Assigning patient:", patient);

  patient.assigned = true;
  hospital.admittedPatients.push(patient); // Add patient to admitted list

  console.log(`Assigned patient ${patient.name} to hospital ${hospital.name}`);
};

// Find the nearest hospital with available beds
const findAvailableHospital = (patient, hospitals) => {
  let closestHospital = null;
  let shortestDistance = Infinity;

  hospitals.forEach((hospital) => {
    const distance = calculateDistance(patient.location, hospital.location);
    if (hospital.availableBeds > 0 && distance < shortestDistance) {
      closestHospital = hospital;
      shortestDistance = distance;
    }
  });

  return closestHospital;
};

// Add a patient to the queue if no hospitals are available
const queuePatient = (patient) => {
  patientQueue.push(patient);
  console.log(`Queued patient ${patient.name}`);
};

// Try to process the queue periodically
const processPatientQueue = (hospitals) => {
  while (patientQueue.length > 0) {
    const patient = patientQueue.shift(); // Take patient from queue
    console.log(`Processing patient ${patient.name}`);

    const hospital = findAvailableHospital(patient, hospitals);

    if (hospital) {
      assignPatientToHospital(patient, hospital); // Assign the patient if a hospital is available
      console.log(
        `Assigned patient ${patient.name} to hospital ${hospital.name}`
      );
    } else {
      console.log(
        `No available hospital for patient ${patient.name}. Keeping in queue.`
      );
      patientQueue.unshift(patient); // Put patient back if no beds are available
      break; // Exit the loop as the queue has not changed
    }
  }
};

module.exports = {
  assignBeds,
  findAvailableHospital,
  assignPatientToHospital,
  patientQueue,
};
