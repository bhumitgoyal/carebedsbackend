const { patients, hospitals } = require("../data/hardcodedData");
const { calculateDistance } = require("../utils/distanceUtils");

// model flow here:
// we get patient and hospital data first
// then we sort the patients in queue according to priority
// we try to assign patients to hospital that  are nearest to them.
//if no hospitals available we make a patient queue
//process the queue again if hospital become available again

let patientQueue = []; // Queue for patients who can't be assigned immediately

// Main function to assign patients to hospitals or add them to the queue
const assignBeds = () => {
  const sortedPatients = sortPatientsByPriority(patients);

  for (let patient of sortedPatients) {
    const hospital = findAvailableHospital(patient, hospitals);
    if (!patients.assigned) {
      if (hospital) {
        assignPatientToHospital(patient, hospital);
      } else {
        queuePatient(patient);
      }
    }
  }

  processPatientQueue(hospitals); // Process queued patients if beds are available
};

// Sort patients based on priority (High > Medium > Low)
const sortPatientsByPriority = (patients) => {
  return patients.sort(
    (a, b) => priorityLevel(b.priority) - priorityLevel(a.priority)
  );
};

// Convert priority string to numeric level
const priorityLevel = (priority) => {
  return priority === "High" ? 3 : priority === "Medium" ? 2 : 1;
};

// Assign the patient to a hospital
const assignPatientToHospital = (patient, hospital) => {
  hospital.availableBeds--;
  hospital.assignedPatients = hospital.assignedPatients || [];
    hospital.assignedPatients.push(patient);
    patient.assigned = true;
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
    console.log(`Processing patient ${patient.name}`); // Debugging line

    const hospital = findAvailableHospital(patient, hospitals);

    if (hospital) {
      assignPatientToHospital(patient, hospital); // Assign the patient if a hospital is available
      console.log(
        `Assigned patient ${patient.name} to hospital ${hospital.name}`
      );
    } else {
      // If no hospital is available, log and do not re-queue the patient
      console.log(
        `No available hospital for patient ${patient.name}. Keeping in queue.`
      );
      patientQueue.unshift(patient); // Put patient back if no beds are available
      break; // Exit the loop as the queue has not changed
    }
  }
};

// Export all necessary functions
module.exports = {
  assignBeds,
  findAvailableHospital,
  assignPatientToHospital,
  patientQueue,
};
