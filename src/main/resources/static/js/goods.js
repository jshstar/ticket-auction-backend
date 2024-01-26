function getGoodsCategories() {
    $.ajax({
        type: "GET",
        url: `${getUrl()}/api/v1/goods-categorys`,
        success: function (response) {
            for (let i = 0; i < response.data.length; i++) {
                let name = response.data[i].categoryName;
                if (name === "서커스마술") name = "서커스/마술";

                let div = $('<div>').addClass("btn-div")
                let btn = $('<button>').text(name)
                    .addClass("btn round-btn")
                    .addClass(response.data[i].categoryName);

                if (name === '연극') {
                    btn = $('<button>').text(response.data[i].categoryName)
                        .addClass("btn round-btn active")
                        .addClass(response.data[i].categoryName);
                }

                btn.on("click", function () {
                    clickOnCategoryBtn(response.data[i].categoryName);
                });

                let db = div.append(btn);
                $(".btn-by-category").append(db);
            }
        },
        error: function (qXHR, textStatus) {
            console.log(qXHR);
        }
    });
}

function clickOnCategoryBtn(name) {
    $.ajax({
        type: "GET",
        url: `${getUrl()}/api/v1/goods`,
        data: {
            page: 0,
            size: 5,
            sort: "endDate",
            categoryName: name
        },
        success: function (response) {
            console.log(response);
            $(".btn-div button").removeClass("active");
            $(`.${name}`).addClass("active");
            $("#goods-posters").empty();

            for (let i = 0; i < response.data.goodsResponses.length; i++) {
                let d = response.data.goodsResponses[i];

                let eid = encode(d.goodsId);
                let pd = $('<div>').addClass("col")
                    .append(
                        $('<img>').attr("src", `${d.s3Url}`).addClass("goods-poster-img")
                            .on("click", function () {
                                redirectToPageWithParameter(
                                    "/goods/goods-details.html",
                                    "goodsId",
                                    eid
                                );
                            })
                    )
                    .append($('<p>').text(d.title.split(" - ")[0]).addClass("goods-title"))
                    .addClass("pd");

                $("#goods-posters").append(pd);
            }
        },
        error: function (qXHR, textStatus) {
            console.log(qXHR);
        }
    });
}

