$(document).ready(function () {
    // fetchGoodsInfos 함수는 'goods' 클래스를 가진 페이지에서만 호출됩니다.
    if ($('body').hasClass('goodsClass')) {
        reissueToken((token => {
            fetchGoodsInfos(token);
            fetchPlace();
        }));
    }

    // 공연 추가 페이지 상품 선택 시 이미지 변경
    $(document).on('change', '#goodsInfoLabel', function () {
        var selectedOption = $(this).find('option:selected');
        var s3Url = selectedOption.data('s3Url');

        if (s3Url) {
            $('#goodsInfoImage').attr('src', s3Url);
        } else {
            $('#goodsInfoImage').attr('src', '기본이미지URL');
        }
    });

});

let zones = [];

// 구역 추가 함수
function addZone() {
    let zoneName = $('#zoneName').val().trim();
    let zoneSeats = $('#zoneSeats').val().trim();

    // 입력 유효성 검사
    if (!zoneName || !zoneSeats || isNaN(parseInt(zoneSeats, 10))) {
        alert('유효한 구역명과 좌석 수를 입력해주세요.');
        return;
    }

    // 구역 정보 객체 생성
    let zoneInfo = {zoneName, zoneSeats: parseInt(zoneSeats, 10)};
    zones.push(zoneInfo);

    // 테이블에 구역 정보 행 추가
    let newRow = `<tr>
        <td>${zoneName}</td>
        <td>${zoneSeats}</td>
        <td><button class="btn remove-zone" onclick="removeZone(this)">제거</button></td>
    </tr>`;
    $('#zonesTable tbody').append(newRow);

    // 입력 필드 초기화
    $('#zoneName').val('');
    $('#zoneSeats').val('');
}

function removeZone(button) {
    let row = $(button).closest('tr');
    let zoneNameToRemove = row.find('td').first().text();

    // zones 배열에서 해당 구역 정보 제거
    zones = zones.filter(zone => zone.zoneName !== zoneNameToRemove);

    // 테이블에서 해당 행 제거
    row.remove();
}

// place js
function submitPlace(token) {
    const name = $('#placeName').val().trim();
    const address = $('#address').val().trim();

    // 입력 유효성 검사
    let validZones = zones.filter(zone => zone.zoneSeats && !isNaN(zone.zoneSeats));
    if (validZones.length !== zones.length) {
        alert('모든 구역에 유효한 좌석 수를 입력해야 합니다.');
        return;
    }

    const placeCreateRequest = {
        name: name,
        address: address,
        zoneInfos: validZones.map(zone => ({
            name: zone.zoneName,
            seatNumber: parseInt(zone.zoneSeats, 10)
        }))
    };

    $.ajax({
        url: '/api/v1/admin/places',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(placeCreateRequest),
        beforeSend: function (xhr) {
            if (token) {
                xhr.setRequestHeader('Authorization', token);
            }
        },
        success: function (response) {
            handleSuccess(response);
        },
        error: function (xhr, status, error) {
            handleError(xhr.statusText);
        }
    });


    function handleSuccess(response) {
        console.log('Success:', response);
        alert('공연장이 성공적으로 추가되었습니다.');
        $('#zonesTable tbody').empty(); // 테이블 내용 초기화
        zones = []; // zones 배열 초기화
    }

// 에러 핸들러
    function handleError(xhr, status, error) {
        console.error('Error:', error);
        alert('오류가 발생했습니다. 공연장을 추가하지 못했습니다. 오류: ' + error);
    }

}


