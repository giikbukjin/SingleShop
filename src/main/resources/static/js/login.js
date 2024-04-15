import {
  blockIfLogin,
  createNavbar
} from "./useful-functions.js";

// 요소(element), input 혹은 상수
const emailInput = document.querySelector("#emailInput");
const passwordInput = document.querySelector("#passwordInput");
const submitButton = document.querySelector("#submitButton");

blockIfLogin();
addAllElements();

// html에 요소를 추가하는 함수들을 묶어주어서 코드를 깔끔하게 하는 역할임.
async function addAllElements() {
  createNavbar();
}

  document.addEventListener('DOMContentLoaded', function () {
    const loginForm = document.getElementById('loginForm');
    const emailInput = document.getElementById('emailInput');
    const passwordInput = document.getElementById('passwordInput');
    const emailError = document.getElementById('emailError');
    const passwordError = document.getElementById('passwordError');

    loginForm.addEventListener('submit', function (event) {
      event.preventDefault(); // Prevent form submission

      // Reset error messages
      emailError.textContent = '';
      passwordError.textContent = '';

      // Validate email
      if (!validateEmail(emailInput.value.trim())) {
        emailError.textContent = '유효한 이메일을 입력하세요.';
        return;
      }

      // Validate password
      if (!validatePassword(passwordInput.value.trim())) {
        passwordError.textContent = '비밀번호는 8자 이상, 15자 이하여야 합니다.';
        return;
      }

      // If both email and password are valid, submit the form
      loginForm.submit();
    });

    // Function to validate email
    function validateEmail(email) {
      const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      return re.test(String(email).toLowerCase());
    }

    // Function to validate password
    function validatePassword(password) {
      return password.length >= 8 && password.length <= 15;
    }
  });