// $(document).ready(function () {
//     // 페이지 로드 시 로그인 상태 확인 후 UI 업데이트
//     $("#headers").load("/header.html", function (response, status, xhr) {
//         checkLoginStatus();
//     })
// });


function checkLoginStatus() {
    let token = Cookies.get('Authorization');
    if (token == null) {
        updateLoginStatus(null, false);
    } else {
        reissueToken((token) => {
            updateLoginStatus(token, true);
        });
    }
}

function updateLoginStatus(token, stat) {
    // 로그인 상태 확인 API 호출
    if (!stat) {
        $("#login, #signup").css("display", "block");
        $("#login-user-set").css("display", "none");
        return;
    }

    $.ajax({
        url: "/api/v1/auth/status",
        type: "GET",
        headers: {
            "Authorization": token
        },
        success: function (data) {
            if (data.isLoggedIn === true) {
                // 사용자가 로그인된 경우
                if (data.user.role === "USER") {
                    $("#login-user-set").css("display", "block");
                    $("#login-user-set #userDropdown, #login-user-set #user-drop-menus #nickname")
                        .text(data.user.nickname);
                    $("#login, #signup").css("display", "none");
                    $("#login-user-set #user-drop-menus #header-point")
                        .text(data.point.toLocaleString());
                    $("#admin-set").css("display", "none");
                } else {
                    // 사용자가 어드민인 경우
                    $("#admin-set").css("display", "block");
                    $("#login-user-set").css("display", "none");
                    $("#admin-set #admin-nickname, #admin-set #adminDropdown")
                        .text(data.user.nickname);
                    $("#login, #signup").css("display", "none");
                }
            } else {
                // 사용자가 로그아웃된 경우
                $("#login, #signup").css("display", "block");
                $("#login-user-set").css("display", "none");
            }
        },
        error: function (jqXHR, textStatus) {
            console.error("Error checking login status:", textStatus);
        }
    });
}

function setTokenInCookie(data) {
    Cookies.set('Authorization', data, {path: '/'})
}

function confirmFuncLogout() {
    let result = confirm("로그아웃 하시겠습니까?");
    if (result) {
        requestLogout();
    }
}

function requestLogout() {
    let token = Cookies.get('Authorization');
    $.ajax({
        url: "/api/v1/auth/logout",
        type: "POST",
        headers: {
            "Authorization": token
        },
        success: function (data) {
            // 로그아웃에 성공한 경우 처리
            console.log("Logout successful");
            // 여기에서 로그아웃 후의 추가 동작을 수행할 수 있습니다.
            Cookies.remove('Authorization', {path: '/'})

            window.location.href = `/index.html`
        },
        error: function (jqXHR, textStatus) {
            // 로그아웃에 실패한 경우 처리
            console.error("Error during logout:", textStatus);
        }
    });
}


function requestLogin() {
    let email = $('#email').val();
    let password = $('#password').val();

    $.ajax({
        type: "POST",
        url: `/api/v1/auth/login`,
        contentType: "application/json",
        data: JSON.stringify({email: email, password: password}),
    })
        .done(function (res, status, xhr) {
            const token = xhr.getResponseHeader('Authorization');

            Cookies.set('Authorization', token, {path: '/'})

            window.location.href = `/index.html`
        })
        .fail(function (jqXHR, textStatus) {
            alert("fail");
        });
}

/* 토큰이 필요한 페이지로 이동 시 함수*/
function movePageWithToken(pageUrl) {
    let token = Cookies.get('Authorization');
    if (token) {
        reissueToken((token) => {
            redirectToPageWithToken(pageUrl, token);
        })
    }
}

function redirectToPageWithToken(pageUrl, token) {
    fetch(pageUrl, {
        method: 'GET',
        headers: {
            'Authorization': `${token}`
        }
    })
        .then(response => {
            // 응답을 확인하고, 필요한 처리를 수행
            if (response.ok) {
                // 페이지 이동 또는 다른 동작 수행
                window.location.href = pageUrl;
            } else {
                console.error('페이지 이동 실패:', response.statusText);
            }
        })
        .catch(error => {
            console.error('페이지 이동 실패:', error);
        });
}

// 권한(토큰)이 필요 없는 페이지 이동 시 호출 함수
function redirectToPage(pageUrl) {
    fetch(pageUrl, {
        method: 'GET'
    })
        .then(response => {
            // 응답을 확인하고, 필요한 처리를 수행
            if (response.ok) {
                // 페이지 이동 또는 다른 동작 수행
                window.location.href = pageUrl;
            } else {
                console.error('페이지 이동 실패:', response.statusText);
            }
        })
        .catch(error => {
            console.error('페이지 이동 실패:', error);
        });
}
