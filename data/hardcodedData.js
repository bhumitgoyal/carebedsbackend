const patients = [
  {
    id: 1,
    name: "John Doe",
    priority: "High",
        location: { lat: 40.7128, lon: -74.006 },
        assigned: false,
  },
  {
    id: 2,
    name: "Jane Smith",
    priority: "Medium",
      location: { lat: 34.0522, lon: -118.2437 },
      assigned: false,
  },
  {
    id: 3,
    name: "Alice Johnson",
    priority: "Low",
      location: { lat: 51.5074, lon: -0.1278 },
      assigned: false,
  },
  {
    id: 99,
    name: "Patient1",
    priority: "High",
      location: { lat: 40.7128, lon: -74.006 },
      assigned: false,
  },
  {
    id: 99,
    name: "Patient2",
    priority: "Medium",
      location: { lat: 40.7128, lon: -74.006 },
      assigned: false,
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
