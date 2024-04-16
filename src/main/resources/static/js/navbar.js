export const createNavbar = () => {
  const pathname = window.location.pathname;

  switch (pathname) {
    case "/home":
      addNavElements("admin register login account logout");
      break;
    case "/account/orders":
      addNavElements("admin account logout");
      break;
    case "/account/security/${id}":
      addNavElements("admin account logout");
      break;
    case "/account/signout":
      addNavElements("admin account logout");
      break;
    case "/account":
      addNavElements("admin logout");
      break;
    case "/admin/orders":
      addNavElements("admin account logout");
      break;
    case "/admin/users":
      addNavElements("admin account logout");
      break;
    case "/admin":
      addNavElements("account logout");
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

  const container = document.querySelector("#navbar");
  const isLogin = getCookie("Authorization") ? true : false;
  var isAdmin = false;
  (async () => {
      try {
          const res = await fetch("api/users/admin-check");
          const result = await res.json();
          isAdmin = result.status === "success"; // result.status가 "success"이면 isAdmin을 true로 설정
          console.log(isAdmin);
          // 여기서 isAdmin이 true로 설정되었으므로, 이후에 처리해야 할 모든 코드를 여기에 넣어주세요.
          console.log(isAdmin); // 여기서 isAdmin은 true입니다.
          // isAdmin이 변경된 이후에 실행되어야 하는 모든 코드는 여기에 위치해야 합니다.
      } catch (error) {
          isAdmin = false;
          console.error("Admin check failed:", error);
          // 에러 처리 코드를 추가할 수 있습니다.
      }
      console.log(isAdmin); // 여기서도 isAdmin은 true 또는 false일 수 있습니다.
  })();

  // 로그인 안 된 상태에서만 보이게 될 navbar 요소들
  const itemsBeforeLogin = {
    register: '<li><a href="/auth/signup">회원가입</a></li>',
    login: '<li><a href="/auth/login">로그인</a></li>',
  };

  // 로그인 완료된 상태에서만 보이게 될 navbar 요소들
  const itemsAfterLogin = {
//      function getCookie(name) {
//              const cookies = document.cookie.split(';');
//              for (const cookie of cookies) {
//                const [cookieName, cookieValue] = cookie.trim().split('=');
//                if (cookieName === name) {
//                  return cookieValue;
//                }
//              }
//              return null;
//            }
    account: '<li><a href="/account">계정관리</a></li>',
//    if (getCookie('kakao')) {
//    logout: '<li><a href="" id="logout">로그아웃</a></li>',
//    }
    logout: '<li><a href="/auth/logout" id="logout">로그아웃</a></li>',
    productAdd: '<li><a href="/product/add">제품 추가</a></li>',
    categoryAdd: '<li><a href="/category/add">카테고리 추가</a></li>',
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
//           토큰 삭제
//              function deleteCookie(name) {
//              	document.cookie = name + '=; expires=Thu, 01 Jan 1970 00:00:01 GMT;';
//              }
//              deleteCookie("Refresh");
//              deleteCookie("Authorization")
//                deleteCoo
          window.location.href = '/auth/logout';
        });
      }
  `;

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
