import React from "react";
import StudentCard from "./StudentCard";

export default function StudentList(props) {
  return (
    <div>
      {props.list.map((stu, index) => (
        <StudentCard key={index} student={stu} />
      ))}
    </div>
  );
}
