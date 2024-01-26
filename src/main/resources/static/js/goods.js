var curCategoryName = "연극";
var cursorId = null;
var index = 0;
var loading = false;

function getGoodsCategories() {
    $.ajax({
        type: "GET",
        url: `${getUrl()}/api/v1/goods-categorys`,
        success: function (response) {
            for (let i = 0; i < response.data.length; i++) {
                let name = response.data[i].categoryName;

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
                    $(".btn-div button").removeClass("active");
                    $(`.${name}`).addClass("active");

                    curCategoryName = response.data[i].categoryName;
                    cursorId = null;
                    $(".goods-posters-row").empty();

                    loadGoods();
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


function loadGoods() {
    if (cursorId === -1 || loading) {
        return;
    }

    loading = true;
    var apiUrl = cursorId === null
        ? `${getUrl()}/api/v1/goods?categoryName=${curCategoryName}`
        : `${getUrl()}/api/v1/goods?cursorId=${cursorId}&categoryName=${curCategoryName}`;

    $.ajax({
        type: "GET",
        url: apiUrl,
        success: function (response) {
            displayPosters(response.data);
        },
        complete: function () {
            loading = false;  // 호출이 완료되면 loading을 false로 설정
        }
    });
}

function displayPosters(data) {
    console.log(data)
    var div = $(".goods-posters-row")
    var row = $('<div>').addClass("d-flex justify-content-start posters-div row goods-posters");

    data.goodsResponses.forEach(function (goods) {
        var row_div = $('<div>')
            .addClass("col-md-2 poster")
            .append($('<img>').attr("src", `${goods.s3Url}`).addClass("goods-poster-img"))
            .append($('<p>').text(goods.title.split(" - ")[0]).addClass("goods-title")
            );

        row_div.on("click", function () {
            redirectToPageWithParameter(
                "/goods/goods-details.html",
                "goodsId",
                encode(goods.goodsId)
            );
        });

        row.append(row_div);
        index += 1;
    });
    div.append(row);
    cursorId = data.nextCursorId;
}
