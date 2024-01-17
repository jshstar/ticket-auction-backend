let zones = [];

function addZone() {
    const zoneName = $('#zoneName').val().trim();
    const seatsCount = $('#zoneSeats').val().trim();

    if (!isValidInput(zoneName, seatsCount)) {
        return;
    }
    // 입력 받은 데이터를 zones 배열에 추가합니다.
    zones.push({zoneName, seatsCount: parseInt(seatsCount, 10)});

    // 입력 받은 데이터를 zones 배열에 추가합니다.
    zones.push({zoneName, seatsCount: parseInt(seatsCount, 10)});

    // 입력된 구역명과 좌석 수로 테이블에 새로운 행을 추가합니다.
    addRowToTable(zoneName, seatsCount);

    // 입력 칸 초기화
    $('#zoneName').val('');
    $('#zoneSeats').val('');
}

function addRowToTable(zoneName, seatsCount) {
    const newRow = $('<tr>').append(
        $('<td>').text(zoneName),
        $('<td>').text(seatsCount),
        $('<td>').append($('<button>').text('제거').addClass('remove-zone'))
    );

    $('#zonesTable').append(newRow);
}

function isValidInput(zoneName, seatsCount) {
    if (!zoneName || !seatsCount || isNaN(parseInt(seatsCount, 10))) {
        alert('유효한 구역명과 좌석 수를 입력해주세요.');
        return false;
    }
    return true;
}

function removeZone(button) {
    // 제거 버튼을 클릭한 행을 찾아서 삭제합니다.
    $(button).closest('tr').remove();
}

// place js
function submitPlace(token) {
    const name = $('#placeName').val().trim();
    const address = $('#address').val().trim();
    let zoneInfos = zones.map(zone => ({
        name: zone.zoneName,
        seatNumber: zone.seatsCount
    }));

    if (!isValidPlaceInput(name, address, zoneInfos)) {
        return;
    }

    const placeRequest = {
        name: name,
        address: address,
        zoneInfos: zoneInfos
    };

    $.ajax({
        url: '/api/v1/admin/places',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(placeRequest),
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

    function isValidPlaceInput(name, address, zoneInfos) {
        if (!name || !address || zoneInfos.length === 0) {
            alert('모든 필드를 채워주세요.');
            return false;
        }
        return true;
    }

    function handleSuccess(response) {
        console.log('Success:', response);
        alert('공연장이 성공적으로 추가되었습니다.');

        // 테이블의 데이터만 삭제
        $('#zonesTable tr:not(#inputRow)').remove();
        zones = []; // zones 배열 초기화
    }

    function handleError(error) {
        console.error('Error:', error);
        alert('오류가 발생했습니다. 공연장을 추가하지 못했습니다. 오류: ' + error);
    }
}


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
            $("#submitButton").prop('disabled', true).text('Submitting...');
        },
        success: function (response) {
            alert('공연 정보가 성공적으로 추가되었습니다.');
            console.log(response);
            // 폼 초기화 등 추가 작업 수행
        },
        error: function (xhr, status, error) {
            alert('공연 정보를 추가하는 중 오류가 발생했습니다: ' + error);
        },
        complete: function () {
            // 요청이 완료되면 버튼을 다시 활성화
            $("#submitButton").prop('disabled', false).text('Add Performance Info');
        }
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


