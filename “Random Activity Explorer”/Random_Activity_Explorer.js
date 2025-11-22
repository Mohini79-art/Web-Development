const card = document.getElementById("activityCard");
const btn = document.getElementById("newActivityBtn");
const typeSelect = document.getElementById("typeSelect");

// Fetch Activity Function
async function fetchActivity() {
  card.innerHTML = `<p class="loading">Loading...</p>`;

  let type = typeSelect.value;
  let url = "https://www.boredapi.com/api/activity";

  if (type) {
    url += `?type=${type}`;
  }

  try {
    let res = await fetch(url);
    let data = await res.json();

    if (data.error) {
      card.innerHTML = `<p class="error">No activity found for this type.</p>`;
      return;
    }

    card.innerHTML = `
      <h3>${data.activity}</h3>
      <p><strong>Type:</strong> ${data.type}</p>
      <p><strong>Participants:</strong> ${data.participants}</p>
    `;

  } catch (err) {
    card.innerHTML = `<p class="error">Oops! Something went wrong.</p>`;
  }
}

// Load activity on page load
fetchActivity();

// When user clicks button
btn.addEventListener("click", fetchActivity);
