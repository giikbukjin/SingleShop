document.getElementById('registerUserForm').addEventListener('submit', function(event) {
    const nameInput = document.getElementById('fullNameInput');
    const emailInput = document.getElementById('emailInput');
    const passwordInput = document.getElementById('passwordInput');
    const passwordConfirmInput = document.getElementById('passwordConfirmInput');
    const nameErrorMessage = document.getElementById('nameErrorMessage');
    const emailErrorMessage = document.getElementById('emailErrorMessage');
    const passwordErrorMessage = document.getElementById('passwordErrorMessage');
    const confirmPasswordErrorMessage = document.getElementById('confirmPasswordErrorMessage');
    let isValid = true;

    // 이름 길이 유효성 검사 (5자 이상, 15자 이하)
    if (nameInput.value.length < 5 || nameInput.value.length > 15) {
        nameErrorMessage.textContent = '이름은 5자 이상, 15자 이하로 입력해주세요.';
        isValid = false;
    } else {
        nameErrorMessage.textContent = '';
    }

    // 이메일 형식 유효성 검사
    if (!isValidEmail(emailInput.value)) {
        emailErrorMessage.textContent = '올바른 이메일 형식이 아닙니다.';
        isValid = false;
    } else {
        emailErrorMessage.textContent = '';
    }

    // 비밀번호 유효성 검사 (최소 6자 이상)
    if (passwordInput.value.length < 6) {
        passwordErrorMessage.textContent = '비밀번호는 최소 6자 이상이어야 합니다.';
        isValid = false;
    } else {
        passwordErrorMessage.textContent = '';
    }

    // 비밀번호 확인 일치 여부 확인
    if (passwordInput.value !== passwordConfirmInput.value) {
        confirmPasswordErrorMessage.textContent = '비밀번호가 일치하지 않습니다.';
        isValid = false;
    } else {
        confirmPasswordErrorMessage.textContent = '';
    }

    // 폼 제출 방지
    if (!isValid) {
        event.preventDefault();
    }
});

// 이메일 형식 검사 함수
function isValidEmail(email) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
}