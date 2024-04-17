// 원래 쓰려던 isAdmin 방식
// const isAdmin = {
//    checkAd: false,
//  }
//  async function checkAdmin() {
//    try {
//        const res = await fetch("/api/users/admin-check");
//        const result = await res.json();
//
//    } catch (error) {
//        console.error("Admin check failed:", isAdmin.checkAd);
//    }
//
//  }
//  console.log("isAdmin, if 구문 밖에서 :", isAdmin.checkAd);
//

export const createNavbar = () => {
  const pathname = window.location.pathname;

// account/security/**?

  switch (pathname) {
    case "/home":
      addNavElements("admin register login cart account logout");
      break;
    case "/account/orders":
      addNavElements("admin account logout");
      break;
    case "/account/security/*":
      addNavElements("admin account logout");
      break;
    case "/account/signout":
      addNavElements("admin account logout");
      break;
    case "/account":
      addNavElements("admin cart logout");
      break;
    case "/admin/orders":
      addNavElements("admin account logout");
      break;
    case "/admin/users":
      addNavElements("admin logout");
      break;
    case "/admin":
      addNavElements("logout");
      break;
    case "/cart":
      addNavElements("admin register login account logout");
      break;
    case "/category/add":
      addNavElements("admin account productAdd logout");
      break;
    case "/auth/login":
      addNavElements("register");
      break;
    case "/order/complete":
      addNavElements("admin account logout");
      break;
    case "/order":
      addNavElements("admin account logout");
      break;
    case "/product/add":
      addNavElements("admin account logout");
      break;
    case "/product/detail":
      addNavElements("admin register login account logout");
      break;
    case "/product/list":
      addNavElements("admin register login account logout");
      break;
    case "/auth/signup":
      addNavElements("login");
      break;

    default:
  }
};

// navbar ul 태그에, li 태그들을 삽입함)
const addNavElements = (keyString) => {
  const keys = keyString.split(" ");
  const container = document.querySelector("#navbar");

  function getCookie(name) {
    const cookies = document.cookie.split(';');
        for (const cookie of cookies) {
            const [cookieName, cookieValue] = cookie.trim().split('=');
                if (cookieName === name) {
                    return cookieValue;
                }
        }
    return null;
  }

  const isLogin = getCookie("Authorization") ? true : false;

  // 로그인 안 된 상태에서만 보이게 될 navbar 요소들
  const itemsBeforeLogin = {
    register: '<li><a href="/auth/signup">회원가입</a></li>',
    login: '<li><a href="/auth/login">로그인</a></li>',
  };

// 여기랑 밑에 주석 풀어주셔야 카카오 로그아웃 사용 가능합니다. 다양한 방법 존재. HTTPONLY 속성의 쿠키를 불러올 수 있는 방법?
//  let logoutPath;
//
//      function cookieCheck(name) {
//            const cookies = document.cookie.split(';');
//                for (const cookie of cookies) {
//                    const [cookieName, cookieValue] = cookie.trim().split('=');
//                        if (cookieName === name) {
//                            return true;
//                        }
//                }
//            return null;
//          }
//
//          function getCookieName(name) {
//            let matches = document.cookie.match(new RegExp(
//              "(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
//            ));
//            return matches ? decodeURIComponent(matches[1]) : undefined;
//          }
////
//  console.log(getCookieName("kakao"));
//
//  if (cookieCheck("kakao")) { logoutPath = '<li><a href="https://kauth.kakao.com/oauth/logout?client_id=1fa06f949c45fbf2a3e893c494263777&logout_redirect_uri=http://localhost:8080/auth/logout" id="logout">로그아웃</a></li>' }
//      else { logoutPath = '<li><a href="/auth/logout" id="logout">로그아웃</a></li>' };
//
//      console.log(logoutPath);

  // 로그인 완료된 상태에서만 보이게 될 navbar 요소들
  const itemsAfterLogin = {
    account: '<li><a href="/account">계정관리</a></li>',
    logout: '<li><a href="/auth/logout" id="logout">로그아웃</a></li>',
//    logout: logoutPath,
    productAdd: '<li><a href="/product/add">제품 추가</a></li>',
    categoryAdd: '<li><a href="/category/add">카테고리 추가</a></li>',
    cart: '<li><a href="/cart"><span class="icon"><i class="fas fa-cart-shopping"></i></span>카트</a></li>',
  };

  const itemsForAdmin = {
    admin: '<li><a href="/admin">관리 페이지</a></li>',
  };

  // 로그아웃 요소만 유일하게, 클릭 이벤트를 필요로 함 (나머지는 href로 충분함)
  const logoutScript = document.createElement("script");
  logoutScript.innerText = `
      const logoutElem = document.querySelector('#logout'); 
      
      if (logoutElem) {
        logoutElem.addEventListener('click', () => {
          window.location.href = '/auth/logout';
//          window.location.reload(); 혹시나, 사이트에 그대로 남아있는 경우를 위해서
        });
      }
  `;


// 비동기 함수로 하니까 isAdmin값을 넣을 수가 없습니다. 안 되서 아래 방법 사용했는데, 함수 안에서는 true로 바뀌나,
// isAdmin이 밖에서는 내용이 바뀌지 않습니다. 왜 그럴까요?

  var isAdmin = false;

    var xhr = new XMLHttpRequest();
    xhr.open('GET', '/api/users/admin-check', true); // GET 요청을 보냄
    xhr.onreadystatechange = function () {
      if (xhr.readyState === XMLHttpRequest.DONE) { // 요청이 완료되면
        if (xhr.status === 200) { // 요청이 성공했을 때
          if (xhr.responseText == "{\"status\": \"success\"}") {
              isAdmin = true;
              console.log("if 안쪽의 isAdmin 값: ", isAdmin);
          }

        } else {
          console.error('Request failed with status:', xhr.status); // 요청이 실패했을 때
        }
      }
    };
    xhr.send(); // 요청 보내기

    console.log("요청 보내고 난 후의 is Admin: ", isAdmin);

  let items = "";
  for (const key of keys) {
    if (isAdmin) {
      items += itemsForAdmin[key] ?? "";
    }
    if (isLogin) {
      items += itemsAfterLogin[key] ?? "";
    } else {
      items += itemsBeforeLogin[key] ?? "";
    }
  }

  // items에 쌓은 navbar 요소들 문자열을 html에 삽입함.
  container.insertAdjacentHTML("afterbegin", items);

  // insertAdjacentHTML 은 문자열 형태 script를 실행하지는 않음.
  // append, after 등의 함수로 script 객체 요소를 삽입해 주어야 실행함.
  container.after(logoutScript);
};

// function getCookie(name) {
//              const cookies = document.cookie.split(';');
//              for (const cookie of cookies) {
//                const [cookieName, cookieValue] = cookie.trim().split('=');
//                if (cookieName === name) {
//                  return cookieValue;
//                }
//              }
//              return null;
//            }