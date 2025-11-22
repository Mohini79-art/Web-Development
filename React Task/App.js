import React from "react";
import StudentList from "./StudentList";

export default function App() {
  const students = [
    { name: "Amit", grade: "A" },
    { name: "Sonu", grade: "B" },
    { name: "Priya", grade: "A+" },
    { name: "Ravi", grade: "C" }
  ];

  return (
    <div>
      <h2>Students List</h2>
      <StudentList list={students} />
    </div>
  );
}
