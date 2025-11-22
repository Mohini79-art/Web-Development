import React from "react";

export default function StudentCard(props) {
  return (
    <div style={{
      border: "1px solid #ccc",
      padding: "10px",
      margin: "10px",
      borderRadius: "6px"
    }}>
      <p><strong>Name:</strong> {props.student.name}</p>
      <p><strong>Grade:</strong> {props.student.grade}</p>
    </div>
  );
}
