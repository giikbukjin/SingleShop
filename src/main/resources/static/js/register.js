import { validateEmail, createNavbar } from "./useful-functions.js";

// 요소(element), input 혹은 상수
const fullNameInput = document.querySelector("#fullNameInput");
const emailInput = document.querySelector("#emailInput");
const passwordInput = document.querySelector("#passwordInput");
const passwordConfirmInput = document.querySelector("#passwordConfirmInput");
const submitButton = document.querySelector("#submitButton");

const fullNameError = document.createElement("p");
const emailError = document.createElement("p");
const passwordError = document.createElement("p");
const passwordConfirmError = document.createElement("p");

addAllElements();
addAllEvents();

// html에 요소를 추가하는 함수들을 묶어주어서 코드를 깔끔하게 하는 역할임.
async function addAllElements() {
  createNavbar();
}

// 여러 개의 addEventListener들을 묶어주어서 코드를 깔끔하게 하는 역할임.
function addAllEvents() {
  submitButton.addEventListener('submit', handleSubmit);
}

// 회원가입 진행
async function handleSubmit(e) {
  e.preventDefault();

  const fullName = fullNameInput.value;
  const email = emailInput.value;
  const password = passwordInput.value;
  const passwordConfirm = passwordConfirmInput.value;

  // 잘 입력했는지 확인
  const isFullNameValid = fullName.length >= 5 && fullName.length <= 15;
  const isEmailValid = validateEmail(email);
  const isPasswordValid = password.length >= 8 && password.length <= 15;
  const isPasswordSame = password === passwordConfirm;

  fullNameError.textContent = '';
  emailError.textContent = '';
  passwordError.textContent = '';
  passwordConfirmError.textContent = '';

  if (!isFullNameValid) {
    fullNameError.textContent = "유저이름은 2자 이상, 15자 이하여야 합니다.";
    fullNameError.classList.add("error-message");
    fullNameInput.parentNode.appendChild(fullNameError);
  }

  if (!isEmailValid) {
    emailError.textContent = "이메일 형식이 맞지 않습니다.";
    emailError.classList.add("error-message");
    emailInput.parentNode.appendChild(emailError);
  }

  if (!isPasswordValid) {
    passwordError.textContent = "비밀번호는 8자 이상, 15자 이하여야 합니다.";
    passwordError.classList.add("error-message");
    passwordInput.parentNode.appendChild(passwordError);
  }

  if (!isPasswordSame) {
    passwordConfirmError.textContent = "비밀번호가 일치하지 않습니다.";
    passwordConfirmError.classList.add("error-message");
    passwordConfirmInput.parentNode.appendChild(passwordConfirmError);
  }
}
