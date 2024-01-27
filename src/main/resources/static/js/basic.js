var white_list = {
    "/": true,
    "/#": true,
    "/index.html": true,
    "/index.html#": true,
    "/login.html": true,
    "/user/signup.html": true,
    "/goods/goods-details.html": true,
};

function getUrl() {
    const hostname = window.location.hostname;
    const url = hostname === 'localhost' ? 'http://localhost:8080' : 'https://api.ticket-auction.shop';

    return url;
}


function checkLoginStatus() {
    var curHtml = window.location.pathname;
    let token = Cookies.get('Authorization');

    if (token == null && !white_list[curHtml]) {
        // 토큰도 없고, 접근 권한 없는 페이지에 접근 시
        redirectToPage('/login.html');
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
        url: `${getUrl()}/api/v1/auth/status`,
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
    confirmAlert("로그아웃 하시겠습니까?")
        .then((result) => {
            if (result) {
                requestLogout();
            }
        });
}

function requestLogout() {
    let token = Cookies.get('Authorization');
    $.ajax({
        url: `${getUrl()}/api/v1/auth/logout`,
        type: "POST",
        headers: {
            "Authorization": token
        },
        success: function (data) {
            // 로그아웃에 성공한 경우 처리
            console.log("Logout successful");
            // 여기에서 로그아웃 후의 추가 동작을 수행할 수 있습니다.
            Cookies.remove('Authorization', {path: '/'})

            redirectToPage("/index.html");
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
        url: `${getUrl()}/api/v1/auth/login`,
        contentType: "application/json",
        data: JSON.stringify({email: email, password: password}),
    })
        .done(function (res, status, xhr) {
            const token = xhr.getResponseHeader('Authorization');

            Cookies.set('Authorization', token, {path: '/'})

            // backPageWithToken();
            redirectToPageWithToken("/index.html", token);
        })
        .fail(function (jqXHR, textStatus) {
            errorAlert("로그인에 실패했습니다. 다시 시도해주세요.");
        });
}

function backPageWithToken() {
    window.history.back();
    checkLoginStatus();
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
    // fetch(pageUrl, {
    //     method: 'GET'
    // })
    //     .then(response => {
    //         // 응답을 확인하고, 필요한 처리를 수행
    //         if (response.ok) {
    //             // 페이지 이동 또는 다른 동작 수행
    window.location.href = pageUrl;
//             } else {
//                 console.error('페이지 이동 실패:', response.statusText);
//             }
//         })
//         .catch(error => {
//             console.error('페이지 이동 실패:', error);
//         });
}

// 쿼리 파라미터와 함께 보내기
function redirectToPageWithParameter(pageUrl, parameter, value) {
    fetch(pageUrl, {
        method: 'GET'
    })
        .then(response => {
            // 응답을 확인하고, 필요한 처리를 수행
            if (response.ok) {
                // 페이지 이동 또는 다른 동작 수행
                window.location.href = `${pageUrl}?${parameter}=${value}`;
            } else {
                console.error('페이지 이동 실패:', response.statusText);
            }
        })
        .catch(error => {
            console.error('페이지 이동 실패:', error);
        });
}

function redirectToPageWithParameters(pageUrl, paramValueMap) {
    fetch(pageUrl, {
        method: 'GET'
    })
        .then(response => {
            // 응답을 확인하고, 필요한 처리를 수행
            if (response.ok) {
                // 페이지 이동 또는 다른 동작 수행
                let queryString = '';
                for (const key of Object.keys(paramValueMap)) {
                    queryString += `${key}=${paramValueMap[key]}&`
                }
                window.location.href = `${pageUrl}?${queryString}`;
            } else {
                console.error('페이지 이동 실패:', response.statusText);
            }
        })
        .catch(error => {
            console.error('페이지 이동 실패:', error);
        });
}

function getQueryParams() {
    var queryParams = {};
    var queryString = window.location.search.substring(1); // ? 제외한 쿼리 문자열

    // 쿼리 문자열을 &로 분리하여 배열로 만듦
    var queryParamsArray = queryString.split("&");

    // 각 쿼리 파라미터를 처리
    queryParamsArray.forEach(function (param) {
        var pair = param.split("=");
        var key = decodeURIComponent(pair[0]); // 특수 문자를 디코딩
        var value = decodeURIComponent(pair[1]); // 특수 문자를 디코딩
        queryParams[key] = value;
    });

    return queryParams;
}

function movePage() {
    let token = Cookies.get('Authorization');
    if (token) {
        reissueToken((token) => {
            redirectToPageWithToken('/index.html', token);
        });
    } else {
        redirectToPage(`/index.html`);
    }
}

function displayRemainingTime(endTime, tag, btn) {
    let now = new Date();
    let timeDiff = endTime - now;

    if (timeDiff <= 0) {
        $(`#${tag}`).empty();
        $(`#${btn}`).removeClass("disabled");
    } else {
        // 남은 시간을 초로 변환
        let secondsRemaining = Math.floor(timeDiff / 1000);

        // 분과 초 계산
        let minutes = Math.floor(secondsRemaining / 60);
        let seconds = secondsRemaining % 60;

        // 시간 표시를 위해 2자리로 포맷팅
        let formattedTime = `${padZero(minutes)}:${padZero(seconds)}`;

        // 화면에 남은 시간 표시
        $(`#${tag}`).text(`  ${formattedTime}`);

        setTimeout(function () {
            displayRemainingTime(endTime);
        }, 1000);
    }
}

// 10 미만의 숫자에 0을 붙이는 함수
function padZero(number) {
    return number < 10 ? `0${number}` : number;
}

// 날짜와 시간을 원하는 형식으로 변환하는 함수
function formatDateTime(date) {
    var year = date.getFullYear();
    var month = String(date.getMonth() + 1).padStart(2, '0');
    var day = String(date.getDate()).padStart(2, '0');
    var hours = String(date.getHours()).padStart(2, '0');
    var minutes = String(date.getMinutes()).padStart(2, '0');

    return `${year}.${month}.${day} ${hours}:${minutes}`;
}

function encode(input) {
    return btoa(input + "rOnIOuBneuCnOuLpC4uLi4u");
}

function decode(input) {
    if (isNumeric(input)) {
        return input;
    }
    let decodedString = atob(input);
    if (!decodedString.includes("rOnIOuBneuCnOuLpC4uLi4u")) {
        return input;
    }

    return decodedString.replace("rOnIOuBneuCnOuLpC4uLi4u", "");
}

function isNumeric(input) {
    return /^[0-9]+$/.test(input);
}

/**
 * 예쁜 비동기 alert
 * @param title 제목
 * @param text 메세지
 * @param type 아이콘 타입
 * @param confirmButtonText 확인 버튼 텍스트
 */
function asyncAlert(title, text, type, confirmButtonText) {
    Swal.fire({
        title: title,
        text: text,
        icon: type,
        confirmButtonText: confirmButtonText
    });
}

function okAlert(text) {
    Swal.fire({
        title: '알림!',
        text: text,
        icon: 'info',
        confirmButtonText: '확인'
    })
}

function errorAlert(text) {
    Swal.fire({
        title: '알림!',
        text: text,
        icon: 'error',
        confirmButtonText: '확인'
    })
}

async function confirmAlert(text) {
    const result = await Swal.fire({
        title: text,
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "예",
        cancelButtonText: "아니요",
        closeOnConfirm: false,
        closeOnCancel: true
    });

    // result.value 가 true일 때는 "예" 버튼이 클릭된 경우
    // result.dismiss 가 "cancel"일 때는 "아니요" 버튼이 클릭된 경우
    return result.isConfirmed ? true : false;
}