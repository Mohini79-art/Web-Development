function addTask() {
  let input = document.getElementById("taskInput");
  let taskText = input.value.trim();

  if (taskText === "") {
    alert("Please enter a task!");
    return;
  }

  let ul = document.getElementById("taskList");

  let li = document.createElement("li");

  let span = document.createElement("span");
  span.textContent = taskText;

  let delBtn = document.createElement("button");
  delBtn.textContent = "Delete";
  delBtn.className = "delete-btn";

  delBtn.onclick = function () {
    li.remove();
  };

  li.appendChild(span);
  li.appendChild(delBtn);

  ul.appendChild(li);

  input.value = "";
}