/// goodsInfo
function submitGoodsInfo(token) {
    // 폼 데이터 가져오기
    var jsonPart = {
        name: $('#title').val().trim(), // 'title' 필드를 'name'으로 매핑
        description: $('#content').val().trim(), // 'content' 필드를 'description'으로 매핑
        ageGrade: parseInt($('#age').val().trim(), 10), // 'age' 필드를 'ageGrade'로 매핑
        runningTime: parseInt($('#time').val().trim(), 10), // 'time' 필드를 'runningTime'으로 매핑
        categoryName: $('#category').val().trim()// 'category' 필드를 'categoryName'으로 매핑
    };

    var isValid = isValidPerformanceInput(
        $('#title').val().trim(),
        $('#content').val().trim(),
        $('#time').val().trim(),
        $('#age').val().trim(),
        $('#category').val().trim()
    );
    if (!isValid) {
        return;
    }

    // FormData 객체 생성 및 파일 추가
    var formData = new FormData();
    var photo = $('#photo')[0].files[0];
    if (photo) {
        formData.append('files', photo); // 사진 파일
    }

    // 선택된 모든 파일을 FormData에 추가
    var files = $('#photo')[0].files;
    for (var i = 0; i < files.length; i++) {
        formData.append('files', files[i]); // 각 파일을 'files' 키로 추가
    }

    // JSON 데이터를 Blob으로 변환하여 FormData에 추가
    formData.append('goodsInfoCreateRequest', new Blob([JSON.stringify(jsonPart)], {
        type: 'application/json'
    }));

    $.ajax({
        url: '/api/v1/admin/goods-infos',
        type: 'POST',
        contentType: false, // multipart/form-data를 위해 false로 설정
        processData: false, // jQuery가 데이터를 처리하지 않도록 설정
        data: formData,
        beforeSend: function (xhr) {
            if (token) {
                xhr.setRequestHeader('Authorization', token);
            }
        },
        success: function (response) {
            alert('공연 정보가 성공적으로 추가되었습니다.');
            console.log(response);
            movePageWithToken("/goods.html");
        },
        error: function (xhr, status, error) {
            alert('공연 정보를 추가하는 중 오류가 발생했습니다: ' + error);
        },
    });
}

// 공연 정보 유효성 검사
function isValidPerformanceInput(title, content, time, age, category) {
    if (!title || !content || !time || !age || !category) {
        alert('모든 필드를 채워주세요.');
        return false;
    }
    // 여기에 추가 유효성 검사 로직을 구현할 수 있습니다.
    return true;
}


// goods
// 페이지 로드시 공연정보 가져오기
function fetchGoodsInfos(token) {
    $.ajax({
        url: '/api/v1/goods-infos',
        type: 'GET',
        beforeSend: function (xhr) {
            if (token) {
                xhr.setRequestHeader('Authorization', token);
            }
        },
        success: function (response) {
            console.log(response);
            $('#goodsInfoLabel').empty(); // 셀렉트 박스 초기화
            var option = new Option("공연 정보를 선택해 주세요");
            $('#goodsInfoLabel').append(option);
            response.data.forEach(function (goodsInfo) {
                var option = new Option(goodsInfo.name, goodsInfo.goodsInfoId);
                // s3Url이 있는 경우, 옵션에 해당 URL을 데이터 속성으로 저장합니다.
                if (goodsInfo.s3Url) {
                    $(option).data('s3Url', goodsInfo.s3Url);
                }
                $('#goodsInfoLabel').append(option);
            });
        },
        error: function (xhr, status, error) {
            alert('상품 정보를 가져오는 데 실패했습니다: ' + error);
        }
    });
}

//페이지 로드시 공연장 조회 정보 가져오기
function fetchPlace() {
    $.ajax({
        url: '/api/v1/places',
        type: 'GET',
        success: function (response) {
            console.log(response);
            $('#placeLabel').empty(); // 셀렉트 박스 초기화
            var option = new Option("공연장을 선택해 주세요");
            $('#placeLabel').append(option);
            response.data.forEach(function (place) { // 'response.data'가 아닌 'response'를 순회
                var option = new Option(place.name, place.placeId);
                $('#placeLabel').append(option);
            });
        },
        error: function (xhr, status, error) {
            alert('공연장 정보를 가져오는 데 실패했습니다: ' + error);
        }
    });
}


