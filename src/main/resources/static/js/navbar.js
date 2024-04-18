export const createNavbar = () => {
function aaaaaa(str) {
    const pattern = /^\/account\/security\//;
    return pattern.test(str);
}

const pathname = aaaaaa(window.location.pathname) ? "/account/security" : window.location.pathname;

  switch (pathname) {
    case "/home":
      addNavElements("cart admin register login account logout");
      break;
    case "/orders":
      addNavElements("cart admin account logout");
      break;
    case "/account/security":
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
      addNavElements("cart admin account logout");
      break;
    case "/product/add":
      addNavElements("admin account logout");
      break;
    case "/product/detail":
      addNavElements("cart admin register login account logout");
      break;
    case "/product/list":
      addNavElements("cart admin register login account logout");
      break;
    case "/auth/signup":
      addNavElements("login");
      break;

    default:
  }
};

// navbar ul 태그에, li 태그들을 삽입함)
const addNavElements = async (keyString) => {
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

// 카카오 로그인 시, 쿠키 검색 후, KAKAO 쿠키가 있을 시, KAKAO 로그아웃으로 링크 전환
  let logoutPath;

      function cookieCheck(name) {
            const cookies = document.cookie.split(';');
                for (const cookie of cookies) {
                    const [cookieName, cookieValue] = cookie.trim().split('=');
                        if (cookieName === name) {
                            return true;
                        }
                }
            return null;
          }

          function getCookieName(name) {
            let matches = document.cookie.match(new RegExp(
              "(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
            ));
            return matches ? decodeURIComponent(matches[1]) : undefined;
          }

    // 아래 path 를 '<li><a href="https://kauth.kakao.com/oauth/logout?client_id=5ac62ccca7ee659af683fca629357a50&logout_redirect_uri=http://localhost:8080/auth/logout" id="logout">로그아웃</a></li>' 로 바꿔주시면 됩니다.
  if (cookieCheck("kakao")) { logoutPath = '<li><a href="https://kauth.kakao.com/oauth/logout?client_id=1fa06f949c45fbf2a3e893c494263777&logout_redirect_uri=http://localhost:8080/auth/logout" id="logout">로그아웃</a></li>' }
      else { logoutPath = '<li><a href="/auth/logout" id="logout">로그아웃</a></li>' };

  // 로그인 완료된 상태에서만 보이게 될 navbar 요소들
  const itemsAfterLogin = {
    account: '<li><a href="/account">계정관리</a></li>',
//    logout: '<li><a href="/auth/logout" id="logout">로그아웃</a></li>',
    logout: logoutPath,
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
        })
      }
  `;

let isAdmin = await (async () => {
      try {
          const res = await fetch("api/users/admin-check");
          const result = await res.json();

           if (result.status === "success") {
            return true;
          };

          return false;

      } catch (error) {
          console.error("Admin check failed:", error);
          // 에러 처리 코드를 추가할 수 있습니다.
      }

  })();

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