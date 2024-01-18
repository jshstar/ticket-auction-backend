function getMyInfo(token, id) {
    $.ajax({
        type: "GET",
        url: `/api/v1/users/${id}`,
        headers: {
            "Authorization": token
        },
        success: function (data) {
            let info = data.data;
            $("#info-email").text(info.email);
            $("#info-name").text(info.name);
            $("#info-nickname").text(info.nickname);
            $("#info-birth").text(info.birth);
            $("#info-phone").text(info.phoneNumber);
        },
        error: function (jqXHR, textStatus) {
            console.log(jqXHR);
            console.error(textStatus);
        },
    })
}

function verificationPhone() {
    let phoneNumber = $("#update-phone").val();
    $.ajax({
        type: "POST",
        url: `/api/v1/auth/sms`,
        contentType: "application/json",
        data: JSON.stringify({
            to: phoneNumber
        }),
        success: function (data) {
            alert("인증 번호를 발송했습니다.");
        },
        error: function (jqXHR, textStatus) {

            let response = jqXHR.responseJSON;
            if (response.data) {
                let data = response.data;
                if (data.to) {
                    $("#update-phoneNumber-span").text(data.to);
                }
            }
            if (response.message) {
                if (response.code.substring(2, 4) === "07") {
                    $("#update-verificationNumber-span").text(response.message);
                }
            }
        },
    });
}

function updateUserInfo(token, id) {
    let nickname = $("#update-nickname").val();
    let phoneNumber = $("#update-phone").val();
    let verificationNumber = $("#update-verificationCode").val();

    $.ajax({
        type: "PUT",
        url: `/api/v1/users/${id}`,
        contentType: "application/json",
        headers: {
            "Authorization": token
        },
        data: JSON.stringify({
            nickname: nickname,
            phoneNumber: phoneNumber,
            verificationNumber: verificationNumber
        }),
        success: function (data) {
            alert("회원 정보를 수정했습니다.")
            movePageWithToken(`/user-info.html`);
        },
        error: function (jqXHR, textStatus) {
            resetValidationMessages();
            let response = jqXHR.responseJSON;
            if (response) {
                if (response.code.substring(2, 4) === "04") {
                    $("#update-nickname-span").text(response.message);
                }
                if (response.code.substring(2, 4) === "06") {
                    $("#update-phoneNumber-span").text(response.message);
                }
                if (response.code.substring(2, 4) === "07") {
                    $("#update-verificationNumber-span").text(response.message);
                }
            }
        }
    });
}

function resetValidationMessages() {
    $("#update-nickname-span, #update-phoneNumber-span, #update-verificationNumber-span")
        .text("");
}

let readyToUpdate = false;

function checkPasswordMatch() {
    let password = $("#update-password").val();
    let confirmPassword = $("#update-password-check").val();
    let messageElement = $("#update-passwordMatchMessage");

    // 비밀번호와 비밀번호 확인 값이 동일한지 확인
    if (password === confirmPassword) {
        messageElement.text("비밀번호가 일치합니다.").css("color", "green")
            .removeClass("password-mismatch").addClass("password-match");
        readyToUpdate = true;
    } else {
        messageElement.text("비밀번호가 일치하지 않습니다.").css("color", "red")
            .removeClass("password-match").addClass("password-mismatch");
        readyToUpdate = false;
    }
}

function updatePassword(token, id) {
    let password = $("#update-password").val();

    $.ajax({
        type: "PATCH",
        url: `/api/v1/users/${id}`,
        contentType: 'application/json',
        headers: {
            "Authorization": token
        },
        data: JSON.stringify({
            password: password
        }),
        success: function (data) {
            alert("비밀 번호 변경이 완료되었습니다.");
            movePageWithToken(`/user-info.html`);
        },
        error: function (jqXHR, textStatus) {
            resetValidationMessages();
            let response = jqXHR.responseJSON;
            if (response.data) {
                $("#update-password-span").text(response.data.password);
            }
            if (response) {
                if (response.code.substring(2, 4) === "02") {
                    $("#update-password-span").text(response.message);
                }

            }
        }
    });
}