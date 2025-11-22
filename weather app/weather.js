async function getWeather() {
  const city = document.getElementById("cityInput").value.trim();
  const resultBox = document.getElementById("result");

  if (city === "") {
    alert("Please enter a city name!");
    return;
  }

  const API_KEY = "YOUR_API_KEY";   // ← apni API key yaha daalo

  const url = `https://api.openweathermap.org/data/2.5/weather?q=${city}&units=metric&appid=${API_KEY}`;

  try {
    let response = await fetch(url);
    let data = await response.json();

    if (data.cod == "404") {
      resultBox.style.display = "block";
      resultBox.innerHTML = `<h3>City Not Found 😢</h3>`;
      return;
    }

    resultBox.style.display = "block";
    resultBox.innerHTML = `
      <h3>${data.name}, ${data.sys.country}</h3>
      <p><strong>Temperature:</strong> ${data.main.temp}°C</p>
      <p><strong>Weather:</strong> ${data.weather[0].main}</p>
      <p><strong>Humidity:</strong> ${data.main.humidity}%</p>
      <p><strong>Wind Speed:</strong> ${data.wind.speed} m/s</p>
    `;

  } catch (error) {
    resultBox.style.display = "block";
    resultBox.innerHTML = `<h3>Error fetching data!</h3>`;
  }
}
