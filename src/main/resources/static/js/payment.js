$(document).ready(function () {
    $('input[name="chargeAmount"]').change(function () {
        if ($(this).attr('id') === 'customAmountRadio') {
            $('#customAmount').prop('disabled', false);
        } else {
            $('#customAmount').prop('disabled', true);
        }
    });
});

var token = Cookies.get('Authorization');
let key;
let tossPayments;
// AJAX 요청이 완료된 후에 TossPayments 초기화
$.ajax({
    url: `${getUrl()}/api/v1/payments/getKey`,
    type: "GET",
    headers: {
        "Authorization": token,
        "Content-Type": "application/json"
    },
    success: function (data) {
        key = data;

        // TossPayments 초기화
        tossPayments = TossPayments(key);

        // getRequest 함수 등을 호출할 수 있음
    },
    error: function (jqXHR, textStatus) {
        console.error("Error checking login status:", textStatus);
    }
});

function getRequest(callback) {
    let amount;
    if ($('#customAmountRadio').is(':checked')) {
        amount = $('#customAmount').val();
    } else {
        amount = $('input[name="chargeAmount"]:checked').val();
    }

    let orderName = "포인트 충전";

    $.ajax({
        url: `${getUrl()}/api/v1/payments`,
        type: "POST",
        headers: {
            "Authorization": token,
            "Content-Type": "application/json"
        },
        data: JSON.stringify({
            amount: amount,
            orderName: orderName
        }),
        success: function (data) {
            let json = {
                "amount": data.request.amount,
                "orderId": data.request.orderId,
                "orderName": data.request.orderName,
                "successUrl": data.request.successUrl,
                "failUrl": data.request.failUrl,
                "cardCompany": null,
                "cardInstallmentPlan": null,
                "maxCardInstallmentPlan": null,
                "useCardPoint": false,
                "customerName": data.request.userName,
                "customerEmail": data.request.userEmail,
                "customerMobilePhone": null,
                "taxFreeAmount": null,
                "useInternationalCardOnly": false,
                "flowMode": "DEFAULT",
                "discountCode": null,
                "appScheme": null
            };
            callback(json);
        },
        error: function (jqXHR, textStatus) {
            console.error("Error checking login status:", textStatus);
        }
    });
}

// pay 함수 수정
function pay(method) {
    getRequest(function (json) {
        tossPayments.requestPayment(method, json)
            .catch(function (error) {
                if (error.code === "USER_CANCEL") {
                    errorAlert('결제를 취소했습니다.');
                } else {
                    errorAlert(error.message);
                }
            });
    });
}