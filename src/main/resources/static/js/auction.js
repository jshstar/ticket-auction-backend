function getJoinedAuctionList(token, page) {
    $.ajax({
        type: "GET",
        url: `${getUrl()}/api/v1/auctions?page=${page}`,
        headers: {
            "Authorization": token
        },
        success: function (response) {
            let data = response.data;

            $(".list-tb-body").empty();
            $(".pagination").empty();


            if (response.code === "A00600" && data.content) {
                displayAuction(data);
            }
        },
        error: function (jqXHR, textStatus) {
            errorAlert("참여 경매 목록 조회에 에러가 발생했습니다.");
        },
    })
}

function displayAuction(data) {
    let size = data.pageable.pageSize;
    let curIndex = data.number;

    for (let i = 0; i < data.content.length; i++) {
        let index = $('<td>').text(i + 1);

        let date = $('<td>').text(data.content[i].startDateTime);

        let ed = encode(data.content[i].id);
        let id = $('<td>').text(ed);

        let status = $('<td>');
        if (!data.content[i].isEnded) {
            status.text("진행 중");
        } else {
            status.text("종료");
        }

        let btn = $('<button>').addClass("detail-btn btn").text("입찰 내역")
            .on("click", function () {
                redirectToPageWithParameter(
                    "/auction/bid-list.html",
                    "auctionId",
                    ed
                );
            });

        let tr = $('<tr>').append(index)
            .append(date)
            .append(status)
            .append(id)
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
                    getJoinedAuctionList(token, i);
                }
            ));
        });

        $(".pagination").append(link);
    }
}

function getBidList(token, page) {
    let queryParams = getQueryParams();

    if (queryParams["auctionId"]) {
        let id = decode(queryParams["auctionId"]);
        $.ajax({
            type: "GET",
            url: `${getUrl()}/api/v1/auctions/${id}/bids?page=${page}`,
            headers: {
                "Authorization": token
            },
            success: function (response) {
                let data = response.data;

                $(".list-tb-body").empty();
                $(".pagination").empty();

                if (response.code === "B00000" && data.content) {
                    displayBid(data);
                }
            },
            error: function (jqXHR, textStatus) {
                errorAlert("입찰 내역 조회에 에러가 발생했습니다.");
            }
        })

    }
}

function displayBid(data) {
    let size = data.pageable.pageSize;
    let curIndex = data.number;

    for (let i = 0; i < data.content.length; i++) {
        let index = $('<td>').text(i + 1);
        let date = $('<td>').text(data.content[i].createdAt);
        let status = $('<td>').text(data.content[i].status);
        let price = $('<td>').text(`${data.content[i].price.toLocaleString()}원`);


        let tr = $('<tr>').append(index)
            .append(date)
            .append(status)
            .append(price);

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
                    getJoinedAuctionList(token, i);
                }
            ));
        });

        $(".pagination").append(link);
    }
}