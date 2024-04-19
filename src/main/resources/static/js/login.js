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
    event.preventDefault();

    emailError.textContent = '';
    passwordError.textContent = '';

    if (!validateEmail(emailInput.value.trim())) {
      emailError.textContent = '유효한 이메일을 입력하세요.';
      return;
    }

    if (!validatePassword(passwordInput.value.trim())) {
      passwordError.textContent = '비밀번호는 8자 이상, 15자 이하여야 합니다.';
      return;
    }

    fetch(loginForm.action, {
      method: 'POST',
      body: new FormData(loginForm),
    })
        .then(response => {
          if (response.ok) {
            window.location.href = response.url;
          } else {
            return response.json();
          }
        })
        .then(errorData => {
          if (errorData && errorData.message) {
            alert(errorData.message);
          }
        })
        .catch(error => {
          console.error('Error occurred during login:', error);
          alert('로그인 중 오류가 발생했습니다. 다시 시도해주세요.');
        });
  });

  function validateEmail(email) {
    const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return re.test(String(email).toLowerCase());
  }

  function validatePassword(password) {
    return password.length >= 8 && password.length <= 15;
  }
});