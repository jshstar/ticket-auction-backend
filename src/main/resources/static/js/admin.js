var goodsInfoFetched = false;
var auctionFetched = false;
var zonesInfo = [];

$(document).ready(function () {
    // fetchGoodsInfos 함수는 'goods' 클래스를 가진 페이지에서만 호출됩니다.
    if ($('body').hasClass('goodsClass') && !goodsInfoFetched) {
        reissueToken((token => {
            fetchGoodsInfos(token);
            fetchPlace();
        }));
        goodsInfoFetched = true;
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

    // zone-grade
    if ($('body').hasClass('zoneGradeClass')) {
        if (zonesData.length === 0 || gradesData.length === 0) {
            var goodsId = localStorage.getItem('goodsId');
            if (localStorage.getItem("zoneGradeResponses") !== null) {
                localStorage.removeItem("zoneGradeResponses");
            }
            loadZoneAndGradeData(goodsId);
        }
    }

    $(document).on('change', '.grade-select', function () {
        var selectedGradeId = $(this).val();
        var selectedGrade = gradesData.find(grade => grade.gradeId.toString() === selectedGradeId);

        if (selectedGrade) {
            var auctionPrice = selectedGrade.auctionPrice;
            $(this).closest('tr').find('.price-info').text(auctionPrice.toLocaleString() + ' 원');
        }

        // 저장 버튼 활성화
        $(this).closest('tr').find('.save-btn').prop('disabled', false);
    });

    $(document).on('click', '.save-btn', function () {
        var $row = $(this).closest('tr');
        var zoneId = $row.find('.grade-select').data('zone-id');
        var gradeId = $row.find('.grade-select').val();
        reissueToken((token => {
            sendCreateRequest(zoneId, gradeId, $row, token);
        }));
    });

    // auction 동작 헨들러
    // 저장 버튼 클릭 이벤트 핸들러

    if ($('body').hasClass('auctionClass') && !auctionFetched) {
        var goodsId = localStorage.getItem('goodsId'); // localStorage에서 goodsId를 가져옵니다.
        if (goodsId) {
            fetchSchedules(goodsId);
            fetchZonesFromLocalStorage(); // 로컬 스토리지에서 구역 데이터를 가져오는 함수를 호출합니다.
            fetchZones(goodsId)
            auctionFetched = true;
        }
    }


    $(document).on('click', '.save-button', function () {
        const rowElement = $(this).closest('tr');
        const seatNumber = rowElement.find('.seat-number-input').val();
        const scheduleId = $('#sequence-select').val();
        const zoneGradeName = rowElement.data('zone-name');
        const zoneGradeId = rowElement.data('zone-id');

        reissueToken((token) => {
            if (validateZoneNameAndSeatNumber(zonesInfo, seatNumber, zoneGradeName) &&
                !isSeatAlreadySaved(scheduleId, zoneGradeName, seatNumber)) {
                saveAuctionSeat(scheduleId, zoneGradeId, seatNumber, rowElement, token);
            } else {
                errorAlert('유효하지 않은 좌석 번호이거나 이미 저장된 좌석 번호입니다.');
            }
        });
    });

    // 삭제 버튼 클릭 이벤트 핸들러
    $(document).on('click', '.delete-button', function () {
        $(this).closest('tr').remove();
    });

    $('#add-zone-button').click(function () {
        const selectedZoneOption = $('#zone-select').find('option:selected');
        const selectedZoneName = selectedZoneOption.text();
        const selectedZoneId = selectedZoneOption.val();

        if (selectedZoneName !== "구역 선택") {
            addAuctionSeatRow(selectedZoneName, selectedZoneId);
        }
    });

    $('#sequence-select').change(function () {
        // 표의 tbody 부분을 비워 초기화합니다.
        $('#auction-table tbody').empty();
    });

});

let zones = [];
// place js
// 구역 추가 함수
function addZone() {
    let zoneName = $('#placeZoneName').val().trim();
    let zoneSeats = $('#placeZoneSeat').val().trim();

    // 입력 유효성 검사
    if (!zoneName || !zoneSeats || isNaN(parseInt(zoneSeats, 10))) {
        errorAlert('유효한 구역명과 좌석 수를 입력해주세요.');
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
    $('#placeZoneName').val('');
    $('#placeZoneSeat').val('');
}

function removeZone(button) {
    let row = $(button).closest('tr');
    let zoneNameToRemove = row.find('td').first().text();

    // zones 배열에서 해당 구역 정보 제거
    zones = zones.filter(zone => zone.zoneName !== zoneNameToRemove);

    // 테이블에서 해당 행 제거
    row.remove();
}

function submitPlace(token) {
    const name = $('#placeName').val().trim();
    const address = $('#address').val().trim();

    // 입력 유효성 검사
    let validZones = zones.filter(zone => zone.zoneSeats && !isNaN(zone.zoneSeats));
    if (validZones.length !== zones.length) {
        errorAlert('모든 구역에 유효한 좌석 수를 입력해야 합니다.');
        return;
    }

    const placeCreateRequest = {
        name: name,
        address: address,
        zoneInfos: validZones.map(zone => ({
            zone: zone.zoneName,
            seatNumber: parseInt(zone.zoneSeats, 10)
        }))
    };

    $.ajax({
        url: `${getUrl()}/api/v1/admin/places`,
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
        okAlert('공연장이 성공적으로 추가되었습니다.');
        $('#zonesTable tbody').empty(); // 테이블 내용 초기화
        zones = []; // zones 배열 초기화
    }

// 에러 핸들러
    function handleError(xhr, status, error) {
        console.error('Error:', error);
        errorAlert('오류가 발생했습니다. 공연장을 추가하지 못했습니다. 오류: ' + error);
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
        url: `${getUrl()}/api/v1/admin/goods-infos`,
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
            okAlert('공연 정보가 성공적으로 추가되었습니다.');
            movePageWithToken("/admin/goods.html");
        },
        error: function (xhr, status, error) {
            errorAlert('공연 정보를 추가하는 중 오류가 발생했습니다: ' + error);
        },
    });
}

// 공연 정보 유효성 검사
function isValidPerformanceInput(title, content, time, age, category) {
    if (!title || !content || !time || !age || !category) {
        errorAlert('모든 필드를 채워주세요.');
        return false;
    }
    // 여기에 추가 유효성 검사 로직을 구현할 수 있습니다.
    return true;
}


// goods
// 페이지 로드시 공연정보 가져오기
function fetchGoodsInfos(token) {
    $.ajax({
        url: `${getUrl()}/api/v1/goods-infos`,
        type: 'GET',
        beforeSend: function (xhr) {
            if (token) {
                xhr.setRequestHeader('Authorization', token);
            }
        },
        success: function (response) {
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
            errorAlert('상품 정보를 가져오는 데 실패했습니다: ' + error);
        }
    });
}

//페이지 로드시 공연장 조회 정보 가져오기
function fetchPlace() {
    $.ajax({
        url: `${getUrl()}/api/v1/places`,
        type: 'GET',
        success: function (response) {
            $('#placeLabel').empty(); // 셀렉트 박스 초기화
            var option = new Option("공연장을 선택해 주세요");
            $('#placeLabel').append(option);
            response.data.forEach(function (place) { // 'response.data'가 아닌 'response'를 순회
                var option = new Option(place.name, place.placeId);
                $('#placeLabel').append(option);
            });
        },
        error: function (xhr, status, error) {
            errorAlert('공연장 정보를 가져오는 데 실패했습니다: ' + error);
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
        url: `${getUrl()}/api/v1/admin/goods-infos/${goodsInfoId}/goods?placeId=${placeId}`,
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(goodsCreateRequest),
        beforeSend: function (xhr) {
            if (token) {
                xhr.setRequestHeader('Authorization', token);
            }
        },
        success: function (response) {
            okAlert('공연 정보가 성공적으로 추가되었습니다.');
            if (response && response.data.goodsId) {
                localStorage.setItem('goodsId', response.data.goodsId);
            }
            movePageWithToken("/admin/grade.html");
        },
        error: function (xhr, status, error) {
            errorAlert('공연 정보를 추가하는 중 오류가 발생했습니다: ' + error);
        }
    });
}


// 등급 생성 grade.html

function goToNextPage() {
    var savedCount = $('#gradesTable .saveRowBtn[disabled]').length;
    var totalCount = $('#gradesTable .saveRowBtn').length;

    if (totalCount > 0 && savedCount !== totalCount) {
        errorAlert('모든 좌석 정보가 저장되지 않았습니다. 저장을 완료해 주세요.');
    } else {
        movePageWithToken("/admin/zone-grade.html");
    }
}

function addRowToGradeTable() {
    var gradeName = $("#gradeName").val();
    var normalPrice = $("#normalPrice").val();
    var auctionPrice = $("#auctionPrice").val();

    $("#gradesTable tbody").append(newRow);
    if (!gradeName || !normalPrice || !auctionPrice) {
        errorAlert("모든 필드를 채워주세요.");
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
        url: `${getUrl()}/api/v1/admin/goods/${goodsId}/grades`,
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({name: gradeName, normalPrice: normalPrice, auctionPrice: auctionPrice}),
        beforeSend: function (xhr) {
            // 토큰 설정
            xhr.setRequestHeader('Authorization', token);
        },
        success: function (response) {
            okAlert('저장 성공!');
            var row = $(button).closest('tr');
            row.find('.saveRowBtn').prop('disabled', true).addClass('saved'); // 저장 버튼 비활성화 및 클래스 추가
            row.find('.deleteRowBtn').prop('disabled', true); // 삭제 버튼 비활성화
            saveRequested = true; // 저장 요청 플래그를 true로 설정
        },
        error: function (xhr, status, error) {
            errorAlert('저장 실패: ' + error);
        }
    });
}

//ZoneGrade
var zonesData = []
var gradesData = []

function loadZoneAndGradeData(goodsId) {
    // Zones 데이터를 가져옵니다.
    if (zonesData.length === 0) {
        $.ajax({
            url: `${getUrl()}/api/v1/zones?goodsId=${goodsId}`,
            type: 'GET',
            success: function (response) {
                zonesData = response.data;
                populateZones(zonesData);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log('Error fetching zones:', textStatus, errorThrown);
            }
        });
    }

    // Grades 데이터를 가져옵니다.
    if (gradesData.length === 0) {
        $.ajax({
            url: `${getUrl()}/api/v1/goods/${goodsId}/grade`,
            type: 'GET',
            success: function (response) {
                gradesData = response.data;
                populateGrades(gradesData);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log('Error fetching grades:', textStatus, errorThrown);
            }
        });
    }
}


function populateZones(zones) {
    zones.forEach(function (zone, index) {
        $('#zone-grade-table tbody').append(`
            <tr>
                <td>${zone.name}</td>
                <td>${zone.seatNumber}</td>
                <td>
                    <select class="grade-select" id="grade-select-${index}" data-zone-id="${zone.zoneId}">
                        <option value="">선택</option>
                        <!-- 등급 정보가 여기에 들어갈 것 -->
                    </select>
                </td>
                <td class="price-info"></td>
                <td>
                    <button class="save-btn btn" style="background-color: #A9BCD0; color: white;" disabled>저장</button>
                </td>
            </tr>
        `);
    });

    // Zones가 모두 추가된 후에 Grades를 채웁니다.
    populateGrades(gradesData);
}

function populateGrades(grades) {
    $('.grade-select').each(function () {
        var $select = $(this);
        grades.forEach(function (grade) {
            $select.append(`
                <option value="${grade.gradeId}" data-auction-price="${grade.auctionPrice}">${grade.name}</option>
            `);
        });
    });
}

// 저장시 서버에 생성 요청
function sendCreateRequest(zoneId, gradeId, $row, token) {
    $.ajax({
        url: `${getUrl()}/api/v1/admin/zone-grades`,
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({zoneId: zoneId, gradeId: gradeId}),
        beforeSend: function (xhr) {
            if (token) {
                xhr.setRequestHeader('Authorization', token);
            }
        },
        success: function (response) {
            $row.find('.save-btn').prop('disabled', true);
            updateNextPageButtonState(); // 저장 버튼 상태 업데이트
            saveResponse(response);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log('Error creating zone-grade:', textStatus, errorThrown);
        }
    });
}

// 받은 응답 저장
function saveResponse(newResponse) {
    let responses = localStorage.getItem('zoneGradeResponses');
    if (responses) {
        responses = JSON.parse(responses);
    } else {
        responses = [];
    }
    responses.push(newResponse);
    localStorage.setItem('zoneGradeResponses', JSON.stringify(responses));
}

// "다음 페이지" 버튼 상태를 업데이트하는 함수
function updateNextPageButtonState() {
    var allButtonsDisabled = $('.save-btn').length > 0 && $('.save-btn:enabled').length === 0;
    $('#auction-page-button').prop('disabled', !allButtonsDisabled);
}


// auction


// 스케줄 목록을 가져오는 함수
function fetchSchedules(goodsId) {
    $.ajax({
        url: `${getUrl()}/api/v1/goods/${goodsId}/schedules`,
        type: 'GET',
        success: function (response) {
            const schedules = response.data;
            $('#sequence-select').append(new Option("회차 선택"));
            schedules.forEach(schedule => {
                $('#sequence-select').append(new Option(schedule.sequence, schedule.scheduleId));
            });
        },
        error: function (xhr, status, error) {
            console.error('스케줄 목록 가져오기 실패:', status, error);
        }
    });
}

// 구역 목록을 가져오는 함수
// 전역 변수로 구역 정보를 저장할 객체 선언
// 구역 목록을 가져오는 함수
function fetchZones(goodsId) {
    $.ajax({
        url: `${getUrl()}/api/v1/zones?goodsId=${goodsId}`,
        type: 'GET',
        success: function (response) {
            response.data.forEach(zone => {
                zonesInfo.push({
                    zoneId: zone.zoneId,
                    name: zone.name,
                    seatNumber: zone.seatNumber,
                });

            });
        },
        error: function (xhr, status, error) {
            console.error('구역 목록 가져오기 실패:', status, error);
        }
    });
}

function fetchZonesFromLocalStorage() {
    var zoneGradeResponses = JSON.parse(localStorage.getItem('zoneGradeResponses'));
    if (zoneGradeResponses && Array.isArray(zoneGradeResponses)) {
        $('#zone-select').empty().append(new Option("구역 선택", ""));
        zoneGradeResponses.forEach(response => {
            if (response.data) {
                $('#zone-select').append(new Option(response.data.zoneName, response.data.zoneGradeId));
            }
        });
    } else {
        console.log("No data found in localStorage for zoneGradeResponses");
    }
}


// 경매 좌석 번호 행 추가 함수
function addAuctionSeatRow(zoneName, zoneId) {
    $('#auction-table tbody').append(`
        <tr data-zone-id="${zoneId}" data-zone-name="${zoneName}">
            <td>${zoneName}</td>
            <td><input type="number" class="seat-number-input" /></td>
            <td>
                <button class="save-button btn" id="auction-seat-save-btn">저장</button>
                <button class="delete-button btn btn-danger">삭제</button>
            </td>
        </tr>
    `);
}


// 경매 좌석 번호 저장 함수
function saveAuctionSeat(scheduleId, zoneId, seatNumber, rowElement, token) {
    const auctionCreateRequest = {
        seatNumber: parseInt(seatNumber, 10)
    };

    $.ajax({
        url: `${getUrl()}/api/v1/admin/schedules/${scheduleId}/auctions?zoneGradeId=${zoneId}`,
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(auctionCreateRequest),
        beforeSend: function (xhr) {
            if (token) {
                xhr.setRequestHeader('Authorization', token);
            }
        },
        success: function (response) {
            rowElement.find('.save-button').prop('disabled', true);
            rowElement.find('.delete-button').prop('disabled', true);
            alert('저장에 성공했습니다.');
        },
        error: function (xhr, status, error) {
            errorAlert('저장에 실패했습니다: ' + xhr.responseText);
        }
    });
}

// 좌석 번호가 유효한지 검증하는 함수
function validateZoneNameAndSeatNumber(placeZoneInfo, seatNumber, zoneName) {
    let valid = false;
    placeZoneInfo.forEach(zoneInfo => {
        if (zoneInfo.name === zoneName && seatNumber > 0 && seatNumber <= zoneInfo.seatNumber) {
            valid = true;
        }
    });
    return valid;
}

var savedSeats = {};

// 동일한 데이터가 있는지 확인하는 함수
function isSeatAlreadySaved(scheduleId, zoneGradeName, seatNumber) {
    var key = `schedule_${scheduleId}_zone_${zoneGradeName}`;
    if (savedSeats[key] && savedSeats[key].includes(seatNumber)) {
        return true;
    }

    if (!savedSeats[key]) {
        savedSeats[key] = [];
    }
    savedSeats[key].push(seatNumber);

    return false;
}
