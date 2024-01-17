$(document).ready(function () {
    $("#settings-menu a").click(function () {
        // 모든 링크에서 'active' 클래스 제거
        $("#settings-menu a").removeClass("active");

        // 클릭된 링크에 'active' 클래스 추가
        $(this).addClass("active");
    });
});

function getMyInfo(token, id) {
    $.ajax({
        type: "GET",
        url: `/api/v1/users/${id}`,
        headers: {
            "Authorization": token
        },
        success: function (data) {
            let info = data.data;
            console.log(info.email)
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