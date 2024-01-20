let readyToSignup = false;

$(document).ready(function () {
    // select 변경 이벤트 감지
    $("#email-domain-select").change(function () {
        var selectedOption = $(this).val();
        var inputField = $("#email-domain-input");

        if (selectedOption === "직접 입력") {
            // 직접 입력을 선택한 경우, 입력란을 활성화
            inputField.prop("readonly", false);
            inputField.val("");
        } else {
            // 다른 옵션을 선택한 경우, 입력란을 읽기 전용으로 설정하고 값을 변경
            inputField.prop("readonly", true);
            inputField.val(selectedOption);
        }
    });

    // 입력란 값이 변경될 때마다 비밀번호 일치 여부 확인
    $("#password, #password-check").on("input", checkPasswordMatch);
});

function signup() {

    let email = $("#email-name-input").val() + "@" + $("#email-domain-input").val();
    let password = $("#signup-password").val();
    let name = $("#signup-name").val();
    let nickname = $("#signup-nickname").val();
    let birth = $("#signup-birth").val();
    let phoneNumber = $("#signup-phone").val();
    let verificationNumber = $("#verificationCode").val();

    if (!email | !password | !name | !nickname | !phoneNumber | !verificationNumber | !birth) {
        alert('입력란을 모두 입력해주세요.');
        return;
    } else if (password && !readyToSignup) {
        alert('비밀번호가 일치하지 않습니다.');
        return;
    }
    $.ajax({
        type: "POST",
        url: getUrl() + `/api/v1/users/signup`,
        contentType: "application/json",
        data: JSON.stringify({
            email: email,
            password: password,
            name: name,
            nickname: nickname,
            birth: birth,
            phoneNumber: phoneNumber,
            verificationNumber: verificationNumber
        }),
        success: function (data) {
            alert("회원 가입에 성공했습니다.");
            window.location.href = `/login.html`;
        },
        error: function (jqXHR, textStatus) {
            resetValidationMessages();
            let response = jqXHR.responseJSON;
            if (response.data) {
                let data = response.data;
                if (data.email) {
                    $("#email-span").text(data.email);
                }
                if (data.password) {
                    $("#password-span").text(data.password);
                }
                if (data.nickname) {
                    $("#nickname-span").text(data.nickname);
                }
                if (data.name) {
                    $("#name-span").text(data.name);
                }
                if (data.birth) {
                    $("#birth-span").text(data.birth);
                }
                if (data.phoneNumber) {
                    $("#phoneNumber-span").text(data.phoneNumber);
                }
                if (data.verificationNumber) {
                    $("#verificationNumber-span").text(data.verificationNumber);
                }
            }
            if (response.message) {
                if (response.message === '사용 중인 이메일 입니다.') {
                    $("#email-span").text(response.message);
                } else if (response.message === '사용 중인 닉네임 입니다.') {
                    $("#nickname-span").text(response.message);
                }
            } else if (response.message === '잘못된 인증 번호 입니다.') {
                $("#verificationNumber-span").text(response.message);
            } else if (response.message === '인증 번호 입력 시간 초과 입니다..') {
                $("#verificationNumber-span").text(response.message);
            }
        }
    });
}

function checkPasswordMatch() {
    var password = $("#signup-password").val();
    var confirmPassword = $("#password-check").val();
    var messageElement = $("#passwordMatchMessage");

    // 비밀번호와 비밀번호 확인 값이 동일한지 확인
    if (password === confirmPassword) {
        messageElement.text("비밀번호가 일치합니다.").css("color", "green")
            .removeClass("password-mismatch").addClass("password-match");
        readyToSignup = true;
    } else {
        messageElement.text("비밀번호가 일치하지 않습니다.").css("color", "red")
            .removeClass("password-match").addClass("password-mismatch");
        readyToSignup = false;
    }
}


function verificationPhone() {
    resetValidationMessages();
    let phoneNumber = $("#signup-phone").val();
    $.ajax({
        type: "POST",
        url: getUrl() + `/api/v1/auth/sms`,
        contentType: "application/json",
        data: JSON.stringify({
            to: phoneNumber
        }),
        success: function (response) {
            let endTime = new Date(response.data);
            displayRemainingTime(endTime, "signup-remaining-time", "verification-btn");
            $("#verification-btn").addClass("disabled");

            alert("인증 번호를 발송했습니다.");
        },
        error: function (jqXHR, textStatus) {

            let response = jqXHR.responseJSON;
            if (response.data) {
                let data = response.data;
                if (data.to) {
                    $("#phoneNumber-span").text(data.to);
                }
            }
            if (response.message) {
                if (response.message === '사용 중인 전화 번호 입니다.') {
                    $("#phoneNumber-span").text(response.message);
                } else if (response.message === '잘못된 인증 번호 입니다.') {
                    $("#verificationNumber-span").text(response.message);
                } else if (response.message === '인증 번호 입력 시간 초과 입니다..') {
                    $("#verificationNumber-span").text(response.message);
                }
            }
        },
    });
}


function resetValidationMessages() {
    $("#email-span, #password-span, #name-span, #nickname-span, #birth-span, #phoneNumber-span, #verificationNumber-span")
        .text("");
}