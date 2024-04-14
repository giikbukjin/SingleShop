
async function get(endpoint, params = "") {
  const apiUrl = params ? `${endpoint}/${params}` : endpoint;
  console.log(`%cGET 요청: ${apiUrl} `, "color: #a25cd1;");

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

  // 토큰이 있으면 Authorization 헤더를 포함, 없으면 포함하지 않음
  const token = getCookie("Authorization");
  const headers = token ? { Authorization: `Bearer ${token}` } : {};

  const res = await fetch(apiUrl, { headers });

  // 응답 코드가 4XX 계열일 때 (400, 403 등)
  if (!res.ok) {
    const errorContent = await res.json();
    const { reason } = errorContent;

    throw new Error(reason);
  }

  const result = await res.json();
  return result;
}

async function post(endpoint, data) {
  const apiUrl = endpoint;
  const bodyData = JSON.stringify(data);
  console.log(`%cPOST 요청: ${apiUrl}`, "color: #296aba;");
  console.log(`%cPOST 요청 데이터: ${bodyData}`, "color: #296aba;");

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

  // 토큰이 있으면 Authorization 헤더를 포함, 없으면 포함하지 않음
  const token = getCookie("Authorization");
  const headers = {
    "Content-Type": "application/json"
  };

  const res = await fetch (apiUrl, {
    method: "POST",
    headers,
    body: bodyData,
  });

  // 응답 코드가 4XX 계열일 때 (400, 403 등)
  if (!res.ok) {
    const errorContent = await res.json();
    const { reason } = errorContent;

    throw new Error(reason);
  }

  const result = await res.json();

  return result;
}


// api 로 PATCH 요청 (/endpoint/params 로, JSON 데이터 형태로 요청함)
async function patch(endpoint, params = "", data) {
  const apiUrl = params ? `${endpoint}/${params}` : endpoint;

  const bodyData = JSON.stringify(data);
  console.log(`%cPATCH 요청: ${apiUrl}`, "color: #059c4b;");
  console.log(`%cPATCH 요청 데이터: ${bodyData}`, "color: #059c4b;");

  const queryParams = new URLSearchParams();

  for (const key in data) {
      queryParams.append(key, data[key]);
  }

  const queryString = queryParams.toString();
//  console.log(${queryString});

  const res = await fetch(apiUrl + '?' + queryString, {
    method: "PATCH",
    headers: {
      "Content-Type": "application/json",
//      Authorization: `Bearer ${sessionStorage.getItem("token")}`,
    },
    body: bodyData,
  });

  // 응답 코드가 4XX 계열일 때 (400, 403 등)
  if (!res.ok) {
    const errorContent = await res.json();
    const { reason } = errorContent;

    throw new Error(reason);
  }
  window.location.reload();
  const result = await res.json();

  return result;
}

// 아래 함수명에 관해, delete 단어는 자바스크립트의 reserved 단어이기에,
// 여기서는 우선 delete 대신 del로 쓰고 아래 export 시에 delete로 alias 함.
async function del(endpoint, params = "") {
  const apiUrl = `${endpoint}/${params}`;

  console.log(`DELETE 요청 ${apiUrl}`, "color: #059c4b;");

  const res = await fetch(apiUrl, {
    method: "DELETE",
    headers: {
      "Content-Type": "application/json",
//      Authorization: `Bearer ${sessionStorage.getItem("token")}`,
    }
  });

  // 응답 코드가 4XX 계열일 때 (400, 403 등)
  if (!res.ok) {
    const errorContent = await res.json();
    const { reason } = errorContent;

    throw new Error(reason);
  }

  return ;
}

// 아래처럼 export하면, import * as Api 로 할 시 Api.get, Api.post 등으로 쓸 수 있음.
export { get, post, patch, del as delete };