// 공연 정보를 생성하고 스케줄을 추가하는 함수
function submitGoodsAndSchedule(token) {
    var goodsInfoId = $('#goodsInfoLabel').val(); // 선택된 상품 정보 ID
    var placeId = $('#placeLabel').val(); // 선택된 공연장 ID
    var title = $('#title').val().trim(); // 공연 제목
    var startDate = $('#startDate').val().trim(); // 시작 일자
    var endDate = $('#endDate').val().trim(); // 종료 일자
    var startTime = $('#startTime').val().trim(); // 상영 시작 시간

    // 상품 생성 요청 데이터 구성
    var goodsCreateRequest = {
        title: title,
        startDate: startDate,
        endDate: endDate,
        startTime: startTime,
    };

    // 상품 정보 생성 및 스케줄 추가 AJAX 요청
    $.ajax({
        url: `/api/v1/admin/goods-infos/${goodsInfoId}/goods?placeId=${placeId}`,
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(goodsCreateRequest),
        beforeSend: function (xhr) {
            if (token) {
                xhr.setRequestHeader('Authorization', token);
            }
        },
        success: function (response) {
            alert('공연 정보가 성공적으로 추가되었습니다.');
            console.log(response);
            if (response && response.data.goodsId) {
                localStorage.setItem('goodsId', response.data.goodsId);
            }
            movePageWithToken("/grade.html");
        },
        error: function (xhr, status, error) {
            alert('공연 정보를 추가하는 중 오류가 발생했습니다: ' + error);
        }
    });
}


// 등급 생성 grade.html

function goToNextPage() {
    var savedCount = $('#gradesTable .saveRowBtn[disabled]').length;
    var totalCount = $('#gradesTable .saveRowBtn').length;

    if (totalCount > 0 && savedCount !== totalCount) {
        alert('모든 좌석 정보가 저장되지 않았습니다. 저장을 완료해 주세요.');
    } else {
        movePageWithToken("/zoneGrade.html");
    }
}

function addRowToGradeTable() {
    var gradeName = $("#gradeName").val();
    var normalPrice = $("#normalPrice").val();
    var auctionPrice = $("#auctionPrice").val();

    $("#gradesTable tbody").append(newRow);
    if (!gradeName || !normalPrice || !auctionPrice) {
        alert("모든 필드를 채워주세요.");
        return;
    }


    var newRow = `<tr>
        <td>${gradeName}</td>
        <td>${normalPrice}</td>
        <td>${auctionPrice}</td>
        <td><button class="saveRowBtn">저장</button></td>
        <td><button class="deleteRowBtn">삭제</button></td>
    </tr>`;
    $("#gradesTable tbody").append(newRow);

    $("#gradeName").val('');
    $("#normalPrice").val('');
    $("#auctionPrice").val('');
}

function deleteGradeRow(button) {
    $(button).closest('tr').remove();
}

function saveGradeData(button, token) {
    var row = $(button).closest('tr');
    var gradeName = row.find('td:eq(0)').text();
    var normalPrice = row.find('td:eq(1)').text();
    var auctionPrice = row.find('td:eq(2)').text();
    var goodsId = localStorage.getItem('goodsId');

    // 서버로 데이터 전송
    $.ajax({
        url: `/api/v1/admin/goods/${goodsId}/grades`,
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({name: gradeName, normalPrice: normalPrice, auctionPrice: auctionPrice}),
        beforeSend: function (xhr) {
            // 토큰 설정
            xhr.setRequestHeader('Authorization', token);
        },
        success: function (response) {
            alert('저장 성공!');
            $(button).prop('disabled', true); // 버튼 비활성화
            saveRequested = true; // 저장 요청 플래그를 true로 설정
        },
        error: function (xhr, status, error) {
            alert('저장 실패: ' + error);
        }
    });
}


