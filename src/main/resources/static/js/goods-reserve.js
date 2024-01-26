// 좌석 예약 페이지 초기화 메서드
function initGoodsReservePage() {
    NProgress.start();
    var token; // jwt 토큰
    // 헤더 로딩
    $("#headers").load("/header.html", function (response, status, xhr) {
        checkLoginStatus();
    });
    token = Cookies.get('Authorization');

    var placeInfo = { // 공연장 정보 초기 설정
        1: { // 공연장 id. 수동으로 관리해야함
            map: [ // 공연장 좌석 배치도. 수동으로 관리해야함
                'AAAAAAAAAA',
                'BBBBBBBBBB',
                'BBBBBBBBBB',
                'CCCCCCCCCC',
                'CCCCCCCCCC',
                'CCCCCCCCCC',
                'DDDDDDDDDD',
                'DDDDDDDDDD',
                'DDDDDDDDDD',
                'DDDDDDDDDD'
            ],
            zoneTypes: ['A', 'B', 'C', 'D'], // 공연장 구역 목록. 수동으로 관리해야함
            seats: {},
            legend: {
                node: $('#legend'),
                items: []
            }
        }
    };
    var currentPlaceId = 1; // 현재 공연장 id
    var seatCount = placeInfo[currentPlaceId]['zoneTypes'].reduce((obj, key) => { // 좌석 만들어줄때 좌석등급마다 번호 지정용
        obj[key] = 1;
        return obj;
    }, {});
    var zoneGradeInfo = {}; // 현재 공연의 좌석등급 정보
    var scheduleId, goodsId; // 회차 id, 공연 id.
    var currentViewAuctionLabel = -1; // 현재 보는중인 경매 좌석의 label
    var auctionSeats = []; // 경매 좌석 목록
    var auctionSeatsInfo = {}; // 경매 좌석 상세 정보 {A-2: {}}
    var auctionSSEConnection; // 경매 sse 연결 객체
    var reservedSeats = []; // 예약된 좌석 목록
    var selectSeats = []; // 선택한 좌석 목록 '1-1-1' -> {scheduleId:1, zoneGradeId:1, seatNumber:1}

    // 쿼리 파라미터 초기화
    function initQueryParams() {
        const queryString = window.location.search;
        const urlParams = new URLSearchParams(queryString);

        scheduleId = urlParams.get('scheduleId');
        goodsId = urlParams.get('goodsId');
    }

    // 공연 가격 정보 로딩
    initRequiredData();

    // 유저 포인트 업데이트
    updateUserPointText();

    // 경매 정보 받아오기
    function getAuctionInfo() {
        if (currentViewAuctionLabel === -1) {
            errorAlert('경매 좌석을 선택해주세요!');
            return;
        }
        $.ajax({
            url: `${getUrl()}/api/v1/auctions/${auctionSeatsInfo[currentViewAuctionLabel].auctionId}`,
            type: "GET",
            headers: {
                "Authorization": token
            },
            success: function (data) {
                // 경매 정보 갱신
                data = data['data'];
                $('#bid-select-seat').text(currentViewAuctionLabel);
                $('#bid-current-price').text(data['bidPrice']);
                $('#bid-start-price').text(data['startPrice']);
                let leftTime = Math.floor(data['remainTimeSeconds']);
                auctionCloseCountdownStart(leftTime);
                initMyBidStatus();
                getLast3Bids();
            },
            error: function (jqXHR, textStatus) {
                errorAlert('경매 정보 갱신 중 오류가 발생했습니다! 다시 시도해주세요.');
            }
        });
    }

    // 경매 새로고침 버튼 클릭
    $('#auction-info-refresh-btn').click(function () {
        getAuctionInfo();
    });


    // 공연장 그리기
    var $cart = $('#selected-seats'),
        $counter = $('#counter'),
        $total = $('#total'),
        sc = $('#seat-map').seatCharts({
            map: placeInfo[currentPlaceId].map,
            seats: placeInfo[currentPlaceId].seats,
            naming: {
                top: false,
                getLabel: function (character, row, column) {
                    return `${character}-${seatCount[character]++}`;
                },
                getId: function (character, row, column) {
                    return `${character}-${seatCount[character]}`;
                }
            },
            legend: {
                node: $('#legend'),
                items: placeInfo[currentPlaceId].legend.items
            },
            click: function () {
                if (this.status() == 'available') {
                    if (selectSeats.length >= 2) {
                        errorAlert('좌석은 최대 2개까지 선택할 수 있습니다.');
                        return 'available';
                    }
                    $('<li>' + this.data().category + ' 좌석 # ' + this.settings.label + ': <b>' + this.data().price + '₩</b> <a href="#" class="cancel-cart-item">[cancel]</a></li>')
                        .attr('id', 'cart-item-' + this.settings.id)
                        .data('seatId', this.settings.id)
                        .appendTo($cart);

                    const [zoneName, seatNumber] = this.settings.label.split('-');
                    selectSeats.push(`${scheduleId}-${zoneGradeInfo[zoneName]['zoneGradeId']}-${seatNumber}`); // 선택한 좌석 장바구니에 넣기
                    $counter.text(sc.find('selected').length + 1);
                    $total.text(recalculateTotal(sc) + this.data().price);
                    return 'selected';
                } else if (this.status() == 'selected') {
                    $counter.text(sc.find('selected').length - 1);
                    $total.text(recalculateTotal(sc) - this.data().price);
                    const [zoneName, seatNumber] = this.settings.label.split('-');
                    $('#cart-item-' + this.settings.id).remove();
                    selectSeats = selectSeats.filter((value) => { // 선택한 좌석 장바구니에서 빼기
                        return value !== `${scheduleId}-${zoneGradeInfo[zoneName]['zoneGradeId']}-${seatNumber}`;
                    });

                    return 'available';
                } else if (this.status() == 'unavailable') {
                    return 'unavailable';
                } else if (this.status() == 'auction') { // 경매 좌석 눌렀을 때
                    currentViewAuctionLabel = this.settings.label;
                    // 경매 정보 받아오기
                    getAuctionInfo();
                    // SSE 설정
                    const requestUrl = `${getUrl()}/api/v1/auctions/${auctionSeatsInfo[currentViewAuctionLabel].auctionId}/bids/sse`;
                    if (auctionSSEConnection !== undefined) {
                        auctionSSEConnection.close();
                    }
                    const eventSource = new EventSource(requestUrl);
                    eventSource.onmessage = function (event) {
                        // 경매 현재가 갱신
                        $('#bid-current-price').text(event.data);
                        // 내가 최고 입찰인지 갱신

                        // 경매 최근 입찰 내역 갱신
                    };
                    eventSource.onerror = function (error) {
                        console.error('EventSource failed:', error);
                        eventSource.close(); // 연결 종료
                    };
                    auctionSSEConnection = eventSource;
                    return 'auction';
                } else {
                    return this.style();
                }
            }
        });

    // 좌석 예매 버튼 클릭
    $('#reserve-btn').click(function () {
        const requestData = {
            price: $('#total').text(),
            reservationSeats: []
        };
        if (selectSeats.length === 0) {
            errorAlert('좌석을 선택해주세요!');
            return;
        }
        for (const selectSeat of selectSeats) {
            const [scheduleId, zoneGradeId, seatNumber] = selectSeat.split('-');
            requestData['reservationSeats'].push({
                scheduleId: scheduleId,
                zoneGradeId: zoneGradeId,
                seatNumber: seatNumber
            });
        }
        $.ajax({
            url: `${getUrl()}/api/v1/reservations`,
            contentType: "application/json; charset=utf-8",
            type: "POST",
            headers: {
                "Authorization": token
            },
            data: JSON.stringify(requestData),
            success: function (data) {
                okAlert('예매 성공했습니다.');
                location.reload();
            },
            error: function (jqXHR, textStatus) {
                errorAlert(jqXHR.responseJSON.message);
            }
        });
    });

    // 경매 입찰 버튼 클릭
    $('#bid-btn').click(function () {
        const price = parseInt($('#bid-input').val());
        const bidCurrentPrice = parseInt($('#bid-current-price').text());
        if (isNaN(price) || isNaN(bidCurrentPrice)) {
            errorAlert('경매 좌석을 선택해주세요!');
            return;
        }

        if (price < bidCurrentPrice * 1.05) {
            errorAlert('최소 현재 입찰가의 5%보다 높게 입찰해야 합니다.');
            return;
        }
        const requestData = {
            price: price
        };
        $.ajax({
            url: `${getUrl()}/api/v1/auctions/${auctionSeatsInfo[currentViewAuctionLabel].auctionId}/bids`,
            contentType: "application/json; charset=utf-8",
            type: "POST",
            headers: {
                "Authorization": token
            },
            data: JSON.stringify(requestData),
            success: function (data) {
                okAlert('입찰 성공했습니다.');
                updateUserPointText();
                getLast3Bids();
                initMyBidStatus();
            },
            error: function (jqXHR, textStatus) {
                errorAlert(jqXHR.responseJSON.message);
            }
        });
    })

    // 좌석 선택 취소
    $('#selected-seats').on('click', '.cancel-cart-item', function () {
        sc.get($(this).parents('li:first').data('seatId')).click();
    });
    initAuctionSeats(scheduleId, goodsId);

    // 선택한 좌석 카운팅
    function recalculateTotal(sc) {
        var total = 0;

        //basically find every selected seat and sum its price
        sc.find('selected').each(function () {
            total += this.data().price;
        });

        return total;
    }

    // 필요한 정보 초기화
    function initRequiredData() {
        initQueryParams();
        initZoneGradeInfo(goodsId);
    }

    // 좌석등급정보 동기로 불러옴
    function initZoneGradeInfo(goodsId) {
        $.ajax({
            url: `${getUrl()}/api/v1/goods/${goodsId}/seats`,
            type: "GET",
            async: false,
            headers: {
                "Authorization": token
            },
            success: function (data) {
                NProgress.done();
                const seatInfos = data['data']['seatInfos'];
                for (const seatInfo of seatInfos) {
                    zoneGradeInfo[seatInfo['zoneName']] = {
                        zoneGradeId: seatInfo['zoneGradeId'],
                        price: seatInfo['price'],
                        gradeName: seatInfo['gradeName']
                    }
                }

                for (const zoneType of placeInfo[currentPlaceId].zoneTypes) {
                    placeInfo[currentPlaceId].seats[zoneType] = {
                        price: zoneGradeInfo[zoneType].price,
                        classes: `${zoneType}-class`,
                        category: `${zoneGradeInfo[zoneType].gradeName}`
                    };
                    placeInfo[currentPlaceId].legend.items.push(
                        [`${zoneType}`, 'available', `${zoneGradeInfo[zoneType].gradeName}`],
                    )
                }
                placeInfo[currentPlaceId].legend.items.push(
                    ['', 'unavailable', '예약된 좌석'],
                    ['', 'available', '경매 좌석']
                );
            },
            error: function (jqXHR, textStatus) {
                errorAlert(jqXHR.responseJSON.message);
            }
        });
    }

    // 좌석 설정 끝나면 경매좌석 설정 시작
    function initAuctionSeats(scheduleId, goodsId) {
        $.ajax({
            url: `${getUrl()}/api/v1/goods/${goodsId}/auction-seats?scheduleId=${scheduleId}`,
            type: "GET",
            headers: {
                "Authorization": token
            },
            success: function (data) {
                console.log(data);
                for (const seatInfo of data['data']['seatInfos']) {
                    const seatLabel = `${seatInfo.zoneName}-${seatInfo.seatNumber}`;
                    auctionSeats.push(seatLabel);
                    auctionSeatsInfo[seatLabel] = {
                        "zoneName": seatInfo.zoneName,
                        "gradeName": seatInfo.gradeName,
                        "price": seatInfo.price,
                        "zoneGradeId": seatInfo.zoneGradeId,
                        "auctionId": seatInfo.auctionId,
                        "seatNumber": seatInfo.seatNumber,
                        "deadline": seatInfo.deadline,
                        "isEnded": seatInfo.isEnded
                    }
                }
                // 경매 좌석 상태 auction으로 변경
                sc.get(auctionSeats).status('auction');
                initReservedSeats(scheduleId);
            },
            error: function (jqXHR, textStatus) {
                errorAlert(jqXHR.responseJSON.message);
            }
        });
    }

    // 경매좌석 설정 끝나면 예약된 좌석 설정
    function initReservedSeats(scheduleId) {
        $.ajax({
            url: `${getUrl()}/api/v1/goods/${scheduleId}/reserved-seats`,
            type: "GET",
            headers: {
                "Authorization": token
            },
            success: function (data) {
                for (const {zoneGradeId, seatNumber} of data['data']) {
                    for (const key of Object.keys(zoneGradeInfo)) {
                        if (zoneGradeInfo[key].zoneGradeId === zoneGradeId) {
                            reservedSeats.push(`${key}-${seatNumber}`);
                            break;
                        }
                    }
                }
                // 이미 예매된 좌석 비활성화
                sc.get(reservedSeats).status('unavailable');
                const reservedSeatCount = {}
                for (const seat of reservedSeats) {
                    const [zoneName, _] = seat;
                    reservedSeatCount[zoneName] = (reservedSeatCount[zoneName] + 1) || 1;
                }
                // 잔여석 계산
                for (const zone of placeInfo[currentPlaceId].zoneTypes) {
                    const remainSeatCount = (seatCount[zone] - 1) - (reservedSeatCount[zone] || 0);
                    $(`<span>${zoneGradeInfo[zone].gradeName}: ${remainSeatCount}석</span><br>`).appendTo('#remaining-seat-info');
                }
            },
            error: function (jqXHR, textStatus) {
                errorAlert(jqXHR.responseJSON.message);
            }
        });
    }

    function updateUserPointText() {
        $.ajax({
            url: `${getUrl()}/api/v1/users/points`,
            type: "GET",
            headers: {
                "Authorization": token
            },
            success: function (data) {
                $("#my-point").text(data['data']);
            },
            error: function (jqXHR, textStatus) {
                errorAlert(jqXHR.responseJSON.message);
            }
        });
    }

    var timerId = -1;
    var auctionLeftTime = -1;

    // 경매 종료 카운트다운, 시간 초 단위로 받음
    function auctionCloseCountdownStart(leftTime) {
        if (timerId !== -1) {
            clearInterval(timerId);
        }
        auctionLeftTime = leftTime;
        timerId = setInterval(countdown, 1000);
    }

    function countdown() {
        if (auctionLeftTime > 0) {
            auctionLeftTime--;
            var day = Math.floor(auctionLeftTime / 86400); // 일
            var hour = Math.floor(auctionLeftTime % 86400 / 3600); // 시
            var min = Math.floor(auctionLeftTime % 86400 % 3600 / 60); // 분
            var sec = Math.floor(auctionLeftTime % 86400 % 3600 % 60); // 초
            $('#bid-countdown').text(`${day}일 ${hour}시 ${min}분 ${sec}초`);
        } else {
            okAlert('경매가 종료되었습니다!');
            clearInterval(timerId);
        }
    }

    // 최근 입찰 내역 3개 조회
    function getLast3Bids() {
        $.ajax({
            url: `${getUrl()}/api/v1/auctions/${auctionSeatsInfo[currentViewAuctionLabel].auctionId}/bids/last?limit=${3}`,
            type: "GET",
            headers: {
                "Authorization": token
            },
            success: function (data) {
                console.log(data);
                for (let i = 0; i < 3; i++) {
                    $(`#bid-last-${i + 1}-info`).text('정보 없음');
                }
                var bids = data['data'];
                for (let i = 0; i < bids.length; i++) {
                    $(`#bid-last-${i + 1}-info`).text(`${bids[i].nickname}님 ${bids[i].price}원`);
                }
            },
            error: function (jqXHR, textStatus) {
                errorAlert(jqXHR.responseJSON.message);
            }
        });
    }

    function initMyBidStatus() {
        $.ajax({
            url: `${getUrl()}/api/v1/auctions/${auctionSeatsInfo[currentViewAuctionLabel].auctionId}/bids/is-highest`,
            type: "GET",
            headers: {
                "Authorization": token
            },
            success: function (data) {
                if (data['data']) {
                    $('#my-bid-status').text('입찰 중입니다!');
                } else {
                    $('#my-bid-status').text('경매에 참여하지 않았거나 패찰 당했습니다.');
                }
            },
            error: function (jqXHR, textStatus) {
                errorAlert(jqXHR.responseJSON.message);
            }
        });
    }
}
