function getReservationList(token, page) {
    $.ajax({
        type: "GET",
        url: `/api/v1/reservations`,
        data: {
            page: page,
            size: 15
        },
        headers: {
            "Authorization": token
        },
        success: function (response) {
            console.log(response);
            let data = response.data;

            $(".list-tb-body").empty();
            $(".pagination").empty();


            if (response.code === "R00002" && data.content) {
                displayReservation(data);
            }
        },
        fail: function (jqXHR, textStatus) {
            console.log(jqXHR);
            console.log(textStatus);
        },
    });
}

function displayReservation(data) {
    let size = data.pageable.pageSize;
    let curIndex = data.number;

    for (let i = 0; i < data.content.length; i++) {
        let id = $('<td>').text(data.content[i].reservationId);
        var dateObject = formatDateTime(new Date(data.content[i].reservationDate));
        let date = $('<td>').text(dateObject);
        let title = $('<td>').text(data.content[i].title);
        dateObject = formatDateTime(new Date(data.content[i].useDate));
        let useDate = $('<td>').text(`${dateObject} / ${data.content[i].numberOfTicket}매`);
        let cancelDate = $('<td>').text(formatDateTime(new Date(data.content[i].cancelDeadline)));
        let status = $('<td>');
        if (data.content[i].status === "OK") {
            status.text("예매");
        } else if (date.content[i].status === "CANCEL") {
            status.text("취소");
        } else {
            status.text("사용 완료");
        }

        let btn = $('<button>').addClass("detail-btn btn").text("상세");
        let tr = $('<tr>').append(id)
            .append(date)
            .append(title)
            .append(useDate)
            .append(cancelDate)
            .append(status)
            .append($('<td>').append(btn));
        $(".list-tb-body").append(tr);
    }

    let totalPage = data.totalPages;
    for (let i = 0; i < totalPage; i++) {
        const pageNumber = i + 1;
        let link = $('<a>');
        link.addClass('list-a');
        link.href = '#';
        link.text(pageNumber);

        if (i === data.number) {
            link.addClass('now');
        }

        link.on("click", function () {
            reissueToken((token => {
                    getReservationList(token, i);
                }
            ));
        });


        $(".pagination").append(link);

    }
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


function getReservationDetail() {
    
}