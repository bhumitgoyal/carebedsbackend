const patients = [
  {
    id: 1,
    name: "John Doe",
    age: 75,
    gender: "Male",
    bloodType: "O+",
    medicalCondition: "Hypertension",
    admissionType: "Urgent",
    medication: "Ibuprofen",
    testResults: "Normal",
    assigned: false,
    location: { lat: 40.7128, lon: -74.006 },
    priority: "",
  },
  {
    id: 2,
    name: "Jane Smith",
    location: { lat: 34.0522, lon: -118.2437 },
    age: 55,
    gender: "Female",
    bloodType: "B+",
    medicalCondition: "Asthma",
    admissionType: "Elective",
    medication: "Aspirin",
    testResults: "Abnormal",
    assigned: false,
    priority: "",
  },
  {
    id: 3,
    name: "Alice Johnson",
    location: { lat: 51.5074, lon: -0.1278 },
    age: 12,
    gender: "Female",
    bloodType: "B+",
    medicalCondition: "Diabetes",
    admissionType: "Elective",
    medication: "Aspirin",
    testResults: "Abnormal",
    assigned: false,
    priority: "",
  },
];

const hospitals = [
  {
    id: 1,
    name: "General Hospital",
    availableBeds: 1,
    location: { lat: 40.73061, lon: -73.935242 },
  },
  {
    id: 2,
    name: "City Clinic",
    availableBeds: 1,
    location: { lat: 34.0522, lon: -118.2437 },
  },
  {
    id: 3,
    name: "County Hospital",
    availableBeds: 1,
    location: { lat: 51.5074, lon: -0.1278 },
  },
];

module.exports = { patients, hospitals };
