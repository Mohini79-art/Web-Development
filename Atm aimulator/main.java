<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>ATM Simulator</title>

<style>
body{
  margin:0;
    height:100vh;
    font-family: Arial, sans-serif;
 justify-content:center;
  display:flex;
    justify-content:center;
    align-items:center;
    background:
      linear-gradient(rgba(0,0,0,0.6), rgba(0,0,0,0.6)),
      url("https://images.unsplash.com/photo-1518770660439-4636190af475");
    background-size: cover;
    background-position: center;
    background-repeat: no-repeat;
}

.container{
  width:400px;
  background:rgba(0,0,0,0.85);
  padding:25px;
  border-radius:16px;
  color:white;
  box-shadow:0 20px 50px black;
}

h2{ text-align:center; }

button, input{
  width:100%;
  padding:12px;
  margin-top:10px;
  border:none;
  border-radius:6px;
}

button{
  background:#00c2d1;
  color:white;
  cursor:pointer;
  font-size:16px;
}

button:hover{ background:#009aa6; }

.hidden{ display:none; }

.msg{
  text-align:center;
  margin-top:10px;
  color:#ffd700;
  font-size:14px;
}

.history{
  max-height:120px;
  overflow:auto;
  font-size:13px;
  background:#111;
  padding:10px;
  border-radius:8px;
  margin-top:10px;
}
</style>
</head>

<body>

<div class="container">

<!-- SCREEN 1 -->
<div id="screen1">
  <h2 id="insertTxt">please insert your card </h2>
  <button onclick="insertCard()">OK</button>
</div>

<!-- SCREEN 2 -->
<div id="screen2" class="hidden">
  <h2 id="langTxt"></h2>
  <button onclick="setLang('hi')">हिंदी</button>
  <button onclick="setLang('en')">English</button>
</div>

<!-- SCREEN 3 -->
<div id="screen3" class="hidden">
  <h2 id="accTxt"></h2>
  <button onclick="nextScreen(4)">Savings</button>
  <button onclick="nextScreen(4)">Current</button>
</div>

<!-- SCREEN 4 -->
<div id="screen4" class="hidden">
  <h2 id="pinTxt"></h2>
  <input type="password" id="pin" placeholder="PIN">
  <button onclick="login()">Login</button>
  <div class="msg" id="pinMsg"></div>
</div>

<!-- SCREEN 5 -->
<div id="screen5" class="hidden">
  <h2 id="welcomeTxt"></h2>

  <button onclick="showBalance()" id="btnBal"></button>
  <button onclick="deposit()" id="btnDep"></button>
  <button onclick="withdraw()" id="btnWith"></button>
  <button onclick="showHistory()" id="btnHist"></button>
  <button onclick="exitATM()" id="btnExit"></button>

  <div class="msg" id="menuMsg"></div>
  <div class="history hidden" id="historyBox"></div>
</div>

</div>

<script>
    let pinAttempts = 3;
let cardAttempts = 0;
let dailyWithdrawn = 0;
let DAILY_LIMIT = 10000;
let accountLocked = false;
let lastDate = new Date().toDateString();

let currentUser = null;
let users = [
  {
    username: "mohini",
    pin: "1234",
    balance: 5000,
    pinAttempts: 3,
    cardAttempts: 0,
    dailyWithdrawn: 0,
    locked: false,
    lastDate: new Date().toDateString(),
    history: []
  },
  {
    username: "rahul",
    pin: "1111",
    balance: 8000,
    pinAttempts: 3,
    cardAttempts: 0,
    dailyWithdrawn: 0,
    locked: false,
    lastDate: new Date().toDateString(),
    history: []
  }
];


/* ===== TEXT ===== */
const T = {
 en:{
  insert:"Please insert your debit card",
  lang:"Select Language",
  acc:"Select Account Type",
  pin:"Enter your PIN",
  welcome:"Welcome to ATM",
  bal:"Check Balance",
  dep:"Deposit",
  with:"Withdraw",
  hist:"Transaction History",
  exit:"Exit",
  wrong:"Wrong PIN",
 },
 hi:{
  insert:"कृपया अपना डेबिट कार्ड डालें",
  lang:"भाषा चुनें",
  acc:"खाता प्रकार चुनें",
  pin:"अपना पिन दर्ज करें",
  welcome:"ATM में स्वागत है",
  bal:"बैलेंस देखें",
  dep:"जमा करें",
  with:"निकासी",
  hist:"लेन-देन इतिहास",
  exit:"बाहर जाएँ",
  wrong:"गलत पिन",
 }
};

/* ===== UI ===== */
function updateText(){
 insertTxt.innerText=T[lang].insert;
 langTxt.innerText=T[lang].lang;
 accTxt.innerText=T[lang].acc;
 pinTxt.innerText=T[lang].pin;
 welcomeTxt.innerText=T[lang].welcome;
 btnBal.innerText=T[lang].bal;
 btnDep.innerText=T[lang].dep;
 btnWith.innerText=T[lang].with;
 btnHist.innerText=T[lang].hist;
 btnExit.innerText=T[lang].exit;
}
function checkNewDay(user){
  let today = new Date().toDateString();
  if(today !== user.lastDate){
    user.pinAttempts = 3;
    user.cardAttempts = 0;
    user.dailyWithdrawn = 0;
    user.locked = false;
    user.lastDate = today;
  }
}
function insertCard(){
  let uname = prompt("Enter username (card holder name)");

  currentUser = users.find(u => u.username === uname);

  if(!currentUser){
    alert("Invalid Card");
    return;
  }

  checkNewDay(currentUser);

  if(currentUser.cardAttempts >= 3){
    alert("Card blocked for today. Try tomorrow.");
    return;
  }

  currentUser.cardAttempts++;
  nextScreen(2);
}

function nextScreen(n){
 document.querySelectorAll("[id^=screen]").forEach(s=>s.classList.add("hidden"));
 document.getElementById("screen"+n).classList.remove("hidden");
}

function setLang(l){
 lang=l;
 updateText();
 nextScreen(3);
}


/* ===== ATM ACTIONS ===== */
function showBalance(){
  menuMsg.innerText = "₹ " + currentUser.balance;
}
function withdraw(){
  let amt = Number(prompt("Enter amount"));

  if(currentUser.dailyWithdrawn + amt > 10000){
    menuMsg.innerText = "Daily limit exceeded!";
    return;
  }

  if(amt > 0 && amt <= currentUser.balance){
    currentUser.balance -= amt;
    currentUser.dailyWithdrawn += amt;

    let d = new Date().toLocaleString();
    currentUser.history.unshift(
      `${d} | Withdraw ₹${amt} | Balance ₹${currentUser.balance}`
    );

    showBalance();
  }
}


function deposit(){
  let amt = Number(prompt("Enter amount"));
  if(amt > 0){
    currentUser.balance += amt;

    let d = new Date().toLocaleString();
    currentUser.history.unshift(
      `${d} | Deposit ₹${amt} | Balance ₹${currentUser.balance}`
    );

    showBalance();
  }
}

function addHistory(type,amt){
 let d=new Date().toLocaleString();
 history.unshift(`${d} | ${type}: ₹${amt} | Bal: ₹${balance}`);
}
function showBalance(){
  menuMsg.innerText = "Available Balance: ₹" + currentUser.balance;
}



function showHistory(){
  let box = document.getElementById("historyBox");
  box.classList.remove("hidden");
  box.innerHTML = "";

  if(currentUser.history.length === 0){
    box.innerText = "No transactions yet";
    return;
  }

  currentUser.history.forEach(h => {
    let d = document.createElement("div");
    d.innerText = h;
    box.appendChild(d);
  });
}

function exitATM(){
  currentUser = null;
  pin.value = "";
  menuMsg.innerText = "";
  historyBox.classList.add("hidden");
  nextScreen(1);
}
function login() {
  checkNewDay(currentUser); // pass the user!

  if(currentUser.locked){
    pinMsg.innerText = "Account Locked! Try tomorrow.";
    setTimeout(() => {
      pinMsg.innerText = "";
      pin.value = "";
      nextScreen(1);
    }, 2000);
    return;
  }

  if(pin.value === currentUser.pin){
    currentUser.pinAttempts = 3; // reset attempts
    pin.value = "";
    pinMsg.innerText = "";
    nextScreen(5); // go to menu
  } else {
    currentUser.pinAttempts--;
    pinMsg.innerText = "Wrong PIN. Attempts left: " + currentUser.pinAttempts;

    if(currentUser.pinAttempts === 0){
      currentUser.locked = true;
      pinMsg.innerText = "Account Locked! Try tomorrow.";
      setTimeout(() => {
        pinMsg.innerText = "";
        pin.value = "";
        nextScreen(1);
      }, 2500);
    }
  }
}


updateText();
</script>

</body>
</html>
