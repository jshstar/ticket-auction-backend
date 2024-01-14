$(document).ready(function () {
    // 페이지 로드 시 로그인 상태 확인
    checkLoginStatus();
});

function checkLoginStatus() {
    var token = Cookies.get('Authorization');

    // 로그인 상태 확인 API 호출
    $.ajax({
        url: "/api/v1/auth/status",
        type: "GET",
        headers: {
            "Authorization": token
        },
        success: function (data) {
            if (data.isLoggedIn) {
                // 로그인 상태일 경우 처리
                $("#logout").show();
                $("#mypage").show();
                $("#name").show();
                $("#name_a").text(data.user.nickname);
            } else {
                // 비로그인 상태일 경우 처리
                $("#login").show();
                $("#signup").show();
            }
        },
        error: function (jqXHR, textStatus) {
            console.error("Error checking login status:", textStatus);
        }
    });

}

function requestLogout() {
    var token = Cookies.get('Authorization');
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
            window.location.href = `/api/v1/`
        },
        error: function (jqXHR, textStatus) {
            // 로그아웃에 실패한 경우 처리
            console.error("Error during logout:", textStatus);
        }
    });
}

$(document).ready(function () {
    Cookies.remove('Authorization', {path: '/'})
});

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

            window.location.href = `/api/v1/`

        })
        .fail(function (jqXHR, textStatus) {
            alert("fail");
        });
}